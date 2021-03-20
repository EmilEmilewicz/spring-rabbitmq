#!/bin/sh

mvn liquibase:rollback -Dliquibase.rollbackTag="consumer-1.0"
mvn liquibase:update
mvn spring-boot:run
