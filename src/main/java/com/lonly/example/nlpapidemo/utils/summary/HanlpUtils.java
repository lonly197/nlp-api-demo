package com.lonly.example.nlpapidemo.utils.summary;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.summary.TextRankSentence;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.lonly.example.nlpapidemo.core.fudandnn.CoreTag;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lonlyhuang
 */
@Slf4j(topic = "HanlpUtils")
public class HanlpUtils {
    private HanlpUtils() {
    }

    public static List<String> extractSummary(String document, Optional<String> type) {
        return getTopSentenceList(document, type.orElse("1"));
    }

    private static List<String> getTopSentenceList(String document, String type) {
        List<String> sentenceList = splitSentence(document, "[，,。:：？?！!；;]");
        List<List<String>> docs = convertSentenceListToDocument(sentenceList, type);
        TextRankSentence textRank = new TextRankSentence(docs);
        int[] topSentence = textRank.getTopSentence(1);

        return Arrays.stream(topSentence).mapToObj(sentenceList::get).collect(Collectors.toCollection(LinkedList::new));
    }

    private static List<String> splitSentence(String document, String sentenceSeparator) {
        List<String> sentences;
        String[] sections = document.split("[\r\n]");

        sentences = Arrays.stream(sections)
                .map(String::trim)
                .filter(line -> line.length() != 0)
                .map(line -> line.split(sentenceSeparator))
                .flatMap(Arrays::stream).map(String::trim)
                .filter(sent -> sent.length() != 0)
                .collect(Collectors.toList());

        return sentences;
    }

    private static List<List<String>> convertSentenceListToDocument(List<String> sentenceList, String type) {

        return sentenceList
                .stream()
                .map(sentence -> segSentence(sentence, type))
                .collect(Collectors.toCollection(() -> new ArrayList<>(sentenceList.size())));
    }

    private static List<String> segSentence(String sentence, String type) {
        switch (type) {
            case "2":
                return fudanSegSentence(sentence);
            default:
                return hanlpSegSentence(sentence);
        }
    }

    private static List<String> hanlpSegSentence(String sentence) {

        return StandardTokenizer.segment(sentence.toCharArray())
                .stream()
                .filter(CoreStopWordDictionary::shouldInclude)
                .map(term -> term.word)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private static List<String> fudanSegSentence(String sentence) {
        return CoreTag.tag(sentence)
                .stream()
                .filter("/"::contains)
                .map(item -> item.split("/", 2))
                .filter(arr -> !(FUDAN_FILTER_TAG_LIST.contains(arr[1]) || CoreStopWordDictionary.contains(arr[1])))
                .map(arr -> arr[0])
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private final static List<String> FUDAN_FILTER_TAG_LIST = Arrays.asList("IJ,ON,PU,X".split(","));

}
