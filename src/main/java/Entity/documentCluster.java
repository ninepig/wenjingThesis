package Entity;

import Util.DocumentAux;
import com.hankcs.hanlp.HanLP;

import java.util.List;
import java.util.Map;

/**
 * Created by yamengwenjing on 2017-04-11.
 */
public class documentCluster {
    //每一个回答的内容是一段，然后所有的段落组成了content
    String content;
    String ldaKeyWord;

    List<String> TextRankKeyWord ;
    // 摘要抽取from hanLP;
    List<String> sentenceList ;


    // top n 个 keyword 组成的 string ，用于分析相似度
    String topNkeywordString;

    public String getHanlpSentence() {
        return hanlpSentence;
    }

    public void setHanlpSentence(String hanlpSentence) {
        this.hanlpSentence = hanlpSentence;
    }

    String  hanlpSentence;
    //摘要from us
    // 默认0.3的百分比，最多不超过300个字
    String abstractString;


    int answersNumber;

    public documentCluster(String content, String ldaKeyWord, int containAnswer) {
        this.content = content;
        this.ldaKeyWord = ldaKeyWord;
        this.answersNumber = containAnswer;
        handleInsideProcessing();

    }

    public documentCluster(String content, String ldaKeyWord, int containAnswer,String topNkeywordString) {
        this.content = content;
        this.ldaKeyWord = ldaKeyWord;
        this.answersNumber = containAnswer;
        this.topNkeywordString = topNkeywordString;
        handleInsideProcessing();

    }

    /*
    内部方法，textRank， 摘要
    TODO
     */
    private int topKeyWordTextRank = 10;
    private void handleInsideProcessing() {
        //get textRank keyword
      this.TextRankKeyWord =  DocumentAux.getTextRankKeywords(content,topKeyWordTextRank);
        //get textRank sentences
       this.sentenceList = DocumentAux.getTextRankSentences(content,answersNumber);

        StringBuffer sb = new StringBuffer();
        for(String a: this.sentenceList){
            sb.append(a+"\n");
        }
        this.hanlpSentence= sb.toString();


        //get wsummary abstract sentences
//        this.abstractString = DocumentAux.getSummaryFromWSsummary(content);

    }

    public String getLdaKeyWord() {
        return ldaKeyWord;
    }

    public void setLdaKeyWord(String ldaKeyWord) {
        this.ldaKeyWord = ldaKeyWord;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<String> getTextRankKeyWord() {
        return TextRankKeyWord;
    }

    public void setTextRankKeyWord(List<String> textRankKeyWord) {
        TextRankKeyWord = textRankKeyWord;
    }

    public List<String> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<String> sentenceList) {
        this.sentenceList = sentenceList;
    }

    public String getAbstractString() {
        return abstractString;
    }

    public void setAbstractString(String abstractString) {
        this.abstractString = abstractString;
    }

    public int getAnswersNumber() {
        return answersNumber;
    }

    public void setAnswersNumber(int answersNumber) {
        this.answersNumber = answersNumber;
    }
    public String getTopNkeywordString() {
        return topNkeywordString;
    }

    public void setTopNkeywordString(String topNkeywordString) {
        this.topNkeywordString = topNkeywordString;
    }



}
