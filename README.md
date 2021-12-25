# Intructions to run

## As a maven application

1. Move to the project's root
2. Build the command jar with ```mvn clean package```
3. Execute with ```java -jar target/<path-to-jar> <csv-path> <column-name>```

There are a few csv samples in src/java/resources folder in the project

## As a docker application

1. Move to the project's root
2. Build the image with ```sudo docker build -t esteban/kyruus .```
3. Execute with ```docker run -v $(pwd)/src/main/resources:/usr/resources esteban/kyruus /usr/resources/sample1.csv City``` for example