package com.lonly.example.nlpapidemo.core.fudandnn;

import cn.edu.fudan.flow.PosTagger;
import com.lonly.example.nlpapidemo.utils.tag.FudanDNNTag;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author lonlyhuang
 */
@Slf4j(topic = "Model Load")
public class CoreTag {
    private static PosTagger posTagger;

    public CoreTag() {

    }

    static {
        long start = System.currentTimeMillis();
        Properties prop = new Properties();
        try {
            prop.load(FudanDNNTag.class.getClassLoader().getResourceAsStream("fudandnn.properties"));
            System.out.println("FudanDNN Props" + prop.isEmpty());
            String rootPath = prop.getProperty("Root");
            String preprocessFile = rootPath + prop.getProperty("PreprocessConfPath");
            String posTaggerFile = rootPath + prop.getProperty("PosTaggerConfPath");
            posTagger = new PosTagger(rootPath, preprocessFile, posTaggerFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("FundanDNNTag Init Error:", e);
        }
        log.info("FudanDNN CoreTag Loaded [{} ms]", System.currentTimeMillis() - start);
    }

    public static List<String> tag(String text) {
        return Arrays.asList(posTagger.posTagging(text).getConstriants().split(" "));
    }
}
