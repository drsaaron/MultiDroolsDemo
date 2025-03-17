#! /bin/sh

./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.config.name=application,${ENVIRONMENT:-test}"
