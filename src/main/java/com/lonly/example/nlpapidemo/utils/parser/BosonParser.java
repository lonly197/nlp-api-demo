package com.lonly.example.nlpapidemo.utils.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lonly.example.nlpapidemo.beans.ParserResult;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Lazy
@Slf4j(topic = "Model Load")
@PropertySource(name = "boson", value = {"classpath:boson.properties"}, encoding = "utf-8")
@Component
public class BosonParser {
    @Value("${AppKey}")
    private String appkey;

    @Value("${ParserUrl}")
    private String url;

    public Map<String, String> parserDict = new HashMap<>();

    public BosonParser(@Value("${ParserDict}") String parserDict) {
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

        this.parserDict.putAll(
                Arrays.stream(parserDict.split(","))
                        .map(String::trim).filter(item -> !item.isEmpty())
                        .map(item -> item.split("\\|", 2))
                        .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1], (k, v) -> v))
        );
        log.info("BosonParser Loaded [{} ms]", System.currentTimeMillis() - start);
    }

//    public String parser(String text) {
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
//        return response != null ? response.getBody().toString() : null;
//    }

    public ParserResult parser(String text) {
        ParserResult result = new ParserResult();
        try {
            HttpResponse<ParserResult[]> response = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("X-Token", appkey)
                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
                    .asObject(ParserResult[].class);
            result = response.getBody()[0];
            // 匹配Parser关系字典
            result.setRole(result.getRole().stream().map(this::getParserName).collect(Collectors.toCollection(ArrayList::new)));
            // 修正Head Index
            List<String> heads = result.getHead();
            result.setHead(
                    IntStream.range(0, heads.size())
                            .mapToObj(i -> Integer.parseInt(heads.get(i)) < 0 ? String.valueOf(i) : heads.get(i))
                            .collect(Collectors.toCollection(ArrayList::new))
            );
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getParserName(String key) {
        return parserDict.isEmpty() ? key : parserDict.getOrDefault(key, "其他");
    }
}
