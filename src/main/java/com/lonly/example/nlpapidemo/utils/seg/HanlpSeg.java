package com.lonly.example.nlpapidemo.utils.seg;

import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Slf4j(topic = "Model Load")
@Component
public class HanlpSeg {
    public Segment hanlpCRFSeg;
    public Segment hanlpShortSeg;
    public Segment hanlpNShortSeg;
    public Segment hanlpSeg;

    public HanlpSeg() {
//        HanLP.Config.ShowTermNature = false;    // 关闭词性显示
        hanlpCRFSeg = new CRFSegment().enableCustomDictionary(false).enablePartOfSpeechTagging(true);
        hanlpNShortSeg = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        hanlpShortSeg = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        hanlpSeg = new Segment() {
            @Override
            protected List<Term> segSentence(char[] chars) {
                return null;
            }

            @Override
            public List<Term> seg(String text) {
                return StandardTokenizer.segment(text);
            }
        };
    }

    public String[] seg(String text) {
        return hanlpSeg.seg(text).stream().map(word -> word.word).collect(Collectors.toList()).toArray(new String[0]);
    }

    public String[] segCRF(String text) {
        return hanlpCRFSeg.seg(text).stream().map(word -> word.word).collect(Collectors.toList()).toArray(new String[0]);
    }

    public String[] segShort(String text) {
        return hanlpShortSeg.seg(text).stream().map(word -> word.word).collect(Collectors.toList()).toArray(new String[0]);
    }

    public String[] segNShort(String text) {
        return hanlpNShortSeg.seg(text).stream().map(word -> word.word).collect(Collectors.toList()).toArray(new String[0]);
    }
}
