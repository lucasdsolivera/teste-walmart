package com.lucas.builder;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.neo4j.graphdb.Node;

import com.lucas.to.RetornoConsultaTO;

public class RetornoConsultaTOBuilder {

	RetornoConsultaTO retornoConsultaTO;

	public RetornoConsultaTOBuilder() {
		retornoConsultaTO = new RetornoConsultaTO();
		retornoConsultaTO.setPontos(new ArrayList<String>());
	}
	
	public RetornoConsultaTOBuilder comDistancia(Double distancia) {
		retornoConsultaTO.setDistancia(distancia);
		return this;
	}

	public RetornoConsultaTOBuilder comPontos(Iterable<Node> nodes) {
		nodes.forEach(node -> {
			retornoConsultaTO.getPontos().add((String) node.getProperty("nome"));
		});
		return this;
	}

	public RetornoConsultaTOBuilder comValor(BigDecimal autonomia, BigDecimal valorLitro) {
		BigDecimal valorFinal = new BigDecimal(retornoConsultaTO.getDistancia()).divide(autonomia).multiply(valorLitro);
		retornoConsultaTO.setValor(valorFinal);
		return this;
	}

	public RetornoConsultaTO build() {
		return retornoConsultaTO;
	}
	
	
}
