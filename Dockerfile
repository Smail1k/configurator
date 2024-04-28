FROM maven:3.8.5-openjdk-17-slim

RUN apt-get update && apt-get install -y bash

WORKDIR /app

COPY ./pom.xml .

RUN mvn dependency:go-offline

# Копируем исходный код проекта
COPY . .

# Собираем и устанавливаем приложение
RUN mvn install --no-transfer-progress -DskipTests=true

# Экспонируем порт 8080
EXPOSE 8080

# Запускаем Spring Boot приложение
ENTRYPOINT ["mvn", "spring-boot:run"]