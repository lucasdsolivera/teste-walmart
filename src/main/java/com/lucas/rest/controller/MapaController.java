package com.lucas.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.repository.MapaRepository;
import com.lucas.to.MapaTO;
import com.lucas.to.ParametrosConsultaTO;
import com.lucas.to.RetornoConsultaTO;


@RestController
public class MapaController {
	
	@Autowired
	private MapaRepository mapaRepository;
	
	@RequestMapping(path="/mapas", method=RequestMethod.POST) 
	public MapaTO salvarMapa(@RequestBody MapaTO mapaTO) {
		return mapaRepository.cadastraMapa(mapaTO);
	}
	
	@RequestMapping(path="/mapas/{nomeMapa}", method=RequestMethod.GET) 
	public MapaTO salvarMapa(@PathVariable String nomeMapa) {
		return mapaRepository.findMapaByName(nomeMapa);
	}
	
	@RequestMapping(path="/mapas", method=RequestMethod.GET)
	public List<MapaTO> mapas() {
		return mapaRepository.findAll();
	}
	
	@RequestMapping(path="/mapas/consulta-menor-caminho", method=RequestMethod.POST)
	public RetornoConsultaTO consultaMenorCaminho(@RequestBody ParametrosConsultaTO parametrosConsultaTO,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			return mapaRepository.consultaMenorCaminho(parametrosConsultaTO);
		} catch (Exception e) {
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new RetornoConsultaTO(e);
		}
	}
}
