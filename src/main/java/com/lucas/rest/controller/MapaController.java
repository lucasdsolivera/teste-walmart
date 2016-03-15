package com.lucas.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.repository.MapaRepository;
import com.lucas.to.MapaTO;


@RestController
public class MapaController {
	
	@Autowired
	private MapaRepository mapaRepository;
	
	@RequestMapping(path="/mapas", method=RequestMethod.POST) 
	public MapaTO salvarMapa(@RequestBody MapaTO mapaTO) {
		return mapaRepository.cadastraMapa(mapaTO);
	}
	
	@RequestMapping(path="/mapas", method=RequestMethod.GET)
	public List<MapaTO> mapas() {
		return mapaRepository.findAll();
	}
}
