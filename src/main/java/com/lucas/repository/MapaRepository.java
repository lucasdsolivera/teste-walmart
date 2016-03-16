package com.lucas.repository;

import java.util.List;

import com.lucas.to.MapaTO;
import com.lucas.to.ParametrosConsultaTO;
import com.lucas.to.RetornoConsultaTO;

public interface MapaRepository {

	MapaTO cadastraMapa(MapaTO mapa);

	List<MapaTO> findAll();

	MapaTO findMapaByName(String nomeMapa);
	
	void deletaMapaByName(String nomeMapa);

	RetornoConsultaTO consultaMenorCaminho(ParametrosConsultaTO parametrosConsultaTO) throws Exception;
	
}
