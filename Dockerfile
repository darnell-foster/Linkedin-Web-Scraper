FROM openjdk:18.0.2.1-jdk

WORKDIR /scraper

ADD . .

CMD [ "java", "-cp",  "jsoup-1.15.3.jar", "src/main/java/org/example/Main.java"]