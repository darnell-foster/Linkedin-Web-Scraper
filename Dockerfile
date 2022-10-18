#Creates a container that has this version of java
FROM openjdk:18.0.2.1-jdk-oracle 
# copy contents of file into docker container with a folder called app that holds all file contents
COPY . /app 
#changes the working directory into app folder
WORKDIR /app
# Runs this on the cmd terminal
CMD ["java", "-cp", "jsoup-1.15.3.jar", "src/main/java/org/WebScrapper/Main.java"]