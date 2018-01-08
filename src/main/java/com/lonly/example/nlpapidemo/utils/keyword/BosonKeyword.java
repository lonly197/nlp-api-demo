package com.lonly.example.nlpapidemo.utils.keyword;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Lazy
@PropertySource(value = {"classpath:boson.properties"}, encoding = "utf-8")
@Component
public class BosonKeyword {

    @Value("${AppKey}")
    private String appkey;

    @Value("${KeywordUrl}")
    private String url;

    public String extract(String text) {
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
        return response.getBody().toString();
//        return  ((JSONObject)response.getBody().getArray().get(0)).getJSONArray("word").join(" ").replaceAll("\"", "").split(" ");
    }
}
