package com.lonly.example.nlpapidemo.utils.seg;

import com.lonly.example.nlpapidemo.common.stanford.CoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Lazy
@Slf4j(topic = "Model Load")
@Component
public class StanfordSeg {

    @Autowired
    private CoreNLP coreNLP;

    public String[] seg(String text) {
        return coreNLP.nlp.process(text).get(CoreAnnotations.SentencesAnnotation.class).stream()
                .filter(sent -> sent.size() > 0)
                .flatMap(sent -> sent.get(CoreAnnotations.TokensAnnotation.class).stream())
                .map(token -> token.get(CoreAnnotations.TextAnnotation.class)).collect(Collectors.toList()).toArray(new String[0]);
    }
}
