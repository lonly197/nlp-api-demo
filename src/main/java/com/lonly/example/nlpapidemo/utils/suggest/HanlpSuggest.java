package com.lonly.example.nlpapidemo.utils.suggest;

import com.hankcs.hanlp.mining.word2vec.WordVectorModel;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class HanlpSuggest {

    private WordVectorModel wordVectorModel;

    public HanlpSuggest() {
        Properties prop = new Properties();
        try {
            LocalTime startTime = LocalTime.now();
            prop.load(HanlpSuggest.class.getResourceAsStream("/hanlp.properties"));
            String modelPath = prop.getProperty("Word2vcModelPath");
            wordVectorModel = new WordVectorModel(modelPath);
            System.out.println(String.format("Loading Word Vector Model from %s [%s]", modelPath, Duration.between(startTime, LocalTime.now()).toMinutes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String predict(String text) {
        return wordVectorModel.nearest(text, 10).toString();
    }
}
