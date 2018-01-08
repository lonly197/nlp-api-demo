package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.beans.TagResult;
import com.lonly.example.nlpapidemo.utils.tag.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagUtils tagUtils;

    @PostMapping("boson")
    public TagResult bosonTag(@RequestParam("text") String text) {
        return tagUtils.bosonTag.tag(text);
    }

    @PostMapping("hanlp")
    public TagResult hanlpTag(@RequestParam("text") String text) {
        return tagUtils.hanlpTag.tag(text);
    }

    @PostMapping("fudan")
    public TagResult fudanTag(@RequestParam("text") String text) {
        return tagUtils.fundanDNNTag.tag(text);
    }

    @PostMapping("stanford")
    public TagResult stanfordTag(@RequestParam("text") String text) {
        return tagUtils.stanfordTag.tag(text);
    }

}
