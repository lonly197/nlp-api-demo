package com.lonly.example.nlpapidemo.controllers;

import com.lonly.example.nlpapidemo.beans.ParserResult;
import com.lonly.example.nlpapidemo.utils.parser.ParserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parser")
public class ParserController {

    @Autowired(required = false)
    private ParserUtils parserUtils;

    @PostMapping("boson")
    public ParserResult bosonParser(@RequestParam("text") String text) {
        return parserUtils.bosonParser.parser(text);
    }

    @PostMapping("hanlp")
    public ParserResult hanlpParser(@RequestParam("text") String text) {
        return parserUtils.hanlpParser.parser(text);
    }

    @PostMapping("hanlpcrf")
    public ParserResult hanlpCRFParser(@RequestParam("text") String text) {
        return parserUtils.hanlpParser.crfParser(text);
    }

    @PostMapping("hanlpme")
    public ParserResult hanlpMaxEntParser(@RequestParam("text") String text) {
        return parserUtils.hanlpParser.maxEntParser(text);
    }

    @PostMapping("stanford")
    public ParserResult stanfordParser(@RequestParam("text") String text) {
        return parserUtils.stanfordParser.parser(text);
    }

}
