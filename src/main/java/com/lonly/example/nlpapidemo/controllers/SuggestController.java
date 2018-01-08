package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.utils.suggest.SuggestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suggest")
public class SuggestController {

    @Autowired(required = false)
    private SuggestUtils suggestUtils;

    @PostMapping("boson")
    public String bosonParser(@RequestParam("text") String text) {
        return suggestUtils.bosonSuggest.predict(text);
    }

    @PostMapping("hanlp")
    public String hanlpParser(@RequestParam("text") String text) {
        return suggestUtils.hanlpSuggest.predict(text);
    }


}
