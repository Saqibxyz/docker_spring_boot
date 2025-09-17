# Learn from this website
>https://spring.io/guides/gs/spring-boot-docker
# Some commands
## generate jar file (make sure you are inside correct directory)
```
./mvnw package
```
#### your docker file (make sure the openjdk version is compatible with the java version you are using)
```
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/your_jar_file_name.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
#### build image 
```
docker build -t name-of-image:version .
```

#### check generated image
```declarative
docker images

```
#### run you image
```declarative
docker run -p 8080:8080 spring-boot-app:1.0

```
## Docker Network
#### containes need to communicate with each other. They can achieve this by these
> 1. Ip address of container
> 2. Name of the container if they are in same network
### create network
```declarative
docker network create my-network
```
### run container in that network (not preferred, use docker-compose.yml instead)
```docker run --name springboot --network my-network my-springboot-image
docker run --name mysql --network my-network -e MYSQL_ROOT_PASSWORD=root mysql:8
```
#### Your _docker-compose.yml_ (sample file) setup for communication etc
```
version: '3.9'  # Compose file version

services:
  # Spring Boot Application
  app:
    image: my-springboot-image      # Name of your Docker image
    container_name: springboot-app  # Optional: set a fixed container name
    ports:
      - "8080:8080"                 # Map host port 8080 to container port 8080
    environment:
      SPRING_PROFILES_ACTIVE: dev   # Pass env variables to the app
    depends_on:
      - db                          # Ensure DB starts before app
    networks:
      - my-network                  # Connect to custom network
    restart: unless-stopped          # Auto-restart policy
    volumes:
      - ./logs:/app/logs            # Mount host folder for logs (optional)
    build:
      context: .                    # Uncomment if you want Compose to build the image
      dockerfile: Dockerfile

  # MySQL Database
  db:
    image: mysql:8                  # MySQL official image
    container_name: mysql-db        # Optional container name
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: mydb
      MYSQL_USER: user              # Optional user
      MYSQL_PASSWORD: password      # Optional user password
    ports:
      - "3306:3306"                 # Expose MySQL port to host
    volumes:
      - db-data:/var/lib/mysql      # Persist database data
    networks:
      - my-network                  # Connect to same network as app
    restart: unless-stopped          # Auto-restart policy

# Named volumes for persistent storage
volumes:
  db-data:                          # MySQL data persists even if container removed

# Custom network (optional but recommended)
networks:
  my-network:
    driver: bridge                  # Default network driver


```
#### Now you can run this docker compose by this command
```
cd path/to/your/project
```
```
docker compose up

```
#### or
```
docker compose up -d

```
... Learn other compose commands yourself...


# Kubernates
#### start minikube
```
minikube start
```
#### Point Docker CLI to Minikube’s Docker
```
& minikube -p minikube docker-env | Invoke-Expression

```
#### Build your Docker image inside Minikube
##### Go to your project folder (where Dockerfile is) and run:
```
docker build -t image_name:x.y.z .

```
#### Apply your Kubernetes YAMLs
##### image: my-edited-image:1.2.0
##### imagePullPolicy: Never
#### location for yaml files

```spring-boot-project/
├── src/
├── pom.xml
├── Dockerfile
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml


```

```
kubectl apply -f k8s/

```
#### to delete pods
```
kubectl delete pod -l app=my-app

```
#### verify running pods
```
kubectl get pods

```
#### Access your Spring boot app
```
minikube service my-app-service

```
