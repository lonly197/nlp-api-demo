package com.lonly.example.nlpapidemo.utils.ner;

import cn.edu.fudan.flow.NamedIdentityRecognizer;
import com.lonly.example.nlpapidemo.beans.NerResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Lazy
@PropertySource(value = {"classpath:fudandnn.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class FudanDNNNer {
    private NamedIdentityRecognizer nerRecognizer;

    private Map<String, String> nerDict = new HashMap<>();

    public FudanDNNNer(@Value("${Root}") String rootPath, @Value("${PreprocessConfPath}") String preprocessFile, @Value("${NerRecognizerConfPath}") String nerRecogizerFile, @Value("${NerRecognizerDictionary}") String nerDictPath) {
        long start = System.currentTimeMillis();
        nerRecognizer = new NamedIdentityRecognizer(rootPath, preprocessFile, nerRecogizerFile);
        try {
            nerDict.putAll(
                    Files.lines(Paths.get(rootPath, nerDictPath))
                            .map(String::trim).filter(item -> !item.isEmpty())
                            .map(line -> line.split("\t", 2))
                            .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1], (k, v) -> v))
            );
        } catch (IOException e) {
            log.error("FundanDNNNer Init Error:", e);
        }
        log.info("FundanDNNNer Loaded [{} ms]", System.currentTimeMillis() - start);
    }

//    public String ner(String text) {
//        return Arrays.stream(nerRecognizer.recognize(text).getConstriants().split(" "))
//                .map(String::trim)
//                .filter(item -> !item.isEmpty())
//                .filter(item -> !item.contains("/O") && !item.contains("/PUNCTUATION"))
//                .collect(Collectors.toList()).toString();
//    }

    public NerResult ner(String text) {
        NerResult result = new NerResult();
        String[] ners = nerRecognizer.recognize(text).getConstriants().split(" ");
        System.out.println(nerRecognizer.recognize(text).getConstriants());
        ArrayList<String> words = Arrays.stream(ners).map(String::trim).filter(item -> !item.isEmpty()).map(item -> item.split("/", 2)[0]).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> natures = Arrays.stream(ners).map(String::trim).filter(item -> !item.isEmpty()).map(item -> item.split("/", 2)[1]).collect(Collectors.toCollection(ArrayList::new));
        result.setWord(words);
        result.setEntity(
                IntStream.range(0, natures.size())
                        .filter(i -> filterKey(natures.get(i)))
                        .mapToObj(i -> getNerEntity(i, natures.get(i)))
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        return result;
    }

    private Boolean filterKey(String key) {
        return nerDict.isEmpty() || nerDict.containsKey(key);
    }

    private String[] getNerEntity(int index, String key) {
        return new String[]{String.valueOf(index), String.valueOf(index + 1), nerDict.getOrDefault(key, "")};
    }
}
