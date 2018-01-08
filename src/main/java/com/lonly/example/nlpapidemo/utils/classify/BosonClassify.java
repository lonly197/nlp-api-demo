package com.lonly.example.nlpapidemo.utils.classify;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@Lazy
@PropertySource(value = {"classpath:boson.properties"}, encoding = "utf-8")
@Component
public class BosonClassify {

    @Value("${AppKey}")
    private String appkey;

    @Value("${KeywordUrl}")
    private String url;

    private HashMap<String, String> classifyDict = new HashMap<>();

    public BosonClassify(@Value("${ClassifyDict}") String classStr) {
        classifyDict.putAll(
                Arrays.stream(classStr.split(","))
                        .map(String::trim).filter(item -> !item.isEmpty())
                        .map(item -> item.split("\\|", 2))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1], (k, v) -> v))
        );
    }

    public String predict(String text) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("X-Token", appkey)
                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
                    .asJson();
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String classes = response.getBody().getArray().get(0).toString();
        return classifyDict.getOrDefault(classes, "");
    }
}
