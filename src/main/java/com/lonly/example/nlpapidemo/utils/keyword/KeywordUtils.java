package com.lonly.example.nlpapidemo.utils.keyword;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class KeywordUtils {

    @Lazy
    @Autowired
    public BosonKeyword bosonKeyword;

    @Lazy
    @Autowired
    public HanlpKeyword hanlpKeyword;


}
