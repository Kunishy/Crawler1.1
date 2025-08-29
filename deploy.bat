@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo 🚀 開始部署基隆景點爬蟲應用程式...

REM 檢查Docker是否安裝
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker 未安裝，請先安裝 Docker
    pause
    exit /b 1
)

REM 檢查Docker Compose是否安裝
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker Compose 未安裝，請先安裝 Docker Compose
    pause
    exit /b 1
)

REM 檢查Docker是否運行
docker info >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker 未運行，請啟動 Docker 服務
    pause
    exit /b 1
)

echo ✅ Docker 環境檢查完成

REM 停止現有服務
echo 🛑 停止現有服務...
docker-compose down >nul 2>&1

REM 清理舊映像
echo 🧹 清理舊映像...
docker image prune -f >nul 2>&1

REM 構建新映像
echo 🔨 構建應用程式映像...
docker-compose build --no-cache

REM 啟動服務
echo 🚀 啟動服務...
docker-compose up -d

REM 等待服務啟動
echo ⏳ 等待服務啟動...
timeout /t 10 /nobreak >nul

REM 檢查服務狀態
echo 📊 檢查服務狀態...
docker-compose ps

REM 檢查應用程式健康狀態
echo 🏥 檢查應用程式健康狀態...
powershell -Command "try { Invoke-WebRequest -Uri 'http://localhost:8080/actuator/health' -UseBasicParsing | Out-Null; Write-Host '✅ 應用程式部署成功！' } catch { Write-Host '⚠️ 應用程式可能需要更多時間啟動，請稍後檢查' }"

echo.
echo 📋 部署完成！
echo 🌐 應用程式網址: http://localhost:8080
echo 🗄️  MongoDB Express: http://localhost:8081
echo 📊 MongoDB: localhost:27017
echo.
echo 📋 常用命令:
echo   查看日誌: docker-compose logs -f
echo   停止服務: docker-compose down
echo   重啟服務: docker-compose restart
echo   進入容器: docker exec -it crawler-app sh
echo.
pause
