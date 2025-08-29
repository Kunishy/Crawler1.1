package com.example.Crawler.model;

// Compile: javac -d . -classpath .;jsoup-1.19.1.jar *.java
// Execute: java -classpath .;jsoup-1.19.1.jar CrawlerTest

public class CrawlerTest {

	public static void main(String[] args) {
        KeelungSightsCrawler crawler = new KeelungSightsCrawler();

        Sight[] sights = crawler.getItems("七堵");
        System.out.println(sights);
        for (Sight s: sights) { 
            System.out.println(s); 
        } 
		
	}
}
