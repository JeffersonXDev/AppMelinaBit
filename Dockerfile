# Estágio de Compilação
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app
# Copia os arquivos do Maven e o código fonte
COPY . .
# Transforma o mvnw em executável e compila o JAR
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copia o JAR gerado no estágio anterior (ajuste o nome se necessário)
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


