package com.lonly.example.nlpapidemo.utils.ner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class NerUtils {

    @Autowired
    @Lazy
    public BosonNer bosonNer;

    @Autowired
    @Lazy
    public HanlpNer hanlpNer;

    @Autowired
    @Lazy
    public FudanDNNNer fudanDNNNer;

}
