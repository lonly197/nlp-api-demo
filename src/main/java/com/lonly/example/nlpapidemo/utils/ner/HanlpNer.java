package com.lonly.example.nlpapidemo.utils.ner;

import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.lonly.example.nlpapidemo.beans.NerResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Lazy
@PropertySource(value = {"classpath:hanlp.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class HanlpNer {
    private Segment hanlpNer;

    private HashMap<String, String> nerMap = new HashMap<>();

    public HanlpNer(@Value("${root}") String rootPath, @Value("${NerDictionaryPath}") String nerDictPath) {
        long start = System.currentTimeMillis();
        hanlpNer = new NShortSegment()
                .enableAllNamedEntityRecognize(true);
        try {
            nerMap.putAll(
                    Files.lines(Paths.get(rootPath, nerDictPath))
                            .map(String::trim).filter(item -> !item.isEmpty())
                            .map(line -> line.split("\t", 2))
                            .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1], (k, v) -> v))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("HanlpNer Loaded [{} ms]", System.currentTimeMillis() - start);
    }

//    public String ner(String text){
//        return hanlpNer.seg(text).toString();
//    }

//    public String ner(String text) {
//        return hanlpNer.seg(text).stream()
//                .filter(term -> filterNature(term.nature.name()))
//                .collect(Collectors.toMap(term -> getNerValue(term.nature.name()), term -> term.word, (String k, String v) -> String.join(",",k,v)))
//                .toString();
//    }

    public NerResult ner(String text) {
        NerResult result = new NerResult();
        List<Term> terms = hanlpNer.seg(text);
        result.setTag(terms.stream().map(term -> term.nature.name()).collect(Collectors.toCollection(ArrayList::new)));
        result.setWord(terms.stream().map(term -> term.word).collect(Collectors.toCollection(ArrayList::new)));
        result.setEntity(
                IntStream.range(0, terms.size())
                        .filter(i -> filterNature(terms.get(i).nature.name()))
                        .mapToObj(i -> getNerEntity(i, terms.get(i).nature.name()))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        return result;
    }


    private Boolean filterNature(String nature) {
        return nerMap.isEmpty() || nerMap.keySet().stream().anyMatch(nature::startsWith);
    }

    private String getNerValue(String nature) {
        Optional<Map.Entry<String, String>> nerOptional = nerMap.entrySet().stream().filter(item -> item.getKey().startsWith(nature)).findFirst();
        return nerOptional.isPresent() ? nerOptional.get().getValue() : "";
    }

    private String[] getNerEntity(int index, String nature) {
        return new String[]{String.valueOf(index), String.valueOf(index + 1), getNerValue(nature)};
    }
}
