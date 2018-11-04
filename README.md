# My bank

## Overview
##### This service provides the following abilities 
1. Ability to create monetary accounts with initial balance
2. Ability to transfer money from one account to another

## Prerequisites

* Java 8 or above
* maven 3.2.1 or above

## Setting up development environment

##### Follow the below steps
```
1. git clone https://github.com/muralicmr/mybank.git
2. cd mybank
3. mvn spring-boot:run
```

## Services 

This repository provides 2 services
1. Create account. 

```
curl -X POST \
  http://localhost:8080/account \
  -H 'Content-Type: application/json' \
  -d '{
	"firstName": "Test",
	"lastName": "User",
	"balance": "1343.21"
}'
```

2. Make payment from one account to another
```
curl -X POST \
  http://localhost:8080/makePayment \
  -H 'Content-Type: application/json' \
  -d '{
	"fromAccount": 1,
	"toAccount": 2,
	"amount": "123",
	"description": "Test transfer"
}'
```

## Development tips

Navigate to below URL to look at the contents of H2 database
```
http://localhost:8080/h2-console/login.do
JDBC URL: jdbc:h2:mem:testdb
```

To prefill table you can insert data into ```data.sql```

## TODO

- [X] development
- [X] Unit tests
- [ ] Integration Tests
- [ ] JenkinsFile
- [ ] Sonar
- [ ] Fortify
- [ ] JMeter
- [ ] Deploy to Production