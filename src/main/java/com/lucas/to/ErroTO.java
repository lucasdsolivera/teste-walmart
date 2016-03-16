package com.lucas.to;

public class ErroTO {

	private String mensagem;
	private String causa;
	
	public ErroTO(Exception e) {
		this.causa = e.getClass().getSimpleName();
		this.mensagem = e.getMessage();
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	@Override
	public String toString() {
		return "ErroTO [mensagem=" + mensagem + ", causa=" + causa + "]";
	}
}