package com.lucas.builder;

import com.lucas.to.RetaTO;

public class RetaTOBuilder {

	RetaTO retaTO;

	public RetaTOBuilder() {
		retaTO = new RetaTO();
	}
	
	public RetaTOBuilder comPontoOrigem(String pontoOrigem) {
		retaTO.setPontoOrigem(pontoOrigem);
		return this;
	}

	public RetaTOBuilder comPontoDestino(String pontoDestino) {
		retaTO.setPontoDestino(pontoDestino);
		return this;
	}

	public RetaTOBuilder comDistancia(Double distancia) {
		retaTO.setDistancia(distancia);
		return this;
	}

	public RetaTO build() {
		return retaTO;
	}
	
	
	
}
