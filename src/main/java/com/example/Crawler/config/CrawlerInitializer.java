package com.example.Crawler.config;

import com.example.Crawler.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CrawlerInitializer implements ApplicationRunner {
    
    @Autowired
    private SightService sightService;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=== Spring Boot 應用啟動完成，開始初始化景點資料 ===");
        
        try {
            // 檢查資料庫中是否已有景點資料
            long existingCount = sightService.getSightCount();
            
            if (existingCount > 0) {
                System.out.println("資料庫中已有 " + existingCount + " 個景點資料，跳過爬蟲執行");
            } else {
                System.out.println("資料庫中沒有景點資料，開始執行爬蟲...");
                
                // 執行爬蟲並儲存資料
                int savedCount = sightService.crawlAndSaveSights();
                
                if (savedCount > 0) {
                    System.out.println("✅ 爬蟲執行成功！共儲存 " + savedCount + " 個景點到 MongoDB");
                } else {
                    System.out.println("⚠️ 爬蟲執行完成，但沒有儲存任何景點資料");
                }
            }
            
            // 顯示最終統計
            long finalCount = sightService.getSightCount();
            System.out.println("📊 資料庫統計：總共有 " + finalCount + " 個景點");
            System.out.println("=== 景點資料初始化完成 ===");
            
        } catch (Exception e) {
            System.err.println("❌ 爬蟲執行過程中發生錯誤: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=== 景點資料初始化失敗 ===");
        }
    }
}
