# Giai đoạn 1: Build ứng dụng (có thể bỏ qua nếu đã có file JAR)
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package

# Giai đoạn 2: Chạy ứng dụng
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /app/target/hrm-0.0.1-SNAPSHOT.jar app.jar

# Mở cổng ứng dụng chạy (ví dụ: 8080)
EXPOSE 8989

# Lệnh khởi chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]