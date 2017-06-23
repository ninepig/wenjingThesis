package Entity;

import Util.Cosine_Similarity;
import dbEntity.Answer;

import java.util.List;

import static Util.DocumentAux.getTextRankKeywords;

/**
 * Created by Administrator on 2017/5/14.
 */
public class scoredAnswer  {

    private double score;
    private  Answer answer;
    private String questionTitle;


    public scoredAnswer(Answer answer,String title) {
        this.answer = answer;
        this.questionTitle = title;

    }




    /**
     *  //核心算法，如何衡量这个answer的评分 文本的相似度  利用textRank 来做
     * @param keywordList 这个cluster 指定的 keyword， 由题目以及cluster的lda keyword组成
     *
     */
    public void rateAnswer(List<String> keywordList) {
        //获取 该 answer的 textrank 关键词
        List<String> answerTextRankKeyWowrd = getTextRankKeywords(answer.getRawString(),keywordList.size());
        String answerKeywordString = ListToString(answerTextRankKeyWowrd);
        String clusterKeywordString = ListToString(keywordList);
        this.score =Cosine_Similarity.Cosine_Similarity_Score(answerKeywordString,clusterKeywordString);
    }

    private String ListToString(List<String> answerTextRankKeyWowrd) {

        StringBuffer sb = new StringBuffer();
        for (String a: answerTextRankKeyWowrd
             ) {
            sb.append(a+" ");
        }
        return sb.toString();
    }


    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
