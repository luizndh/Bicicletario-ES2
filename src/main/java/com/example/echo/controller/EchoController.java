package com.example.echo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo.webservice.SwapiClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * API Echo - Projeto base para a disciplina de Programação Modular.
 *   
 * Os métodos da API Echo são documentados utilizando a especificação OpenAPI (Swagger) e podem ser visualizados 
 * acessando a URL do domínio + "/swagger-ui.html".
 * Acesso em servidor local: http://localhost:8080/swagger-ui.html
 */

@RestController
@RequestMapping("/echo")
@Api(value="Spring Boot REST API Echo")
public class EchoController {

	@Autowired
	private SwapiClient swapi;
	
	@GetMapping("/{palavra}")
	@ApiOperation(value="Retorna repetição da palavra informada")
	public String getEcho (@PathVariable (value ="palavra") String palavra){
		return palavra +" "+ palavra +" "+ palavra;					
	}
	
	@GetMapping("/")
	@ApiOperation(value="Retorna informação sobre a API")
	public String getRoot () {
		return "Isto é um eco, adicione uma palavra no caminho.";					
	} 
	
	@RequestMapping(value ="/planet", method = RequestMethod.GET)
	@ApiOperation(value="Retorna o nome de um planeta do Star Wars informando o valor do id [1-60] na requisição à API Swapi")
	public @ResponseBody String getPlanetName (@RequestParam ("id") String id){   
		return "Planeta Star Wars: " + swapi.getPlanetName(id);			
	}
	
}