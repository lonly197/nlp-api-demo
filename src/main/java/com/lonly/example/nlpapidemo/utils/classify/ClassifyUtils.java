package com.lonly.example.nlpapidemo.utils.classify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ClassifyUtils {

    @Lazy
    @Autowired
    public BosonClassify bosonClassify;

    @Lazy
    @Autowired
    public HanlpClassify hanlpClassify;

}
