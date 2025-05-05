# Giai đoạn 1: Build ứng dụng
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
COPY . .

# Đảm bảo mvnw có quyền thực thi
RUN chmod +x mvnw

# Build project
RUN ./mvnw clean package -DskipTests

# Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8989

ENTRYPOINT ["java", "-jar", "app.jar"]
