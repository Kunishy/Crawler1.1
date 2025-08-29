package com.example.Crawler.service;

import com.example.Crawler.model.KeelungSightsCrawler;
import com.example.Crawler.model.Sight;
import com.example.Crawler.repository.SightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
public class SightService {
    
    @Autowired
    private SightRepository sightRepository;
    
    @Autowired
    private KeelungSightsCrawler crawler;
    
    /**
     * 根據區域名稱查詢景點
     * @param zone 區域名稱
     * @return 該區域的所有景點
     */
    public List<Sight> getSightsByZone(String zone) {
        return sightRepository.findByZoneRegex(zone);
    }
    
    /**
     * 獲取所有景點
     * @return 所有景點列表
     */
    public List<Sight> getAllSights() {
        return sightRepository.findAll();
    }
    
    /**
     * 根據類別查詢景點
     * @param category 景點類別
     * @return 該類別的所有景點
     */
    public List<Sight> getSightsByCategory(String category) {
        return sightRepository.findByCategory(category);
    }
    
    /**
     * 根據景點名稱查詢景點
     * @param sightName 景點名稱
     * @return 符合名稱的景點
     */
    public List<Sight> getSightsByName(String sightName) {
        return sightRepository.findBySightNameContaining(sightName);
    }
    
    /**
     * 執行爬蟲並儲存資料到資料庫
     * @return 儲存的景點數量
     */
    public int crawlAndSaveSights() {
        // 檢查資料庫是否已有資料
        if (sightRepository.existsBySightNameIsNotNull()) {
            System.out.println("資料庫中已有景點資料，跳過爬蟲執行");
            return 0;
        }
        
        System.out.println("開始執行爬蟲...");
        
        // 定義所有區域
        String[] zones = {"中山", "信義", "仁愛", "中正", "安樂", "七堵", "暖暖"};
        int totalSaved = 0;
        
        for (String zone : zones) {
            try {
                System.out.println("正在爬取 " + zone + " 區的景點資料...");
                Sight[] sights = crawler.getItems(zone);
                
                if (sights != null && sights.length > 0) {
                    List<Sight> sightList = Arrays.asList(sights);
                    sightRepository.saveAll(sightList);
                    totalSaved += sights.length;
                    System.out.println("成功儲存 " + sights.length + " 個 " + zone + " 區景點");
                } else {
                    System.out.println(zone + " 區沒有找到景點資料");
                }
                
                // 避免請求過於頻繁
                Thread.sleep(1000);
                
            } catch (Exception e) {
                System.err.println("爬取 " + zone + " 區資料時發生錯誤: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("爬蟲執行完成，總共儲存 " + totalSaved + " 個景點");
        return totalSaved;
    }
    
    /**
     * 清空所有景點資料
     */
    public void clearAllSights() {
        sightRepository.deleteAll();
        System.out.println("已清空所有景點資料");
    }
    
    /**
     * 獲取資料庫中的景點總數
     * @return 景點總數
     */
    public long getSightCount() {
        return sightRepository.count();
    }
}
