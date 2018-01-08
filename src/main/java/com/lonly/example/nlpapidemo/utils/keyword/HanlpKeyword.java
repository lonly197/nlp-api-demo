package com.lonly.example.nlpapidemo.utils.keyword;

import com.hankcs.hanlp.summary.TextRankKeyword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Slf4j(topic = "Model Load")
@Component
public class HanlpKeyword {

    private TextRankKeyword textRankKeyword;

    public HanlpKeyword() {
        long start = System.currentTimeMillis();
        textRankKeyword = new TextRankKeyword();
        log.info("HanlpKeyword Loaded [{} ms]", System.currentTimeMillis() - start);
    }

    public String extract(String text) {
        return textRankKeyword.getTermAndRank(text).toString();
    }
}
