package com.lonly.example.nlpapidemo.utils.seg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@Component
@Scope("singleton")
public class SegUtils {

    @Autowired
    @Lazy
    public JiebaSeg jiebaSeg;

    @Autowired
    @Lazy
    public HanlpSeg hanlpSeg;

    @Autowired
    @Lazy
    public StanfordSeg stanfordSeg;

    @Autowired
    @Lazy
    public FnlpSeg fnlpSeg;

    @Autowired
    @Lazy
    public ThulcSeg thulcSeg;

    @Autowired
    @Lazy
    public FudanDNNSeg fudanDNNSeg;

    @Autowired
    @Lazy
    public BosonSeg bosonSeg;

}
