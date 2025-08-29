package com.example.Crawler.controller;

import com.example.Crawler.model.Sight;
import com.example.Crawler.service.SightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/SightAPI")
@CrossOrigin(origins = "*")
public class CrawlerController {

    @Autowired
    private SightService sightService;

    /**
     * 根據區域名稱查詢景點
     */
    @GetMapping
    public ResponseEntity<List<Sight>> getSightsByZone(@RequestParam("zone") String zone) {
        List<Sight> sights = sightService.getSightsByZone(zone);
        if (sights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sights);
    }
    
    /**
     * 獲取所有景點
     */
    @GetMapping("/all")
    public ResponseEntity<List<Sight>> getAllSights() {
        List<Sight> sights = sightService.getAllSights();
        return ResponseEntity.ok(sights);
    }
    
    /**
     * 根據類別查詢景點
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Sight>> getSightsByCategory(@PathVariable String category) {
        List<Sight> sights = sightService.getSightsByCategory(category);
        if (sights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sights);
    }
    
    /**
     * 根據景點名稱查詢景點
     */
    @GetMapping("/search")
    public ResponseEntity<List<Sight>> searchSightsByName(@RequestParam("name") String name) {
        List<Sight> sights = sightService.getSightsByName(name);
        if (sights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sights);
    }
    
    /**
     * 手動執行爬蟲並儲存資料
     */
    @PostMapping("/crawl")
    public ResponseEntity<String> crawlAndSaveSights() {
        try {
            int count = sightService.crawlAndSaveSights();
            return ResponseEntity.ok("爬蟲執行完成，儲存了 " + count + " 個景點");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("爬蟲執行失敗: " + e.getMessage());
        }
    }
    
    /**
     * 清空所有景點資料
     */
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllSights() {
        try {
            sightService.clearAllSights();
            return ResponseEntity.ok("已清空所有景點資料");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("清空資料失敗: " + e.getMessage());
        }
    }
    
    /**
     * 獲取資料庫統計資訊
     */
    @GetMapping("/stats")
    public ResponseEntity<String> getStats() {
        long count = sightService.getSightCount();
        return ResponseEntity.ok("資料庫中共有 " + count + " 個景點");
    }
}
