package com.lonly.example.nlpapidemo.utils.sentiment;

import org.springframework.stereotype.Component;

//@Component
public class SentimentUtils {
    private static SentimentUtils ourInstance = new SentimentUtils();

    public static SentimentUtils getInstance() {
        return ourInstance;
    }

    public BosonSentiment bosonSentiment;

    public HanlpSentiment hanlpSentiment;

    private SentimentUtils() {
        System.out.println("SentimentUtils Init Start...");
        bosonSentiment = new BosonSentiment();
        hanlpSentiment = new HanlpSentiment();
        System.out.println("SentimentUtils Init Success!");
    }

}
