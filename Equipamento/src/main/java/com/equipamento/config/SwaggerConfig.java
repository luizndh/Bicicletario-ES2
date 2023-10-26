package com.equipamento.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * Utilizado para configurar aspectos dos endpoints do webservice
	 */
	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.echo")) //pacote base onde estão todas as classes java
                .paths(regex("/echo.*")) //caminhos para acesso aos endpoints
                .build()
                .apiInfo(metaInfo());
    }

	/**
	 * Utilizado para informações adicionais da API
	 */	
    private ApiInfo metaInfo() {
        return new ApiInfoBuilder()
        	    .title("Spring Boot REST API Echo")
        	    .description("Exemplo de aplicação REST API com Spring Boot ")
        	    .version("1.0")
        	    .build();     	           
    }

}
