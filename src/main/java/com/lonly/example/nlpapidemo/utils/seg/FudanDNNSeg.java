package com.lonly.example.nlpapidemo.utils.seg;

import cn.edu.fudan.flow.WordSegmentor;
import cn.edu.fudan.lang.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Lazy
@Slf4j(topic = "Model Load")
@PropertySource(value = {"classpath:fudandnn.properties"}, encoding = "utf-8")
@Component
@Scope("singleton")
public class FudanDNNSeg {
    private WordSegmentor fudanDNNSeg;

    public FudanDNNSeg(@Value("${Root}") String rootPath, @Value("${PreprocessConfPath}") String preprocessFile, @Value("${WordSegmentorConfPath}") String wordSegmentorFile) {
        fudanDNNSeg = new WordSegmentor(rootPath, preprocessFile, wordSegmentorFile);
    }

    public String[] seg(String text) {
        return fudanDNNSeg.segmentation(text).getConstriants().split(" ");
    }

    public List<Item> segToList(String text) {
        return fudanDNNSeg.segmentation(text).getItemList();
    }
}
