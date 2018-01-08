package com.lonly.example.nlpapidemo.utils.tag;

import cn.edu.fudan.flow.PosTagger;
import cn.edu.fudan.lang.Item;
import com.lonly.example.nlpapidemo.beans.TagResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Lazy
@Slf4j(topic = "Model Load")
@Component
@Scope("singleton")
@PropertySource(value = {"classpath:fudandnn.properties"}, encoding = "utf-8")
public class FundanDNNTag {
    private PosTagger posTagger;

    private Map<String, String> tagDict = new HashMap<>();

    public FundanDNNTag(@Value("${PosTaggerDictionary}") String tagDictPath, @Value("${Root}") String rootPath, @Value("${PreprocessConfPath}") String preprocessFile, @Value("${PosTaggerConfPath}") String posTaggerFile) {
        long start = System.currentTimeMillis();
        posTagger = new PosTagger(rootPath, rootPath + preprocessFile, rootPath + posTaggerFile);
        try {
            tagDict.putAll(
                    Files.lines(Paths.get(rootPath, tagDictPath))
                            .map(String::trim).filter(item -> !item.isEmpty())
                            .map(line -> line.split("\t", 2))
                            .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1], (k, v) -> v))
            );
        } catch (IOException e) {
            log.error("FundanDNNTag Init Error:", e);
        }
        log.info("FundanDNNTag Loaded [{} ms]", System.currentTimeMillis() - start);
    }

    public List<Item> tagToList(String text) {
        return posTagger.posTagging(text).getItemList();
    }

    public TagResult tag(String text) {
        TagResult result = new TagResult();
        String[] tags = posTagger.posTagging(text).getConstriants().split(" ");
        result.setWord(Arrays.stream(tags).map(String::trim).filter(item -> !item.isEmpty() && item.contains("/")).map(item -> item.split("/", 2)[0]).collect(Collectors.toCollection(ArrayList::new)));
        result.setTag(Arrays.stream(tags).map(String::trim).filter(item -> !item.isEmpty() && item.contains("/")).map(item -> getTagName(item.split("/", 2)[1])).collect(Collectors.toCollection(ArrayList::new)));
        return result;
    }

    private String getTagName(String key) {
        return tagDict.isEmpty() ? key : tagDict.getOrDefault(key, "其他");
    }
}
