package com.lucas.repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lucas.TesteWalmartApplication;
import com.lucas.builder.MapaTOBuilder;
import com.lucas.to.MapaTO;
import com.lucas.to.ParametrosConsultaTO;
import com.lucas.to.RetornoConsultaTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TesteWalmartApplication.class)
public class MapaRepositoryTest {

	@Autowired
	private MapaRepository mapaRepository;
	
	@Before()
	public void before() {
		mapaRepository.cadastraMapa(MapaTOBuilder.mockSP());
		mapaRepository.cadastraMapa(MapaTOBuilder.mockSantos());
	}
	
	@After
	public void after() {
		mapaRepository.deletaMapaByName("JUnitTesteSP");
		mapaRepository.deletaMapaByName("JUnitTesteSantos");
	}
	
	@Test
	public void deveCalcularMenorDistanciaCaminhoObvio() throws Exception {
		ParametrosConsultaTO parametros = new ParametrosConsultaTO();
		parametros.setMapa("JUnitTesteSP");
		parametros.setPontoOrigem("A");
		parametros.setPontoDestino("C");
		parametros.setAutonomia(BigDecimal.ONE);
		parametros.setValorLitro(BigDecimal.TEN);
		
		RetornoConsultaTO retorno = mapaRepository.consultaMenorCaminho(parametros);
		
		Assert.assertNotNull(retorno);
		Assert.assertEquals(Arrays.asList("A", "C"), retorno.getPontos());
		Assert.assertEquals(new Double(5.0), retorno.getDistancia());
	}
	
	@Test
	public void deveCalcularMenorDistanciaCaminhoAlternativo() throws Exception {
		ParametrosConsultaTO parametros = new ParametrosConsultaTO();
		parametros.setMapa("JUnitTesteSantos");
		parametros.setPontoOrigem("A");
		parametros.setPontoDestino("D");
		parametros.setAutonomia(BigDecimal.ONE);
		parametros.setValorLitro(BigDecimal.TEN);
		
		RetornoConsultaTO retorno = mapaRepository.consultaMenorCaminho(parametros);
		
		Assert.assertNotNull(retorno);
		Assert.assertEquals(Arrays.asList("A", "B", "C", "D"), retorno.getPontos());
		Assert.assertEquals(new Double(17.0), retorno.getDistancia());
	}
	
	@Test
	public void deveCalcularPrecoCaminhoObvio() throws Exception {
		ParametrosConsultaTO parametros = new ParametrosConsultaTO();
		parametros.setMapa("JUnitTesteSP");
		parametros.setPontoOrigem("A");
		parametros.setPontoDestino("C");
		parametros.setAutonomia(BigDecimal.ONE);
		parametros.setValorLitro(BigDecimal.TEN);
		
		RetornoConsultaTO retorno = mapaRepository.consultaMenorCaminho(parametros);
		
		Assert.assertNotNull(retorno);
		Assert.assertEquals(new BigDecimal("50.00"), retorno.getValor());
	}
	
	@Test
	public void deveCalcularPrecoCaminhoAlternativo() throws Exception {
		ParametrosConsultaTO parametros = new ParametrosConsultaTO();
		parametros.setMapa("JUnitTesteSantos");
		parametros.setPontoOrigem("A");
		parametros.setPontoDestino("D");
		parametros.setAutonomia(new BigDecimal(8.0));
		parametros.setValorLitro(new BigDecimal(4.5));
		
		RetornoConsultaTO retorno = mapaRepository.consultaMenorCaminho(parametros);
		
		Assert.assertNotNull(retorno);
		Assert.assertEquals(new BigDecimal("9.56"), retorno.getValor());
	}
	
	@Test
	public void deveListarTodosMapas() {
		List<MapaTO> mapas = mapaRepository.findAll();
		Assert.assertNotNull(mapas);
		Assert.assertTrue(mapas.get(0).getNome().equals("JUnitTesteSP"));
		Assert.assertTrue(mapas.get(1).getNome().equals("JUnitTesteSantos"));
	}
	
	@Test 
	public void deveDeletarMapa() {
		mapaRepository.deletaMapaByName("JUnitTesteSP");
		mapaRepository.deletaMapaByName("JUnitTesteSantos");
		List<MapaTO> mapas = mapaRepository.findAll();
		Assert.assertTrue(mapas.isEmpty());
	}
	
	@Test
	public void deveEncontrarMapa() {
		MapaTO mapaTO= mapaRepository.findMapaByName("JUnitTesteSP");
		Assert.assertNotNull(mapaTO);
		Assert.assertTrue(mapaTO.getRetas().size() == 3);
	}
	
}
