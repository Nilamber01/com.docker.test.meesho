FROM zenika/alpine-maven as build

COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM openjdk:8-jre
COPY --from=build /app/target/meesho-automation.jar .
ENTRYPOINT ["sh", "-c", "java -jar meesho-automation.jar $suiteXML"]