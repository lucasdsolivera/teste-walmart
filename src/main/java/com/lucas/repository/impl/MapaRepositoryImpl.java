package com.lucas.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lucas.builder.MapaTOBuilder;
import com.lucas.builder.RetornoConsultaTOBuilder;
import com.lucas.repository.MapaRepository;
import com.lucas.to.MapaTO;
import com.lucas.to.ParametrosConsultaTO;
import com.lucas.to.RetornoConsultaTO;


@Component
public class MapaRepositoryImpl implements MapaRepository {

	@Autowired
	GraphDatabaseService graphDatabaseService;

	private boolean flagRelacionamento;
		
	@Override
	public List<MapaTO> findAll() {
		List<MapaTO> mapas =  new ArrayList<MapaTO>();
		Transaction tx = graphDatabaseService.beginTx();
		
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
		Transaction tx = graphDatabaseService.beginTx();
		
		graphDatabaseService.index().forNodes(mapaTO.getNome());
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
		
		nodeOrigem.getRelationships(Direction.BOTH).forEach(r-> {
			if (r.getEndNode().equals(nodeDestino) || r.getStartNode().equals(nodeDestino)) {
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

	@Override
	public MapaTO findMapaByName(String nomeMapa) {
		Transaction tx = graphDatabaseService.beginTx();
		IndexHits<Node> nodes = graphDatabaseService.index().forNodes(nomeMapa).query(new WildcardQuery(new Term("nome", "*")));
		MapaTO mapaTO = new MapaTOBuilder().comNome(nomeMapa).comRetas(nodes).build();
		tx.success();
		tx.close();
		
		return mapaTO;
	}

	@Override
	public RetornoConsultaTO consultaMenorCaminho(ParametrosConsultaTO parametrosConsultaTO) throws Exception {
		Transaction tx = graphDatabaseService.beginTx();
		
		Node pontoOrigem = graphDatabaseService.index().forNodes(parametrosConsultaTO.getMapa()).get("nome", parametrosConsultaTO.getPontoOrigem()).getSingle();
		Node pontoDestino = graphDatabaseService.index().forNodes(parametrosConsultaTO.getMapa()).get("nome", parametrosConsultaTO.getPontoDestino()).getSingle();
		
		if (pontoOrigem == null) {
			throw new IllegalArgumentException("Origem não existe");
		}
		if (pontoDestino == null) {
			throw new IllegalArgumentException("Destino não existe");
		}
		
		PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(PathExpanders
				.forTypeAndDirection(DynamicRelationshipType.withName("distancia"), Direction.BOTH),"distancia");

		WeightedPath path = finder.findSinglePath(pontoOrigem, pontoDestino);
		
		RetornoConsultaTO retornoConsultaTO = new RetornoConsultaTOBuilder()
					.comDistancia(path.weight())
					.comPontos(path.nodes())
					.comValor(parametrosConsultaTO.getAutonomia(), parametrosConsultaTO.getValorLitro())
					.build();
		tx.success();
		tx.close();
		
		return retornoConsultaTO;
	}

	@Override
	public void deletaMapaByName(String nomeMapa) {
		Transaction tx = graphDatabaseService.beginTx();
		graphDatabaseService.index().forNodes(nomeMapa).delete();
		tx.success();
		tx.close();
	}

}
