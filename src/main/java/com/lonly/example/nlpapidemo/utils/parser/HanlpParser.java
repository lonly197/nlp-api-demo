package com.lonly.example.nlpapidemo.utils.parser;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.dependency.CRFDependencyParser;
import com.hankcs.hanlp.dependency.MaxEntDependencyParser;
import com.lonly.example.nlpapidemo.beans.ParserResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Lazy
@PropertySource(value = {"classpath:hanlp.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class HanlpParser {

//    public String parser(String text) {
//        return HanLP.parseDependency(text).toString();
//    }
//
//    public String crfParser(String text) {
//        return CRFDependencyParser.compute(text).toString();
//    }
//
//    public String maxEntParser(String text) {
//        return MaxEntDependencyParser.compute(text).toString();
//    }

    public ParserResult parser(String text) {
        ParserResult result = new ParserResult();
        CoNLLSentence sentence = HanLP.parseDependency(text);
        result.setWord(Arrays.stream(sentence.getWordArray()).map(item -> item.LEMMA).collect(Collectors.toCollection(ArrayList::new)));
        result.setTag(Arrays.stream(sentence.getWordArray()).map(item -> item.POSTAG).collect(Collectors.toCollection(ArrayList::new)));
        result.setRole(Arrays.stream(sentence.getWordArray()).map(item -> item.DEPREL).collect(Collectors.toCollection(ArrayList::new)));
        // 修正Head Index
        List<Integer> heads = Arrays.stream(sentence.getWordArray()).map(item -> item.HEAD.ID).collect(Collectors.toList());
        result.setHead(
                IntStream.range(0, heads.size())
                        .mapToObj(i -> (heads.get(i) - 1) < 0 ? i : heads.get(i) - 1)
                        .map(String::valueOf)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        return result;
    }

    public ParserResult crfParser(String text) {
        ParserResult result = new ParserResult();
        CoNLLSentence sentence = CRFDependencyParser.compute(text);
        result.setWord(Arrays.stream(sentence.getWordArray()).map(item -> item.LEMMA).collect(Collectors.toCollection(ArrayList::new)));
        result.setTag(Arrays.stream(sentence.getWordArray()).map(item -> item.POSTAG).collect(Collectors.toCollection(ArrayList::new)));
        result.setRole(Arrays.stream(sentence.getWordArray()).map(item -> item.DEPREL).collect(Collectors.toCollection(ArrayList::new)));
        // 修正Head Index
        List<Integer> heads = Arrays.stream(sentence.getWordArray()).map(item -> item.HEAD.ID).collect(Collectors.toList());
        result.setHead(
                IntStream.range(0, heads.size())
                        .mapToObj(i -> (heads.get(i) - 1) < 0 ? i : heads.get(i) - 1)
                        .map(String::valueOf)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        return result;
    }

    public ParserResult maxEntParser(String text) {
        ParserResult result = new ParserResult();
        CoNLLSentence sentence = MaxEntDependencyParser.compute(text);
        result.setWord(Arrays.stream(sentence.getWordArray()).map(item -> item.LEMMA).collect(Collectors.toCollection(ArrayList::new)));
        result.setTag(Arrays.stream(sentence.getWordArray()).map(item -> item.POSTAG).collect(Collectors.toCollection(ArrayList::new)));
        result.setRole(Arrays.stream(sentence.getWordArray()).map(item -> item.DEPREL).collect(Collectors.toCollection(ArrayList::new)));
        // 修正Head Index
        List<Integer> heads = Arrays.stream(sentence.getWordArray()).map(item -> item.HEAD.ID).collect(Collectors.toList());
        result.setHead(
                IntStream.range(0, heads.size())
                        .mapToObj(i -> (heads.get(i) - 1) < 0 ? i : heads.get(i) - 1)
                        .map(String::valueOf)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        return result;
    }
}
