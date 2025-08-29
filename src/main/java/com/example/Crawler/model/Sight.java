package com.example.Crawler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

// JavaBean 類別
@Document(collection = "sights")
public class Sight {
    @Id
    private String id;
    
    @Field("sightName")
    private String sightName;
    
    @Field("zone")
    private String zone;
    
    @Field("category")
    private String category;
    
    @Field("photoURL")
    private String photoURL;
    
    @Field("address")
    private String address;
    
    @Field("description")
    private String description;

    // 預設建構函數
    public Sight() {}
    
    // 帶參數建構函數
    public Sight(String sightName, String zone, String category, String photoURL, String address, String description) {
        this.sightName = sightName;
        this.zone = zone;
        this.category = category;
        this.photoURL = photoURL;
        this.address = address;
        this.description = description;
    }

    // Getter 和 Setter
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getSightName() { 
        return sightName; 
    }
    public void setSightName(String sightName) { 
        this.sightName = sightName; 
    }

    public String getZone() { 
        return zone; 
    }
    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhotoURL() { 
        return photoURL;
    }
    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 獲取備用圖片URL列表
     */
    public static String[] getFallbackImages() {
        return new String[] {
            "https://via.placeholder.com/400x300/cccccc/666666?text=圖片無法載入",
            "https://via.placeholder.com/400x300/e3f2fd/1976d2?text=景點圖片",
            "https://via.placeholder.com/400x300/f3e5f5/7b1fa2?text=基隆景點",
            "https://via.placeholder.com/400x300/e8f5e8/388e3c?text=旅遊景點"
        };
    }

    public String toString() {
        return "SightName: " + sightName + "\n" +
               "Zone: " + zone + "\n" +
               "Category: " + category + "\n" +
               "PhotoURL: " + photoURL + "\n" +
               "Description: " + description + "\n" +
               "Address: " + address + "\n";
    }
}