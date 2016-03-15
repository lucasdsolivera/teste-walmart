package com.lucas;

import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "br.com.lucas")
public class TesteWalmartApplication extends Neo4jConfiguration {

	public TesteWalmartApplication() {
		setBasePackage("br.com.lucas");
	}

	@Bean
	GraphDatabaseService graphDatabaseService() {
		return new GraphDatabaseFactory()
				.newEmbeddedDatabase("src/main/resources/baseNeo4jDB.db");
	}
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(TesteWalmartApplication.class, args);
	}

}
