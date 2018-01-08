package com.lonly.example.nlpapidemo.utils.stanford;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Lazy
@PropertySource(value = {"classpath:boson.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
@Scope("singleton")
public class CoreNLP {
    public StanfordCoreNLP nlp;

    public CoreNLP() {
        long start = System.currentTimeMillis();
        nlp = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
        log.info("StanfordCoreNLP Loaded [{} ms]", System.currentTimeMillis() - start);
    }
}
