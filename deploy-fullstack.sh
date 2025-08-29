#!/bin/bash

# 基隆景點爬蟲應用程式 - 全端Docker部署腳本

set -e

echo "🚀 開始部署基隆景點爬蟲應用程式（全端版本）..."

# 檢查Docker是否安裝
if ! command -v docker &> /dev/null; then
    echo "❌ Docker 未安裝，請先安裝 Docker"
    exit 1
fi

# 檢查Docker Compose是否安裝
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose 未安裝，請先安裝 Docker Compose"
    exit 1
fi

# 檢查Node.js是否安裝
if ! command -v node &> /dev/null; then
    echo "❌ Node.js 未安裝，請先安裝 Node.js"
    exit 1
fi

# 檢查npm是否安裝
if ! command -v npm &> /dev/null; then
    echo "❌ npm 未安裝，請先安裝 npm"
    exit 1
fi

# 檢查Docker是否運行
if ! docker info &> /dev/null; then
    echo "❌ Docker 未運行，請啟動 Docker 服務"
    exit 1
fi

echo "✅ 環境檢查完成"

# 構建React前端
echo "🔨 構建React前端..."
if [ ! -d "build" ] || [ ! -f "build/index.html" ]; then
    echo "📦 安裝前端依賴..."
    npm install
    
    echo "🔨 構建React應用..."
    npm run build
    
    if [ ! -f "build/index.html" ]; then
        echo "❌ React前端構建失敗"
        exit 1
    fi
    echo "✅ React前端構建成功"
else
    echo "✅ React前端已構建，跳過構建步驟"
fi

# 停止現有服務
echo "🛑 停止現有服務..."
docker-compose down 2>/dev/null || true

# 清理舊映像
echo "🧹 清理舊映像..."
docker image prune -f 2>/dev/null || true

# 構建Docker映像
echo "🔨 構建Docker映像..."
docker-compose build --no-cache

# 啟動服務
echo "🚀 啟動服務..."
docker-compose up -d

# 等待服務啟動
echo "⏳ 等待服務啟動..."
sleep 15

# 檢查服務狀態
echo "📊 檢查服務狀態..."
docker-compose ps

# 檢查應用程式健康狀態
echo "🏥 檢查應用程式健康狀態..."
if curl -f http://localhost:8080/actuator/health 2>/dev/null; then
    echo "✅ 應用程式部署成功！"
    echo "🌐 前端應用程式: http://localhost:8080"
    echo "🔌 後端API: http://localhost:8080/SightAPI"
    echo "🗄️  MongoDB Express: http://localhost:8081"
    echo "📊 MongoDB: localhost:27017"
else
    echo "⚠️ 應用程式可能需要更多時間啟動，請稍後檢查"
    echo "📋 查看日誌: docker-compose logs -f crawler-app"
fi

echo ""
echo "📋 常用命令:"
echo "  查看日誌: docker-compose logs -f"
echo "  停止服務: docker-compose down"
echo "  重啟服務: docker-compose restart"
echo "  進入容器: docker exec -it crawler-app sh"
echo "  重新構建前端: ./build-frontend.sh"
echo "  重新構建全端: ./deploy-fullstack.sh"
