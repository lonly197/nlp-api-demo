package com.lonly.example.nlpapidemo.utils.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ParserUtils {

    @Autowired
    @Lazy
    public BosonParser bosonParser;

    @Autowired
    @Lazy
    public HanlpParser hanlpParser;

    @Autowired
    @Lazy
    public StanfordParser stanfordParser;

}
