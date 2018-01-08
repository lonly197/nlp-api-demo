package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.utils.classify.ClassifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classify")
public class ClassifyController {

    @Autowired(required = false)
    private ClassifyUtils classifyUtils;

    @PostMapping("boson")
    public String bosonSummary(@RequestParam("text") String text) {
        return classifyUtils.bosonClassify.predict(text);
    }

    @PostMapping("hanlp")
    public String hanlpSummary(@RequestParam("text") String text) {
        return classifyUtils.hanlpClassify.predict(text);
    }


}
