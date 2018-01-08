package com.lonly.example.nlpapidemo.utils.tag;

import com.lonly.example.nlpapidemo.beans.TagResult;
import com.lonly.example.nlpapidemo.utils.stanford.CoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Slf4j(topic = "Model Load")
@Component
public class StanfordTag {

    @Autowired
    private CoreNLP coreNLP;

    public TagResult tag(String text) {
        TagResult result = new TagResult();
        List<CoreMap> coreMaps = coreNLP.nlp.process(text).get(CoreAnnotations.SentencesAnnotation.class).stream()
                .filter(sent -> sent.size() > 0)
                .flatMap(sent -> sent.get(CoreAnnotations.TokensAnnotation.class).stream())
                .collect(Collectors.toList());
        result.setWord(coreMaps.stream().map(token -> token.get(CoreAnnotations.TextAnnotation.class)).collect(Collectors.toCollection(ArrayList::new)));
        result.setTag(coreMaps.stream().map(token -> token.get(CoreAnnotations.PartOfSpeechAnnotation.class)).collect(Collectors.toCollection(ArrayList::new)));
        return result;
    }
}
