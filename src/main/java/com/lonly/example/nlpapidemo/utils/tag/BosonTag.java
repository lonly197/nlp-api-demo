package com.lonly.example.nlpapidemo.utils.tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lonly.example.nlpapidemo.beans.TagResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
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
@PropertySource(name ="boson", value = {"classpath:boson.properties"}, encoding = "utf-8")
@Component
public class BosonTag {

    @Value("${AppKey}")
    private String appkey;

    @Value("${TagUrl}")
    private String url;

    private Map<String, String> tagDict = new HashMap<>();

    private Map<String, String> tagSpecialDict = new HashMap<>();

    public BosonTag(@Value("${TagDict}") String tagStr, @Value("${TagSpecialDict}") String tagSpecilaStr) {
        long start = System.currentTimeMillis();
        tagDict.putAll(
                Arrays.stream(tagStr.split(","))
                        .map(String::trim).filter(item -> !item.isEmpty())
                        .map(item -> item.split("\\|", 2))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1], (k, v) -> v))
        );
        tagDict.entrySet().stream().forEach(System.out::println);
        tagSpecialDict.putAll(
                Arrays.stream(tagSpecilaStr.split(","))
                        .map(String::trim).filter(item -> !item.isEmpty())
                        .map(item -> item.split("\\|", 2))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1], (k, v) -> v))
        );

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
        log.info("BosonTag Loaded [{} ms]", System.currentTimeMillis() - start);
    }

//    public String tag(String text) {
//        HttpResponse<JsonNode> response = null;
//        try {
//            response = Unirest.post(url)
//                    .header("Accept", "application/json")
//                    .header("Content-Type", "application/json")
//                    .header("X-Token", appkey)
//                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
//                    .asJson();
//        } catch (UnirestException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return response.getBody().toString();
//    }

    public TagResult tag(String text) {
        TagResult result = new TagResult();
        try {
            HttpResponse<TagResult[]> response = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("X-Token", appkey)
                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
                    .asObject(TagResult[].class);
            TagResult repTag = response.getBody()[0];
            result.setWord(repTag.getWord());
            result.setTag(repTag.getTag().stream().map(this::getTagName).collect(Collectors.toCollection(ArrayList::new)));
            repTag.getTag().stream().forEach(System.out::println);
            tagDict.entrySet().stream().forEach(System.out::println);
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
