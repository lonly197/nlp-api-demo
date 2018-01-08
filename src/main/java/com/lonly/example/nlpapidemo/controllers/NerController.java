package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.beans.NerResult;
import com.lonly.example.nlpapidemo.utils.ner.NerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ner")
public class NerController {

    @Autowired(required = false)
    private NerUtils nerUtils;

    @PostMapping("boson")
    public NerResult bosonNer(@RequestParam("text") String text) {
        return nerUtils.bosonNer.ner(text);
    }

    @PostMapping("hanlp")
    public NerResult hanlpNer(@RequestParam("text") String text) {
        return nerUtils.hanlpNer.ner(text);
    }

    @PostMapping("fudan")
    public NerResult fudanNer(@RequestParam("text") String text) {
        return nerUtils.fundanDNNNer.ner(text);
    }

}
