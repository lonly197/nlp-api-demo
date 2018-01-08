package com.lonly.example.nlpapidemo.utils.summary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SummaryUtils {
    @Lazy
    @Autowired
    public BosonSummary bosonSummary;

    @Lazy
    @Autowired
    public HanlpSummary hanlpSummary;

    @Lazy
    @Autowired
    public StanfordSummary stanfordSummary;

}
