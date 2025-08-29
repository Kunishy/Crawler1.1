# 第一階段：構建階段
FROM maven:3.9.6-eclipse-temurin-21 AS build

# 設置工作目錄
WORKDIR /home/app

# 複製pom.xml和源碼
COPY pom.xml .
COPY src ./src

# 構建應用程式，創建JAR檔案
RUN mvn clean package -DskipTests

# 第二階段：運行階段
FROM eclipse-temurin:21-jre

# 設置工作目錄
WORKDIR /home/app

# 從構建階段複製JAR檔案
COPY --from=build /home/app/target/Crawler-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口8080（Spring Boot默認端口）
EXPOSE 8080

# 設置JVM選項以優化容器運行
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# 設置應用程式選項
ENV SPRING_PROFILES_ACTIVE="docker"

# 創建非root用戶以提高安全性
RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser

# 定義容器啟動命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
