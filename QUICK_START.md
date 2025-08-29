# 🚀 快速開始指南

## 使用Docker部署基隆景點爬蟲應用程式

### 方法1：使用部署腳本（推薦）

#### Windows用戶
```cmd
# 雙擊執行
deploy.bat
```

#### Linux/macOS用戶
```bash
# 添加執行權限
chmod +x deploy.sh

# 執行部署腳本
./deploy.sh
```

### 方法2：手動部署

#### 1. 構建Docker映像
```bash
docker build -t crawler-app .
```

#### 2. 使用Docker Compose啟動
```bash
# 啟動所有服務
docker-compose up -d

# 查看服務狀態
docker-compose ps
```

#### 3. 單獨運行應用程式
```bash
docker run -d \
  --name crawler-app \
  -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI=mongodb://your-mongodb-host:27017/keelung_sights \
  crawler-app
```

## 🌐 訪問應用程式

- **主應用程式**: http://localhost:8080
- **MongoDB Express**: http://localhost:8081
- **MongoDB**: localhost:27017

## 📋 常用命令

```bash
# 查看日誌
docker-compose logs -f

# 停止服務
docker-compose down

# 重啟服務
docker-compose restart

# 進入容器
docker exec -it crawler-app sh
```

## 🔧 故障排除

### 端口衝突
如果8080端口被占用，修改`docker-compose.yml`：
```yaml
ports:
  - "8081:8080"  # 改為8081
```

### 記憶體不足
在Docker Desktop設定中增加記憶體限制，或調整JVM參數：
```yaml
environment:
  - JAVA_OPTS=-Xmx1g -Xms512m
```

## 📚 詳細文檔

- [Docker部署指南](DOCKER_DEPLOYMENT.md)
- [響應式設計指南](RWD_DESIGN_GUIDE.md)

## 🆘 需要幫助？

如果遇到問題，請檢查：
1. Docker是否正在運行
2. 端口是否被占用
3. 記憶體是否足夠
4. 查看容器日誌：`docker-compose logs -f`
