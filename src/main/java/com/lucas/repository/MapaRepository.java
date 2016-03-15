package com.lucas.repository;

import java.util.List;

import com.lucas.to.MapaTO;

public interface MapaRepository {

	MapaTO cadastraMapa(MapaTO mapa);

	List<MapaTO> findAll();
	
}
