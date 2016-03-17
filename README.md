# Teste Walmart - Entrega de Mercadorias - Lucas


## Como configurar

1. Baixe o projeto fazendo download do ZIP utilizando git clone:

        $ git clone git@github.com:lucasdsolivera/teste-walmart.git
        
2. Na pasta do projeto execute:
		 
		$ mvn clean install
		
3. E em seguida:       
	
		$ java -jar target/teste-walmart-0.0.1-SNAPSHOT.jar


## Como utilizar
	Segue uma lista de paths disponíveis com o método para sua utilização e um exemplo de request.

	Método: 		GET
	Path: 			http://localhost:8080/mapas/

	Método: 		GET
	Path: 			http://localhost:8080/mapas/{nomeMapa}
	
	Método: 		POST
	Path: 			http://localhost:8080/mapas
	Content-Type: application/json
	Body:
			{
			  "nome":"São Paulo",
			  "retas":
				  [
				  		{"pontoOrigem":"A", "pontoDestino":"B", "distancia":10},
				       {"pontoOrigem":"B", "pontoDestino":"C", "distancia":30},
				       {"pontoOrigem":"C", "pontoDestino":"D", "distancia":5},
				       {"pontoOrigem":"A", "pontoDestino":"D", "distancia":100}
				  ]
			}
	
	Método:			POST
	Path:			http://localhost:8080/mapas/consulta-menor-caminho
	Content-Type: application/json
	Body:
			{
			  "mapa":"São Paulo",
			  "pontoOrigem":"A",
			  "pontoDestino":"D",
			  "autonomia":10,
			  "valorLitro":5
			}
	Retorno Sucesso:
			{
				"pontos": [4]
							0:  "A"
							1:  "B"
							2:  "C"
							3:  "D"
							-
				"valor": 22.5
				"distancia": 45
				"erros": null
			}		
	Retorno Erro (neste caso com o status HTTP 400 Bad Request):
			{
				"pontos": null
				"valor": null
				"distancia": null
				"erros": [1]
						0:  {
							"mensagem": "Destino não existe"
							"causa": "IllegalArgumentException"
						}-
				-
			}			
	
## Tecnologias Utilizadas
	- Java 8
	- Spring (Core, Boot, Web)
	- Neo4j
	- TomCat
	- JUnit
	
Considerações: Escolhi utilizar o Spring boot pela facilidade na configuração inicial do projeto com o http://start.spring.io, O Java 8 me ajudou com as Collections que ficaram mais faceis de manipular e até simular lambda, Neo4j era a solução mais óbvia para o desafio então aproveitei dos recursos da API do Neo4J para Java e utilizei o algorítimo "dijkstra" presente nela para resolver o desafio. Foquei os testes unitários nos cálculos de menor caminho e nas buscas do neo4j, infelizmente não consegui configurar o JUnit para rodar em um db(neo4j) separado, portanto criei mocks temporários para execução dos testes que são excluídos assim que os mesmos terminam. Utilizei o Design Pattern Builder para facilitar e tornar mais legivel a criação dos objetos do projeto e Não criei um Service para acesso ao Repository ambos Design Patterns pois precisava ter uma sessão aberta do Neo4j para utilizar API por isso deixei tudo no Repository. Resumindo tentei fazer algo simples, organizado e de fácil entendimento com as tecnologias mais utlizadas atualmente, espero que gostem :)	
