package com.lonly.example.nlpapidemo.beans;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NerResult {
    private ArrayList<String> tag;
    private ArrayList<String> word;
    private ArrayList<String[]> entity;
}
