package com.lonly.example.nlpapidemo.beans;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ParserResult {
    private ArrayList<String> tag;
    private ArrayList<String> word;
    private ArrayList<String> head;
    private ArrayList<String> role;
}
