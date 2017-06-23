package Enterance;

import Entity.answerDbCluster;
import Util.Cosine_Similarity;
import dbEntity.Answer;
import dbEntity.Question;

import java.util.List;


import static Util.wenjingLdaUtil.ldaProcessingTitle;

/**
 * Created by Administrator on 2017/5/14.
 */
public class handleDocumentDbVersion {

    private Question thisQuestion;
    private List<Answer> answers;
    // 因为现在的cluster是取聚类的结果里面 rate 最高的answer 然后做处理，所以逻辑要改变
    private List<answerDbCluster> clusterList;
    private int topicNumber;



    private int topicKkeyword;
    //文档的document 相似度
    private double[][] cosinSimilarityMatrix;
    //clustering TopKword 相似度
    private double[][] topKcosinSimilartiyMatrix;

//    public handleDocumentDbVersion(Question thisQuestion, int topicNumber) {
//        this.thisQuestion = thisQuestion;
//        this.topicNumber = topicNumber;
//    }

    // 在外部直接传入answers 以防止多次读取数据库
    public handleDocumentDbVersion(Question thisQuestion, List<Answer> answers, int topicNumber,int topicKkeyword) {
        this.thisQuestion = thisQuestion;
        this.answers = answers;
        this.topicNumber = topicNumber;
        this.topicKkeyword = topicKkeyword;

        this.clusterList = ldaProcessingTitle(topicNumber,answers,topicKkeyword,thisQuestion.getTitle());
        
        handleInsideSimilarity();

    }

    public handleDocumentDbVersion() {

    }

    private void handleInsideSimilarity() {

        topKcosinSimilartiyMatrix = new double[topicNumber][topicNumber];

        for(int i = 0 ; i<topicNumber;i++){

            //topicK Simliartiy
            String keyword  = ListToString(clusterList.get(i).getTopNkeywordString());
            for(int j = 0 ; j<topicNumber;j++){
                if(i==j){
                    continue;
                }else {

                    topKcosinSimilartiyMatrix[i][j] = Cosine_Similarity.Cosine_Similarity_Score(keyword,ListToString(clusterList.get(j).getTopNkeywordString()));
                }
            }
        }


    }
    private String ListToString(List<String> answerTextRankKeyWowrd) {

        StringBuffer sb = new StringBuffer();
        for (String a: answerTextRankKeyWowrd
                ) {
            sb.append(a+" ");
        }
        return sb.toString();
    }

    public int getTopicKkeyword() {
        return topicKkeyword;
    }

    public void setTopicKkeyword(int topicKkeyword) {
        this.topicKkeyword = topicKkeyword;
    }

    public double[][] getCosinSimilarityMatrix() {
        return cosinSimilarityMatrix;
    }

    public void setCosinSimilarityMatrix(double[][] cosinSimilarityMatrix) {
        this.cosinSimilarityMatrix = cosinSimilarityMatrix;
    }

    public double[][] getTopKcosinSimilartiyMatrix() {
        return topKcosinSimilartiyMatrix;
    }

    public void setTopKcosinSimilartiyMatrix(double[][] topKcosinSimilartiyMatrix) {
        this.topKcosinSimilartiyMatrix = topKcosinSimilartiyMatrix;
    }

    public Question getThisQuestion() {
        return thisQuestion;
    }

    public void setThisQuestion(Question thisQuestion) {
        this.thisQuestion = thisQuestion;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<answerDbCluster> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<answerDbCluster>  clusterList) {
        this.clusterList = clusterList;
    }

    public int getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(int topicNumber) {
        this.topicNumber = topicNumber;
    }






}
