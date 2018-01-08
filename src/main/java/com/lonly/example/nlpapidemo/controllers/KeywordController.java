package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.utils.keyword.KeywordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keyword")
public class KeywordController {

    @Autowired(required = false)
    @Lazy
    private KeywordUtils keywordUtils;

    @PostMapping("boson")
    public String bosonParser(@RequestParam("text") String text) {
        return keywordUtils.bosonKeyword.extract(text);
    }

    @PostMapping("hanlp")
    public String hanlpParser(@RequestParam("text") String text) {
        System.out.println("keywordUtils is null ? :" + (keywordUtils==null));
        return keywordUtils.hanlpKeyword.extract(text);
    }


}
