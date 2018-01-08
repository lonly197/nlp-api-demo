package com.lonly.example.nlpapidemo.utils.summary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Lazy
@PropertySource(value = {"classpath:hanlp.properties"}, encoding = "utf-8")
@Slf4j(topic = "Model Load")
@Component
public class HanlpSummary {

    public String extract(String text, String percent, Optional<String> type) {
        List<String> sentences = splitSentence(text.replaceAll("\\n", ""), false);
        String result = IntStream.range(0, sentences.size())
                .filter(i -> sentences.get(i).length() >= 1)
                .mapToObj(i -> String.join("", HanlpUtils.extractSummary(sentences.get(i),type).get(0), getLastChar(sentences.get(i))))
                .collect(Collectors.joining());
        return subSentence(result, result.length(), Double.parseDouble(percent));
    }


    /**
     * 获取句子的结尾符号
     * @param cmt 句子
     * @return
     */
    private String getLastChar(String cmt) {
        int length = cmt.length();
        return length > 1 ? cmt.substring(length - 1, length) : cmt;
    }

    /**
     * 根据标点符号进行句子拆分，并且保留句子结尾符号
     */
    private List<String> splitSentence(String cmt, Boolean isExceed) {
        /*正则表达式：句子结束符*/
        String regEx = isExceed ? "[：。！；？]" : "[,.?!:;~，：。！；？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(cmt);
        /*按照句子结束符分割句子*/
        String[] sentences = p.split(cmt);
        /*将句子结束符连接到相应的句子后*/
        if (sentences.length > 0) {
            int count = 0;
            while (count < sentences.length) {
                if (m.find()) {
                    sentences[count] += m.group();
                }
                count++;
            }
        }
        /*输出结果*/
        return Arrays.asList(sentences);
    }

    /**
     * 截取句子
     * @param cmt 句子
     * @param len 原句子长度
     * @param percent 截取比例（大于1时，为截取字数，小于或等于1时，为截取比例）
     * @return
     */
    private String subSentence(String cmt, int len, double percent) {
        if (percent > 1) {
            return cmt.substring(0, (int) percent);
        } else {
            int limit = (int) (len * percent);
            List<String> result = new ArrayList<>();
            for (String sent : splitSentence(cmt, true)) {
                result.add(sent);
                if (result.stream().mapToInt(String::length).sum() >= limit) {
                    break;
                }
            }
            return result.stream().reduce("", String::concat);
        }
    }

}
