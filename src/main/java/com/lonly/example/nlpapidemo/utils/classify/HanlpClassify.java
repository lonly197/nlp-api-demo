package com.lonly.example.nlpapidemo.utils.classify;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lonly.example.nlpapidemo.utils.hanlp.ModelUtils.trainOrLoadModel;

@Lazy
@PropertySource(value = {"classpath:hanlp.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class HanlpClassify {

    private IClassifier classifier;

    public HanlpClassify(@Value("${root}") String rootPath, @Value("${ClassifyCorpPath}") String corpPath, @Value("${ClassifyModelPath}") String modelPath) {
        try {
            long start = System.currentTimeMillis();
            classifier = new NaiveBayesClassifier(trainOrLoadModel(rootPath + corpPath, rootPath + modelPath));
            log.info("HanlpClassify Loaded [{} ms]", System.currentTimeMillis() - start);
        } catch (IOException e) {
            log.error("HanlpClassify Load Model Error: ", e);
        }
    }

    public String predict(String text) {
        return classifier != null ? classifier.classify(text) : "";
    }

}
