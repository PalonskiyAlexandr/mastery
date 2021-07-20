For deployment write mvn jetty: run in command line
For deployment with roles write mvn jetty:run -Dspring.profiles.active=role_name in command line
For migration write mvn flyway:migrate -Dflyway.configFile=src/main/resources/flyway.properties in command line