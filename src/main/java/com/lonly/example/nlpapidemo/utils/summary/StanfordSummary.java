package com.lonly.example.nlpapidemo.utils.summary;

import com.lonly.example.nlpapidemo.core.stanford.CoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Lazy
@Slf4j(topic = "Model Load")
@Component
@Scope("singleton")
public class StanfordSummary {

    @Lazy
    @Autowired
    private CoreNLP coreNLP;

    public String extract(String text) {
        return coreNLP.nlp.process(text)
                .get(CoreAnnotations.SentencesAnnotation.class)
                .stream()
                .filter(sent -> sent.size() > 0)
                .flatMap(sent -> sent.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class).stream())
                .map(triple -> triple.confidence + "\t" + triple.subjectLemmaGloss() + "\t" + triple.relationLemmaGloss() + "\t" + triple.objectLemmaGloss())
                .collect(Collectors.joining("\n"));
    }
}
