# 第一阶段：使用 Maven + Java 25 进行打包构建
FROM maven:3.9.9-eclipse-temurin-25-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 第二阶段：使用 JRE 25 运行应用
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]