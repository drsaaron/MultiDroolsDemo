#! /bin/sh

# get dependencuies
if ./mvnw clean install
then
    rm -rf target/dependency
    mvn dependency:copy-dependencies
fi
