package com.lonly.example.nlpapidemo.utils.sentiment;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.lonly.example.nlpapidemo.utils.seg.SegUtils;

import java.util.Properties;

import static com.lonly.example.nlpapidemo.utils.hanlp.ModelUtils.trainOrLoadModel;

public class HanlpSentiment {

    private IClassifier classifier;

    public HanlpSentiment() {
        Properties prop = new Properties();
        try {
            prop.load(HanlpSentiment.class.getResourceAsStream("/hanlp.properties"));
            String corpPath = prop.getProperty("root") + prop.getProperty("SentiCorpPath");
            String modelPath = prop.getProperty("root") + prop.getProperty("SentiModelPath");
            classifier = new NaiveBayesClassifier(trainOrLoadModel(corpPath, modelPath));
        } catch (Exception e) {
            System.out.println("HanlpSentiment Init Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String predict(String text) {
        return classifier.predict(text).toString();
    }
}
