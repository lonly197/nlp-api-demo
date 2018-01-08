package com.lonly.example.nlpapidemo.utils.seg;

import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Slf4j(topic = "Model Load")
@Component
public class JiebaSeg {
    private JiebaSegmenter jiebaSeg;

    public JiebaSeg(){
        jiebaSeg = new JiebaSegmenter();
    }

    public String[] seg(String text){
        return jiebaSeg.sentenceProcess(text).toArray(new String[0]);
    }
}
