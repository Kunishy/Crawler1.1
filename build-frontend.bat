@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

echo 🔨 開始構建React前端...

REM 檢查Node.js是否安裝
node --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Node.js 未安裝，請先安裝 Node.js
    pause
    exit /b 1
)

REM 檢查npm是否安裝
npm --version >nul 2>&1
if errorlevel 1 (
    echo ❌ npm 未安裝，請先安裝 npm
    pause
    exit /b 1
)

echo ✅ Node.js 環境檢查完成

REM 安裝依賴
echo 📦 安裝前端依賴...
npm install

REM 構建React應用
echo 🔨 構建React應用...
npm run build

if errorlevel 1 (
    echo ❌ React前端構建失敗
    pause
    exit /b 1
) else (
    echo ✅ React前端構建成功！
    echo 📁 構建檔案位於: build/
)

pause
