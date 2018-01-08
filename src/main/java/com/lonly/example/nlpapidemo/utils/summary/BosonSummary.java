package com.lonly.example.nlpapidemo.utils.summary;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Lazy
@PropertySource(value = {"classpath:boson.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class BosonSummary {

    @Value("${AppKey}")
    private String appkey;

    @Value("${SummaryUrl}")
    private String url;

//    public String extract(String text) {
//        HttpResponse<String> response = null;
//        try {
//            response = Unirest.post(url)
//                    .header("Accept", "application/json")
//                    .header("Content-Type", "application/json")
//                    .header("X-Token", appkey)
//                    .body(String.format("{\"content\": \"%s\"}", text).getBytes("UTF-8"))
//                    .asString();
//        } catch (UnirestException | UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return response.getBody().toString();
//    }

    public String extract(String text, String percent) {
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(url)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("X-Token", appkey)
                    .body(String.format("{\"content\": \"%s\", \"percentage\": %s}", text.replaceAll("\\n",""), percent).getBytes("UTF-8"))
                    .asString();
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response.getBody().toString();
    }
}
