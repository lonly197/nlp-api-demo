package com.lonly.example.nlpapidemo.utils.suggest;

import com.lonly.example.nlpapidemo.utils.seg.SegUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class BosonSuggest {
    private Properties prop;
    public BosonSuggest(){
        prop = new Properties();
        try {
            prop.load(BosonSuggest.class.getResourceAsStream("/boson.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String predict(String text){
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.post(prop.getProperty("SuggetUrl"))
                    .header("Accept","application/json")
                    .header("Content-Type","application/json")
                    .header("X-Token",prop.getProperty("AppKey"))
                    .body(String.format("\"%s\"", text).getBytes("UTF-8"))
                    .asJson();
        } catch (UnirestException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response.getBody().toString();
//        return  ((JSONObject)response.getBody().getArray().get(0)).getJSONArray("word").join(" ").replaceAll("\"", "").split(" ");
    }
}
