# Sensor Api

### Instructions of use

#### Start application
`mvn spring-boot:run`


#### Run tests
`mvn test`


#### Curl requests to add sensor data

##### POST request
```
curl -X POST http://localhost:8080/sensors -d @newDat.json --header "Content-Type: application/json"
```
`File newDat.json is in the root project`

##### GET Requests
`Specific Sensor Two`
```
curl -X GET "http://localhost:8080/metrics/2" --header "Content-Type: application/json"
```
`Specific Sensor Two with time ranges`
```
curl -X GET "http://localhost:8080/metrics?starts_at=2022-11-01T20:00:00&ends_at=2022-11-09T20:00:00" --header "Content-Type: application/json"
```

`All sensors`
```
curl -X GET "http://localhost:8080/metrics" --header "Content-Type: application/json"
```