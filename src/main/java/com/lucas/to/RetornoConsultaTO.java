package com.lucas.to;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class RetornoConsultaTO {

	private List<String> pontos;
	private BigDecimal valor;
	private Double distancia;
	private List<ErroTO> erros;
	
	public RetornoConsultaTO() {
	}
	public RetornoConsultaTO(Exception e) {
		erros = Arrays.asList(new ErroTO(e));
	}
	public List<String> getPontos() {
		return pontos;
	}
	public void setPontos(List<String> pontos) {
		this.pontos = pontos;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	public List<ErroTO> getErros() {
		return erros;
	}
	public void setErros(List<ErroTO> erros) {
		this.erros = erros;
	}

	
}