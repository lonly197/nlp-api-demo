package com.lonly.example.nlpapidemo.utils.parser;

import com.lonly.example.nlpapidemo.beans.ParserResult;
import com.lonly.example.nlpapidemo.utils.stanford.CoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;
import edu.stanford.nlp.util.CoreMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Lazy
@Slf4j(topic = "Model Load")
@Component
public class StanfordParser {

    @Autowired
    private CoreNLP coreNLP;

    public ParserResult parser(String text) {
        ParserResult result = new ParserResult();
        List<CoreMap> sentences = coreNLP.nlp.process(text).get(CoreAnnotations.SentencesAnnotation.class).stream()
                .filter(sent -> sent.size() > 0)
                .collect(Collectors.toList());

        List<CoreMap> tokens = sentences.stream()
                .flatMap(sent -> sent.get(CoreAnnotations.TokensAnnotation.class).stream())
                .collect(Collectors.toList());

        ChineseTreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        // Depend List
        List<TypedDependency> tds = sentences.stream()
                .map(sent -> sent.get(TreeCoreAnnotations.TreeAnnotation.class))
                .map(gsf::newGrammaticalStructure)
                .map(gs -> gs.typedDependenciesCCprocessed().stream())
                .flatMap(tdl -> tdl)
                .collect(Collectors.toList());
        // words
        ArrayList<String> words = tokens.stream().map(token -> token.get(CoreAnnotations.TextAnnotation.class)).collect(Collectors.toCollection(ArrayList::new));
        result.setWord(words);
        // tags
        ArrayList<String> tags = tokens.stream().map(token -> token.get(CoreAnnotations.PartOfSpeechAnnotation.class)).collect(Collectors.toCollection(ArrayList::new));
        result.setTag(tags);
        // roles
        result.setRole(
                IntStream.range(0, words.size())
                        .mapToObj(i -> getRole(i, words, tds))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        // heads
        result.setHead(
                IntStream.range(0, words.size())
                        .mapToObj(i -> getHead(i, words, tds))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
//        result.setHead(tds.stream().map(tdl -> tdl.gov().beginPosition()).map(String::valueOf).collect(Collectors.toCollection(ArrayList::new)));
        return result;
    }

    private String getRole(int idx, ArrayList<String> words, List<TypedDependency> tds) {
        return tds.stream()
                .filter(item -> item.dep().word().equals(words.get(idx)))
                .findFirst()
                .map(td -> getReln(td.reln().toString()))
                .orElseGet(() -> "");
    }

    private String getReln(String reln) {
        return !reln.contains(":") ? reln : reln.substring(reln.indexOf(":") + 1);
    }

    private String getHead(int idx, ArrayList<String> words, List<TypedDependency> tds) {
        return tds.stream()
                .filter(item -> item.dep().word().equals(words.get(idx)))
                .findFirst()
                .map(td -> words.indexOf(td.gov().word()))
                .map(i -> i > 0 ? i : idx)
                .map(String::valueOf)
                .orElseGet(() -> String.valueOf(idx));
    }
}
