package com.lucas.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Component;

import com.lucas.builder.MapaTOBuilder;
import com.lucas.repository.MapaRepository;
import com.lucas.to.MapaTO;


@Component
public class MapaRepositoryImpl implements MapaRepository {

	@Autowired
	GraphDatabaseService graphDatabaseService;
	
	@Resource
	GraphDatabase graphDatabase;

	private boolean flagRelacionamento;
	
	@Override
	public List<MapaTO> findAll() {
		List<MapaTO> mapas =  new ArrayList<MapaTO>();
		Transaction tx = graphDatabase.beginTx();
		
		Arrays.asList((graphDatabaseService.index().nodeIndexNames())).forEach(indexName -> {
			
			IndexHits<Node> nodes = graphDatabaseService.index().forNodes(indexName).query(new WildcardQuery(new Term("nome", "*")));
			
			mapas.add(new MapaTOBuilder().comNome(indexName).comRetas(nodes).build());
		}); 
		tx.success();
		tx.close();
		return mapas;
	}
	
	@Override
	public MapaTO cadastraMapa(MapaTO mapaTO) {
		Transaction tx = graphDatabase.beginTx();
		
		mapaTO.getRetas().forEach(r -> {
			relacionaPontosNoMapa(r.getPontoOrigem(), r.getPontoDestino(), r.getDistancia(), mapaTO.getNome());	
		});
		
		tx.success();	
		tx.close();
		return mapaTO;
	}
	
	private void relacionaPontosNoMapa(String pontoOrigem, String pontoDestino, Double distancia, String nomeMapa) {
		Node nodeOrigem = criaPonto(pontoOrigem, nomeMapa);
		Node nodeDestino = criaPonto(pontoDestino, nomeMapa);
		flagRelacionamento = false;
		
		nodeOrigem.getRelationships(Direction.OUTGOING).forEach(r-> {
			if (r.getEndNode().equals(nodeDestino)) {
				r.setProperty("distancia", distancia);
				flagRelacionamento = true;
			}
				
		});
		
		if (!flagRelacionamento) {
			Relationship relacao = nodeOrigem.createRelationshipTo(nodeDestino, DynamicRelationshipType.withName("distancia"));
			relacao.setProperty("distancia", distancia);
		}
	}
	
	private Node criaPonto(String nomePonto, String nomeMapa) {

		Index<Node> indexService = graphDatabaseService.index().forNodes(nomeMapa);
		
		Node node =  indexService.get("nome", nomePonto).getSingle(); 
		
		if (node == null) {		
			node = graphDatabaseService.createNode();
			node.setProperty("nome", nomePonto);
			indexService.add(node, "nome", nomePonto);
		}
		return node;
	}

}
