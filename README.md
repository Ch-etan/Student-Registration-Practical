# Student Registration

A simple Student Registration web application that demonstrates a basic DevOps workflow using **GitHub, Maven, Jenkins, GitHub Actions and Docker**.

The app serves an HTML registration form (Student Name, Roll Number, Email, Course) and shows a confirmation page after submission. It uses Java's built-in HTTP server, so no external frameworks or database are required.

## Tech Stack

- Java 25
- Maven
- Jenkins (CI pipeline)
- GitHub Actions (CI workflow)
- Docker

## Project Structure

```
student-registration/
├── .github/workflows/maven.yml   # GitHub Actions workflow
├── src/main/java/com/example/App.java
├── src/main/resources/index.html # Registration form
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── README.md
```

## Prerequisites

- JDK 25
- Maven 3.9+
- Docker Desktop
- Git

## Build

```bash
mvn clean package
```

The runnable JAR is created at `target/student-app.jar`.

## Run Locally

```bash
java -jar target/student-app.jar
```

Open http://localhost:8080

## Run with Docker

```bash
docker build -t student-registration .
docker run -d -p 8081:8080 --name student-app student-registration
```

Open http://localhost:8081

Useful commands:

```bash
docker images
docker ps
docker logs student-app
docker rm -f student-app
```

## CI/CD

| Tool | Configuration | What it does |
|---|---|---|
| GitHub Actions | `.github/workflows/maven.yml` | Runs `mvn package` on every push to `main` |
| Jenkins | `Jenkinsfile` | Checks out the code, builds with Maven, archives the JAR |
| Docker | `Dockerfile` | Packages the JAR into a runnable container image |

## Author

- Name: <Chetan Chauhan>
- Roll Number: <2401331540093>