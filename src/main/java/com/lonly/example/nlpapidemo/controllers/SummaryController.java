package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.utils.summary.SummaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
public class SummaryController {

    @Autowired(required = false)
    private SummaryUtils summaryUtils;

    @PostMapping("boson")
    public String bosonSummary(@RequestParam("text") String text,
                               @RequestParam(value = "percent", defaultValue = "0.3", required = false) String percent) {
        return summaryUtils.bosonSummary.extract(text, percent);
    }

    @PostMapping("hanlp")
    public String hanlpSummary(@RequestParam("text") String text,
                               @RequestParam(value = "percent", defaultValue = "0.3", required = false) String percent,
                               @RequestParam(value = "type", defaultValue = "1", required = false) String type) {
        return summaryUtils.hanlpSummary.extract(text, percent, java.util.Optional.ofNullable(type));
    }

    @PostMapping("stanford")
    public String stanfordSummary(@RequestParam("text") String text) {
        return summaryUtils.stanfordSummary.extract(text);
    }

}
