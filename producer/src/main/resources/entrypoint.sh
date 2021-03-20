#!/usr/bin/env bash

mvn liquibase:rollback -Dliquibase.rollbackTag="producer-1.0"
mvn liquibase:update
mvn spring-boot:run
