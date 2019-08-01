### Running & Tests

- For running application `mvn spring-boot:run` 
    - Application will start at port 8080, browse `http://localhost:8080/api/flights`
- For running test, `mvn test`

### Api Details

``` sh

GET     /api/flights

Params
------------------
filterBy [optional] -> Parameter for filtering, 'name:value' pair    
                       Valid values: [arrival:XXXX, departure:YYYY]
sortBy   [optional] -> Sort field. 
                       Valid values: [arrival, departure, arrivalTime, departureTime]
dir      [optional] -> Sort direction
                       Valid values: [asc, desc]
page     [optional] -> Requested page, default 1
pageSize [optional] -> Page size, default 10


Sample Request & Response
---------------------------
Request  : http://localhost:8080/api/flights?sortBy=arrival&dir=desc&filterBy=arrival:Antalya&page=1&pageSize=10 
Response : 
{
    "filter": {
        "filterBy": "arrival:Antalya"
    },
    "sorting": {
        "sortBy": "arrival",
        "dir": "desc"
    },
    "page": {
        "page": 1,
        "pageSize": 10
    },
    "flights": [
        {
            "departure": "Cruz del Eje",
            "arrival": "Antalya",
            "arrivalTime": 1558902656,
            "departureTime": 1558902656
        },
        {
            "departure": "Ankara",
            "arrival": "Antalya",
            "arrivalTime": 1564410656,
            "departureTime": 1561627856
        },
        {
            "departure": "Istanbul",
            "arrival": "Antalya",
            "arrivalTime": 1563678000,
            "departureTime": 1563588000
        },
        {
            "departure": "Istanbul",
            "arrival": "Antalya",
            "arrivalTime": 1558902656,
            "departureTime": 1558902656
        },
        {
            "departure": "Istanbul",
            "arrival": "Antalya",
            "arrivalTime": 1558902656,
            "departureTime": 1558902656
        }
    ]
}
```

### Technical Details

- Api calls to external flight api are running in parallel in a seperate thread pool
- For simplicity, there is only `GlobalExceptionHandler` to catch all exception and return `500`  
