package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.utils.seg.SegUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seg")
public class SegController {

    @Autowired(required = false)
    private SegUtils segUtils;

    @PostMapping("jieba")
    public String[] jiebaSegWord(@RequestParam("text") String text) {
        return segUtils.jiebaSeg.seg(text);
    }

    @PostMapping("fnlp")
    public String[] fnlpSegWord(@RequestParam("text") String text) {
        return segUtils.fnlpSeg.seg(text);
    }

    @PostMapping("thulc")
    public String[] thulcSegWord(@RequestParam("text") String text) {
        return segUtils.thulcSeg.seg(text);
    }

    @PostMapping("stanford")
    public String[] stanfordSegWord(@RequestParam("text") String text) {
        return segUtils.stanfordSeg.seg(text);
    }

    @PostMapping("hanlp")
    public String[] hanlpSegWord(@RequestParam("text") String text) {
        return segUtils.hanlpSeg.seg(text);
    }

    @PostMapping("hanlpcrf")
    public String[] hanlpCRFSegWord(@RequestParam("text") String text) {
        return segUtils.hanlpSeg.segCRF(text);
    }

    @PostMapping("hanlpshort")
    public String[] hanlpShortSegWord(@RequestParam("text") String text) {
        return segUtils.hanlpSeg.segShort(text);
    }

    @PostMapping("hanlpnshort")
    public String[] hanlpNShortSegWord(@RequestParam("text") String text) {
        return segUtils.hanlpSeg.segNShort(text);
    }

    @PostMapping("fudan")
    public String[] fundanSegWord(@RequestParam("text") String text) {
        return segUtils.fudanDNNSeg.seg(text);
    }

    @PostMapping("boson")
    public String[] bosonSegWord(@RequestParam("text") String text) {
        return segUtils.bosonSeg.seg(text);
    }

}
