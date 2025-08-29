package com.example.Crawler.repository;

import com.example.Crawler.model.Sight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SightRepository extends MongoRepository<Sight, String> {
    
    /**
     * 根據區域名稱查詢景點
     * @param zone 區域名稱
     * @return 該區域的所有景點
     */
    List<Sight> findByZoneContaining(String zone);
    
    /**
     * 根據區域名稱查詢景點（使用自定義查詢）
     * @param zone 區域名稱
     * @return 該區域的所有景點
     */
    @Query("{'zone': {$regex: ?0, $options: 'i'}}")
    List<Sight> findByZoneRegex(String zone);
    
    /**
     * 根據類別查詢景點
     * @param category 景點類別
     * @return 該類別的所有景點
     */
    List<Sight> findByCategory(String category);
    
    /**
     * 根據景點名稱查詢景點
     * @param sightName 景點名稱
     * @return 符合名稱的景點
     */
    List<Sight> findBySightNameContaining(String sightName);
    
    /**
     * 檢查資料庫中是否有景點資料
     * @return 如果有資料返回true，否則返回false
     */
    boolean existsBySightNameIsNotNull();
}
