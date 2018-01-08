package com.lonly.example.nlpapidemo.utils.hanlp;

import com.hankcs.hanlp.classification.classifiers.IClassifier;
import com.hankcs.hanlp.classification.classifiers.NaiveBayesClassifier;
import com.hankcs.hanlp.classification.models.NaiveBayesModel;
import com.hankcs.hanlp.corpus.io.IOUtil;

import java.io.File;
import java.io.IOException;

public class ModelUtils {

    public static NaiveBayesModel trainOrLoadModel(String corpPath, String modelPath) throws IOException {
        NaiveBayesModel model = (NaiveBayesModel) IOUtil.readObjectFrom(modelPath);
        if (model != null) {
            return model;
        }

        File corpusFolder = new File(corpPath);
        if (!corpusFolder.exists() || !corpusFolder.isDirectory()) {
            System.err.println("没有文本分类语料，请阅读IClassifier.train(java.lang.String)中定义的语料格式与语料下载：" +
                    "https://github.com/hankcs/HanLP/wiki/%E6%96%87%E6%9C%AC%E5%88%86%E7%B1%BB%E4%B8%8E%E6%83%85%E6%84%9F%E5%88%86%E6%9E%90");
        }

        // 创建分类器，更高级的功能请参考IClassifier的接口定义
        IClassifier classifier = new NaiveBayesClassifier();
        // 训练后的模型支持持久化，下次就不必训练了
        classifier.train(corpPath);
        model = (NaiveBayesModel) classifier.getModel();
        IOUtil.saveObjectTo(model, modelPath);
        return model;
    }
}
