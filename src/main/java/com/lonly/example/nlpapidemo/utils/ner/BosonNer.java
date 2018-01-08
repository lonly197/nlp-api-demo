package com.lonly.example.nlpapidemo.utils.ner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lonly.example.nlpapidemo.beans.NerResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Lazy
@Slf4j(topic = "Model Load")
@PropertySource(value = {"classpath:boson.properties"}, encoding = "utf-8")
@Component
public class BosonNer {

    @Value("${AppKey}")
    private String appkey;

    @Value("${NerUrl}")
    private String url;

    public Map<String, String> nerDict = new HashMap<>();

    public BosonNer(@Value("${NerDict}") String nerStr) {
        long start = System.currentTimeMillis();
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        nerDict.putAll(
                Arrays.stream(nerStr.split(","))
                        .map(String::trim).filter(item -> !item.isEmpty())
                        .map(item -> item.split("\\|", 2))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1], (k, v) -> v))
        );
        log.info("BosonNer Loaded [{} ms]", System.currentTimeMillis() - start);
    }

//    public String ner(String text) {
//        String result = "";
//        try {
//            HttpResponse<NerResult[]> response = Unirest.post(url)
//                    .header("Accept", "application/json")
//                    .header("Content-Type", "application/json")
//                    .header("X-Token", appkey)
//                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
//                    .asObject(NerResult[].class);
//            NerResult ners = response.getBody()[0];
//            List<String> words = ners.getWord();
//            result = ners.getEntity().stream().collect(Collectors.toMap(p->p[2],p->words.get(Integer.parseInt(p[0])), (k,v)->String.join(",",k,v))).toString();
//        } catch (UnirestException | UnsupportedEncodingException e) {
//            log.error("BosonNer Ner Errror:", e);
//        }
//        return result;
//    }

    public NerResult ner(String text) {
        NerResult result = new NerResult();
        try {
            HttpResponse<NerResult[]> response = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("X-Token", appkey)
                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
                    .asObject(NerResult[].class);
            result = response.getBody()[0];
            result.setEntity(
                    result.getEntity().stream()
                            .map(item -> new String[]{item[0], item[1], getNerName(item[2])})
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        } catch (UnirestException | UnsupportedEncodingException e) {
            log.error("BosonNer Ner Errror:", e);
        }
        return result;
    }

    private String getNerName(String key) {
        return nerDict.isEmpty() ? key : nerDict.getOrDefault(key, "其他");
    }

}
