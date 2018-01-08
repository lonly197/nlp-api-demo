package com.lonly.example.nlpapidemo.utils.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class TagUtils {

    @Autowired
    @Lazy
    public BosonTag bosonTag;

    @Autowired
    @Lazy
    public HanlpTag hanlpTag;

    @Autowired
    @Lazy
    public FudanDNNTag fudanDNNTag;

    @Autowired
    @Lazy
    public StanfordTag stanfordTag;

}
