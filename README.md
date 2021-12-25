# Kyruus technical interview home assignment

Esteban Capillo, Dec 2021

The problem statement can be found in the root directory

## Instructions to build and run the example application
The following instructions apply to Linux (Fedora)

### As a maven application

1. Move to project's root directory
2. Build the command jar with ```mvn clean package```
3. Execute with ```java -jar target/<path-to-jar> <csv-path> <column-name>```

There are a few csv samples in **src/java/resources** folder in the project

### As a docker application

1. Move to project's root directory
2. Build the image with ```sudo docker build -t esteban/kyruus .```
3. Execute with ```docker run -v $(pwd)/src/main/resources:/usr/resources esteban/kyruus /usr/resources/sample1.csv City``` for example

In this case I choose to map host's sample resources to the container. 

Another possibility is to pass the csv as input stream to ```docker run``` and modify the application to work with the system stream
instead of a file path.