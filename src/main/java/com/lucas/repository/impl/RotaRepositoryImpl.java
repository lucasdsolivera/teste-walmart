package com.lucas.repository.impl;

import javax.annotation.Resource;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.stereotype.Component;

import com.lucas.repository.RotaRepository;

@Component
public class RotaRepositoryImpl implements RotaRepository {

	@Autowired
	GraphDatabaseService graphDatabaseService;

	@Resource
	GraphDatabase graphDatabase;
	
	
	@Override
	public void cadastraReta(String pontoOrigem, String pontoDestino, Double distancia, String nomeMapa) {
		Transaction tx = graphDatabase.beginTx();
		try {
			this.salvaReta(pontoOrigem, pontoDestino, distancia, nomeMapa);
			tx.success();
		} finally {
			tx.close();
		}
	}
	
	private void salvaReta(String pontoOrigem, String pontoDestino, Double distancia, String nomeMapa) {
		Node nodeOrigem = this.criaPonto(pontoOrigem, nomeMapa);
		Node nodeDestino = this.criaPonto(pontoDestino, nomeMapa);

		Relationship relacao = nodeOrigem.createRelationshipTo(nodeDestino, DynamicRelationshipType.withName("distancia"));
		relacao.setProperty("distancia", distancia);
	}
	
	private Node criaPonto(String nomePonto, String nomeMapa) {

		Index<Node> indexService = graphDatabaseService.index().forNodes(nomeMapa);
		
		Node node = graphDatabaseService.createNode();
		node.setProperty("nome", nomePonto);
		indexService.add(node, "nome", nomePonto);
		
		return node;
	}
}
