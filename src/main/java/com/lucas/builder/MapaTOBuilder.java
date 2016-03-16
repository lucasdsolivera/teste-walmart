package com.lucas.builder;

import java.util.ArrayList;
import java.util.Arrays;
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
		mapaTO.setRetas(new ArrayList<RetaTO>());
	}
	
	public MapaTOBuilder comNome(String indexName) {
		mapaTO.setNome(indexName);
		return this;
	}
	
	public MapaTOBuilder comRetas(IndexHits<Node> nodes) {
		if (nodes != null) {
			nodes.forEachRemaining(node -> {
				node.getRelationships(Direction.OUTGOING).forEach(r -> {
					mapaTO.getRetas().add(new RetaTOBuilder()
								.comPontoOrigem((String) r.getStartNode().getProperty("nome"))
								.comPontoDestino((String) r.getEndNode().getProperty("nome"))
								.comDistancia((Double) r.getProperty("distancia"))
								.build());
				});
			});
		}
		return this;
	}
	
	public MapaTOBuilder comRetas(List<RetaTO> retas) {
		mapaTO.setRetas(retas);
		return this;
	}
	
	public MapaTO build() {
		return mapaTO;
	}

	//Metodo auxiliar para testes unitarios
	public static MapaTO mockSP() {
		RetaTO reta1 = new RetaTOBuilder().comPontoOrigem("A")
										.comPontoDestino("B")
										.comDistancia(10.0).build();
		RetaTO reta2 = new RetaTOBuilder().comPontoOrigem("B")
				.comPontoDestino("C")
				.comDistancia(20.0).build();
		RetaTO reta3 = new RetaTOBuilder().comPontoOrigem("A")
				.comPontoDestino("C")
				.comDistancia(5.0).build();
		return new MapaTOBuilder().comNome("JUnitTesteSP").comRetas(Arrays.asList(reta1,reta2,reta3)).build();
	}
	
	//Metodo auxiliar para testes unitarios
	public static MapaTO mockSantos() {
		RetaTO reta1 = new RetaTOBuilder().comPontoOrigem("A")
				.comPontoDestino("B")
				.comDistancia(3.0).build();
		RetaTO reta2 = new RetaTOBuilder().comPontoOrigem("B")
				.comPontoDestino("C")
				.comDistancia(5.0).build();
		RetaTO reta3 = new RetaTOBuilder().comPontoOrigem("A")
				.comPontoDestino("C")
				.comDistancia(9.0).build();
		RetaTO reta4 = new RetaTOBuilder().comPontoOrigem("C")
				.comPontoDestino("D")
				.comDistancia(9.0).build();
		RetaTO reta5 = new RetaTOBuilder().comPontoOrigem("A")
				.comPontoDestino("D")
				.comDistancia(19.0).build();
		
		return new MapaTOBuilder().comNome("JUnitTesteSantos").comRetas(
				Arrays.asList(reta1, reta2, reta3, reta4, reta5)
				).build();
	}
}
