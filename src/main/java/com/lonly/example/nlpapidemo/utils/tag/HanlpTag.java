package com.lonly.example.nlpapidemo.utils.tag;

import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.lonly.example.nlpapidemo.beans.TagResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Lazy
@PropertySource(value = {"classpath:hanlp.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class HanlpTag {
    private Segment hanlpTag;

    private Map<String, String> tagDict = new HashMap<>();

    private Map<String, String> tagSpecialDict = new HashMap<>();

    public HanlpTag(@Value("${root}") String rootPath, @Value("${TagDictionaryPath}") String tagDictPath, @Value("${TagSpecialDictionaryPath}") String tagSpecialDictPath) {
        long start = System.currentTimeMillis();
        hanlpTag = new NShortSegment()
                .enableAllNamedEntityRecognize(true);
        try {
            tagDict.putAll(
                    Files.lines(Paths.get(rootPath, tagDictPath))
                            .map(String::trim).filter(item -> !item.isEmpty())
                            .map(line -> line.split("\t", 2))
                            .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1], (k, v) -> v))
            );
            tagSpecialDict.putAll(
                    Files.lines(Paths.get(rootPath, tagSpecialDictPath))
                            .map(String::trim).filter(item -> !item.isEmpty())
                            .map(line -> line.split("\t", 2))
                            .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1], (k, v) -> v))
            );
        } catch (IOException e) {
            log.error("HanlpTag Init Error:", e);
        }
        log.info("HanlpTag Loaded [{} ms]", System.currentTimeMillis() - start);
    }

//    public String tag(String text) {
//        return hanlpTag.seg(text).toString();
//    }

    public TagResult tag(String text) {
        TagResult result = new TagResult();
        List<Term> terms = hanlpTag.seg(text);
        result.setWord(terms.stream().map(item -> item.word).collect(Collectors.toCollection(ArrayList::new)));
        result.setTag(terms.stream().map(item -> getTagName(item.nature.name())).collect(Collectors.toCollection(ArrayList::new)));
        return result;
    }

    private String getTagName(String key) {
        return tagDict.isEmpty() ?
                key :
                tagSpecialDict.containsKey(key) ?
                        tagSpecialDict.getOrDefault(key, "其他") :
                        tagDict.getOrDefault(key.substring(0, 1), "其他");
    }
}
