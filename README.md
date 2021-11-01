![Build Status](https://github.com/KvalitetsIT/rm-eboks-service/workflows/CICD/badge.svg) ![Test Coverage](.github/badges/jacoco.svg)
# rm-eboks-service

## Endpoints

The service is listening for connections on port 8080.

Spring boot actuator is listening for connections on port 8081. This is used as prometheus scrape endpoint and health monitoring. 

Prometheus scrape endpoint: `http://localhost:8081/actuator/prometheus`  
Health URL that can be used for readiness probe: `http://localhost:8081/actuator/health`

## Configuration

| Environment variable | Description | Required |
|----------------------|-------------|---------- |
| LOG_LEVEL | Log Level for applikation  log. Defaults to INFO. | No |
| LOG_LEVEL_FRAMEWORK | Log level for framework. Defaults to INFO. | No |
| CORRELATION_ID | HTTP header to take correlation id from. Used to correlate log messages. Defaults to "x-request-id". | No
