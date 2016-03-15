package com.lucas.to;

public class RetaTO {

	String pontoOrigem;
	String pontoDestino;
	Double distancia;
	
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
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	@Override
	public String toString() {
		return "RetaTO [pontoOrigem=" + pontoOrigem + ", pontoDestino="
				+ pontoDestino + ", distancia=" + distancia + "]";
	}
	
}
