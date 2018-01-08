package com.lonly.example.nlpapidemo.utils.summary.hanlp;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.summary.TextRankSentence;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.lonly.example.nlpapidemo.utils.seg.SegUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private Utils() {
    }

    @Autowired(required = false)
    private static SegUtils segUtils;

    public static List<String> extractSummary(String document) {
        return getTopSentenceList(document);
    }

    private static List<String> getTopSentenceList(String document) {
        List<String> sentenceList = splitSentence(document, "[，,。:：？?！!；;]");
        List<List<String>> docs = convertSentenceListToDocument(sentenceList);
        TextRankSentence textRank = new TextRankSentence(docs);
        int[] topSentence = textRank.getTopSentence(1);
        LinkedList<String> resultList = new LinkedList<>();

        for (int i : topSentence) {
            resultList.add(sentenceList.get(i));
        }

        return resultList;
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

    private static List<List<String>> convertSentenceListToDocument(List<String> sentenceList) {
        ArrayList<List<String>> docs = new ArrayList<>(sentenceList.size());

        for (String sentence : sentenceList) {
            List<String> wordList = new LinkedList<>();

            List<Term> termList = StandardTokenizer.segment(sentence.toCharArray());
            for (Term term : termList) {
                if (CoreStopWordDictionary.shouldInclude(term)) {
                    wordList.add(term.word);
                }
            }

//            Arrays.stream(segUtils.fudanDNNSeg.seg(sentence)).filter(item->CoreStopWordDictionary.shouldInclude).collect(Collectors.toList());

            docs.add(wordList);
        }

        return docs;
    }
}
