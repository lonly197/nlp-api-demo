package com.lonly.example.nlpapidemo.utils.seg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.thunlp.thulac.Thulac;
import org.thunlp.thulac.io.IInputProvider;
import org.thunlp.thulac.io.StringOutputHandler;
import org.thunlp.thulac.util.IOUtils;

import java.io.IOException;

@Lazy
@Slf4j(topic = "Model Load")
@PropertySource(value = {"classpath:thulc.properties"}, encoding = "utf-8")
@Component
public class ThulcSeg {
    @Value("${ModelPath}")
    private String modelPath;

    public String[] seg(String text) {
        StringOutputHandler outputProvider = IOUtils.outputToString();
        IInputProvider inputProvider = IOUtils.inputFromString(text);
        try {
            Thulac.split(modelPath, '_', (String) null, false, true, false, inputProvider, outputProvider);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String words = outputProvider.getString();
        return words.split(" ");
    }
}
