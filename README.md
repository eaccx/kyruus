# Kyruus technical interview home assignment

Esteban Capillo, Dec 2021

The problem statement can be found in the root directory

## Instructions to build and run the example application
The following instructions apply to Linux (Fedora). 
It is assumed that the environment have the following tools already installed:
* maven
* docker
* java 8+
* a working internet connection for Docker and Maven to work

### As a maven application

1. Move to project's root directory
2. Build the command jar with ```mvn clean package```
3. Execute command with ```java -jar target/<path-to-jar> <column-name> < <path-to-csv>```

There are a few csv samples in **src/java/resources** folder in the project

### As a docker application

1. Move to project's root directory
2. Build the jar with ```mvn clean package```
3. Build the image with ```sudo docker build -t esteban/kyruus .```
4. Execute command with ```docker run -i esteban/kyruus City < src/main/resources/sample1.csv``` for example

## Running tests

Test can be run with ```mvn clean test```

Tests scenarios are written in Gherkin, located at ```src/test/resources/com/kyruus/CsvSort.feature``` and executed by Cucumber and Junit