# Sử dụng image Java chính thức
FROM openjdk:17-slim

# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar từ máy host vào container
COPY target/*.jar app.jar

# Cổng app chạy
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]