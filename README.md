# NLP API DEMO

> This project integrates multiple NLP tools such as Stanford, Hanlp, FNLP, Thulc, FudanDNN, Boson NLP and Jieba to compare their performance and accuracy.


## 接口地址

[http://localhost:7002/]()

## 接口列表

目前已实现的接口如下：

### 分词：
- seg/hanlp
- seg/hanlpcrf
- seg/hanlpnshort
- seg/hanlpshort
- seg/boson
- seg/fudan
- seg/stanford
- seg/fnlp
- seg/jieba
- seg/thulc

### 命名实体识别：
- ner/boson
- ner/hanlp
- ner/fudan

### 依存句法分析：
- parser/boson
- parser/hanlp
- parser/crfhanlp
- parser/maxenthanlp
- parser/stanford

### 情感分析：
- sentiment/boson
- sentiment/hanlp

### 摘要抽取：
- summary/boson
- summary/hanlp

### 文本分类：
- classify/boson
- classify/hanlp

### 关键词抽取：
- keyword/boson
- keyword/hanlp

### 词义联想：
- suggest/boson
- suggest/hanlp

## 如何使用

### 项目打包

```sh
mvn clean package -Dmaven.skip.test=true
```

### 项目启动

```sh
tar xvf nlp-api-demo.tar
cd nlp-api-demo
sh bin/start.sh | tail -f nohup.log
```

### 接口请求

#### Curl

Linux:

```
curl -d "text=2015世界旅游小姐大赛山东赛区冠军总决赛在威海举行。当日，来自山东省各地的30名佳丽选手通过泳装、旗袍和晚礼展示等环节拼比角逐，鞠炎珍获冠军殊荣， 夏伟、宋一凡分获亚军和季军。她们将代表山东参加今年12月5日在沈阳举行的2015世界旅游小姐中国年度冠军总决赛。" "http://192.168.6.114:7002/seg/hanlp"
```

Windows:


```
curl -d "text=2015世界旅游小姐大赛山东赛区冠军总决赛在威海举行。当日，来自山东省各地的30名佳丽选手通过泳装、旗袍和晚礼展示等环节拼比角逐，鞠炎珍获冠军殊荣， 夏伟、宋一凡分获亚军和季军。她们将代表山东参加今年12月5日在沈阳举行的2015世界旅游小姐中国年度冠军总决赛。" "http://192.168.6.114:7002/seg/hanlp" | iconv -f utf-8 -t gbk
```

#### Postman

![](http://omdis1w10.bkt.clouddn.com/nlp_api_usage_00.png)
