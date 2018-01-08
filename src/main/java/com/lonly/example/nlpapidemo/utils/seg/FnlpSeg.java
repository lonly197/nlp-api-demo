package com.lonly.example.nlpapidemo.utils.seg;

import lombok.extern.slf4j.Slf4j;
import org.fnlp.nlp.cn.CNFactory;
import org.fnlp.util.exception.LoadModelException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Lazy
@Slf4j(topic = "Model Load")
@PropertySource(value = {"classpath:fnlp.properties"}, encoding = "utf-8")
@Component
public class FnlpSeg {
    private CNFactory fnlpSeg;

    public FnlpSeg(@Value("${ModelPath}") String modelPath) {
        try {
            fnlpSeg = CNFactory.getInstance("model/fnlp");
        } catch (LoadModelException e) {
            e.printStackTrace();
        }
    }

    public String[] seg(String text) {
        return fnlpSeg != null ? fnlpSeg.seg(text) : null;
    }
}
