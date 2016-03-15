package com.lucas.repository;

public interface RotaRepository {

	void cadastraReta(String pontoOrigem, String pontoDestino, Double distancia, String nomeMapa);
	
}
