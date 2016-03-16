# Teste Walmart - Entrega de Mercadorias - Lucas


## Como configurar

1. Baixe o projeto fazendo download do ZIP utilizando git clone:

        $ git clone git@github.com:lucasdsolivera/teste-walmart.git
        
2. Na pasta do projeto execute:
		 
		$ mvn clean install
		
3. E em seguida:       
	
		$ java -jar target/teste-walmart-0.0.1-SNAPSHOT.jar


## Como utilizar
	Você precisará de um rest-client para utilização, segue uma lista de paths disponíveis com o método para sua utilização e um exemplo de retorno em seguida.

### Listando mapas disponíveis:

	$ GET
	$ http://localhost:8080/mapas/

	[2]
		0:  {
			"nome": "Santos"
			"retas": [2]
						0:  {
							"pontoOrigem": "A"
							"pontoDestino": "B"
							"distancia": 15
						}-
						1:  {
							"pontoOrigem": "B"
							"pontoDestino": "C"
							"distancia": 33
						}-
			-
			}-
		1:  {
			"nome": "São Paulo"
			"retas": [2]
						0:  {
							"pontoOrigem": "A"
							"pontoDestino": "Z"
							"distancia": 160
						}-
						1:  {
							"pontoOrigem": "Y"
							"pontoDestino": "Z"
							"distancia": 65
						}-
			-
			}
