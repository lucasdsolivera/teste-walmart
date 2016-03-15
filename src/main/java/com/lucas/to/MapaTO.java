package com.lucas.to;

import java.util.List;


public class MapaTO {
	
	String nome;
	
	List<RetaTO> retas;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<RetaTO> getRetas() {
		return retas;
	}

	public void setRetas(List<RetaTO> retas) {
		this.retas = retas;
	}

	@Override
	public String toString() {
		return "MapaTO [nome=" + nome + ", retas=" + retas + "]";
	}
	
}