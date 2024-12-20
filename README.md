# manageMatch
Manage an match


Run 

``mvn clean package``

Start docker 

``cd docker``

``docker-compose up``

inside resources there are 2 files(schema.sql) that sets up the schema and adds data for the MEETING_ROOM table
during the initialization of the application.

Swagger Definition is included (swagger.yaml)

Swagger UI : http://localhost:8080/swagger-ui/index.html

In order to access the API implemented you can use the postman collection
(Manage Match.postman_collection.json) included in ..the manageMatch/soapui folder.

Use Manage Match.postman_collection.json in order to test all the APIs created.

Match APIS(included in soapui folder file Manage Match.postman_collection.json)

  - Create Match(POST)
      
      ``http://localhost:8080/api/matches/create``
      
      Request
      
          {
              "description": "OSFP-PAO",
              "matchDate": "2021-03-31",
              "matchTime": "12:00",
              "teamA": "OSFP",
              "teamB": "PAO",
              "sport": "FOOTBALL"
          }
      
      Response
      
          {
              "description": "OSFP-PAO",
              "matchDate": "2021-03-31",
              "matchTime": "12:00:00",
              "teamA": "OSFP",
              "teamB": "PAO",
              "sport": "FOOTBALL"
          }
      
  - Get Match(GET)
      
      ``http://localhost:8080/api/matches/get/1``
      
      Response 
      
          {
              "description": "OSFP-PAO",
              "matchDate": "2021-03-31",
              "matchTime": "12:00:00",
              "teamA": "OSFP",
              "teamB": "PAO",
              "sport": "FOOTBALL"
          }
      
  - Get all Matches(GET)
      
      http://localhost:8080/api/matches/all
      
          [
              {
                  "description": "OSFP-PAO",
                  "matchDate": "2021-03-31",
                  "matchTime": "12:00:00",
                  "teamA": "OSFP",
                  "teamB": "PAO",
                  "sport": "FOOTBALL"
              }
          ]
      
  - Update Match (PUT)
      
      ``http://localhost:8080/api/matches/update/1``
      
      Request
      
          {
              "description": "OSFP-PAO",
              "matchDate": "2022-03-31",
              "matchTime": "12:00",
              "teamA": "OSFP",
              "teamB": "PAO",
              "sport": "FOOTBALL"
          }
      
      Response 
      
          {
              "description": "OSFP-PAO",
              "matchDate": "2022-03-31",
              "matchTime": "12:00:00",
              "teamA": "OSFP",
              "teamB": "PAO",
              "sport": "FOOTBALL"
          }
      
  - Delete Match(DELETE)
      
      ``http://localhost:8080/api/matches/delete/1``
      
      Response : 204 No-Content
    
Match Odds API 

  - Create Match Odds(POST)
  
    ``http://localhost:8080/api/match-odds/create``
    
      Request
    
        {
          "matchId": 1,
          "specifier": "string",
          "odd": 0
        }

      Response 
    
        {
            "matchId": 1,
            "specifier": "string",
            "odd": 0
          }
  
  - Get Match Odds(GET)
  
    ``http://localhost:8080/api/match-odds/get/1``
      
      Response
      
        {
          "matchId": 1,
          "specifier": "string",
          "odd": 0.0
        }
  
  - Get all Match Odds(GET)
  
    ``http://localhost:8080/api/match-odds/all``
    
    Response 
    
        [
           {
              "matchId": 1,
              "specifier": "string",
              "odd": 0.0
          }
        ]
    
    - Update Match Odds(PUT)
    
      ``http://localhost:8080/api/match-odds/update/1``
    
      Request
    
          {
              "matchId": 1,
              "specifier": "1X",
              "odd": 1.0
          }
          
      Response

          {
              "matchId": 1,
              "specifier": "1X",
              "odd": 1.0
          }  
  Delete Match Odds(DELETE)
  
  ``http://localhost:8080/api/match-odds/delete/1``
  
  Response : 204 No-Content
  
