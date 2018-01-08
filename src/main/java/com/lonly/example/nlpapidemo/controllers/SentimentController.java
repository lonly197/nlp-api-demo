package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.utils.sentiment.SentimentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    @Autowired(required = false)
    private SentimentUtils sentimentUtils;

    @PostMapping("boson")
    public String bosonSentiment(@RequestParam("text") String text) {
        return sentimentUtils.bosonSentiment.predict(text);
    }

    @PostMapping("hanlp")
    public String hanlpSentiment(@RequestParam("text") String text) {
        return sentimentUtils.hanlpSentiment.predict(text);
    }


}
