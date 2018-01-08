package com.lonly.example.nlpapidemo.utils.suggest;

import org.springframework.stereotype.Component;

//@Component
public class SuggestUtils {
    private static SuggestUtils ourInstance = new SuggestUtils();

    public static SuggestUtils getInstance() {
        return ourInstance;
    }

    public BosonSuggest bosonSuggest;

    public HanlpSuggest hanlpSuggest;

    private SuggestUtils() {
        System.out.println("SuggestUtils Init Start...");
        bosonSuggest = new BosonSuggest();
        hanlpSuggest = new HanlpSuggest();
        System.out.println("SuggestUtils Init Success!");
    }

}
