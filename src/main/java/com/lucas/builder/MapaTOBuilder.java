package com.lucas.builder;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;

import com.lucas.to.MapaTO;
import com.lucas.to.RetaTO;

public class MapaTOBuilder {
	
	private MapaTO mapaTO;
	
	public MapaTOBuilder() {
		mapaTO = new MapaTO();
	}
	
	public MapaTOBuilder comNome(String indexName) {
		mapaTO.setNome(indexName);
		return this;
	}
	
	public MapaTOBuilder comRetas(IndexHits<Node> nodes) {
		List<RetaTO> retas = new ArrayList<RetaTO>();
		
		nodes.forEachRemaining(node -> {
			node.getRelationships(Direction.OUTGOING).forEach(r -> {
				retas.add(new RetaTOBuilder()
							.comPontoOrigem((String) r.getStartNode().getProperty("nome"))
							.comPontoDestino((String) r.getEndNode().getProperty("nome"))
							.comDistancia((Double) r.getProperty("distancia"))
							.build());
			});
		});
		mapaTO.setRetas(retas);
		return this;
	}
	
	public MapaTO build() {
		return mapaTO;
	}
}
