package com.example.Crawler.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;

@Component
public class KeelungSightsCrawler{

    private static final String BASE_URL = "https://www.travelking.com.tw/tourguide/taiwan/keelungcity/";

    public Sight[] getItems(String zoneFilter) {
        //Sight[] sights;
        List<Sight> sights = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(BASE_URL)
            .timeout(15000) // 設置超時時間
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36") // 偽裝成 Chrome 瀏覽器
            .referrer("http://www.google.com") // 設置來源，避免被封鎖
            .get();
            Elements linksElem = doc.select(".box a"); // 選取所有景點連結
            //System.out.println("\nlinksElem:\n" + linksElem);

            for (Element link : linksElem) {
                String detailUrl = link.absUrl("href");
                //System.out.println("\ndetailUrl:\n" + detailUrl);
                Sight sight = parseSight(detailUrl);
                //System.out.println("\nsight:\n" + detailUrl);
                if (sight != null && sight.getZone().contains(zoneFilter+"區")) {
                    sights.add(sight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sights.toArray(new Sight[0]);
    }

    private Sight parseSight(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Sight sight = new Sight();

            // 解析名稱、區域、類別、圖片、地址、描述等資訊
            sight.setSightName(doc.select("h1.h1").text());
            sight.setZone(doc.select("li.bc_last").text());
            sight.setCategory(doc.select("span strong").text());
            sight.setPhotoURL(doc.select("div#point_area meta[itemprop=image]").first().absUrl("content"));
            sight.setDescription(doc.select("div#point_area meta[itemprop=description]").first().attr("content"));
            sight.setAddress(doc.select("div#point_area meta[itemprop=address]").first().attr("content"));
            
            return sight;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}