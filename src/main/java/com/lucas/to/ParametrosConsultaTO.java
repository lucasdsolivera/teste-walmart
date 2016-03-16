package com.lucas.to;

import java.math.BigDecimal;

public class ParametrosConsultaTO {

	private String mapa;
	private String pontoOrigem;
	private String pontoDestino;
	private BigDecimal autonomia;
	private BigDecimal valorLitro;

	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public String getPontoOrigem() {
		return pontoOrigem;
	}

	public void setPontoOrigem(String pontoOrigem) {
		this.pontoOrigem = pontoOrigem;
	}

	public String getPontoDestino() {
		return pontoDestino;
	}

	public void setPontoDestino(String pontoDestino) {
		this.pontoDestino = pontoDestino;
	}

	public BigDecimal getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(BigDecimal autonomia) {
		this.autonomia = autonomia;
	}

	public BigDecimal getValorLitro() {
		return valorLitro;
	}

	public void setValorLitro(BigDecimal valorLitro) {
		this.valorLitro = valorLitro;
	}

	@Override
	public String toString() {
		return "ParametrosConsultaTO [mapa=" + mapa + ", pontoOrigem="
				+ pontoOrigem + ", pontoDestino=" + pontoDestino
				+ ", autonomia=" + autonomia + ", valorLitro=" + valorLitro
				+ "]";
	}
	
}