# 第一階段：構建React前端
FROM node:18-alpine AS frontend-build

# 設置工作目錄
WORKDIR /app/frontend

# 複製前端依賴檔案
COPY package*.json ./

# 安裝前端依賴
RUN npm ci

# 複製前端源碼
COPY src ./src
COPY public ./public

# 構建React應用
RUN npm run build

# 第二階段：構建Spring Boot後端
FROM maven:3.9.6-eclipse-temurin-21 AS backend-build

# 設置工作目錄
WORKDIR /app/backend

# 複製後端依賴檔案
COPY pom.xml ./

# 複製後端源碼
COPY src/main ./src/main


# 構建Spring Boot應用
RUN mvn clean package -DskipTests

# 第三階段：運行階段
FROM eclipse-temurin:21-jre

# 設置工作目錄
WORKDIR /app

# 從前端構建階段複製靜態檔案到Spring Boot的static目錄
COPY --from=frontend-build /app/frontend/build ./static

# 從後端構建階段複製JAR檔案
COPY --from=backend-build /app/backend/target/Crawler-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口8080
EXPOSE 8080

# 設置JVM選項
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:+UseContainerSupport"

# 設置應用程式選項
ENV SPRING_PROFILES_ACTIVE="docker"

# 創建非root用戶
RUN groupadd -r appuser && useradd -r -g appuser appuser
USER appuser

# 啟動命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
