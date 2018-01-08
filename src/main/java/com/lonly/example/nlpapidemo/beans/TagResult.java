package com.lonly.example.nlpapidemo.beans;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TagResult {
    private ArrayList<String> word;
    private ArrayList<String> tag;
}
