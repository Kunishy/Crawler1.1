#!/bin/bash

echo "🔨 開始構建React前端..."

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

echo "✅ Node.js 環境檢查完成"

# 安裝依賴
echo "📦 安裝前端依賴..."
npm install

# 構建React應用
echo "🔨 構建React應用..."
npm run build

if [ $? -eq 0 ]; then
    echo "✅ React前端構建成功！"
    echo "📁 構建檔案位於: build/"
else
    echo "❌ React前端構建失敗"
    exit 1
fi
