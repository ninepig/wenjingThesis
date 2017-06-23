package Entity;

import dbEntity.*;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 *
 * 这个应该是lda 生成的某一个聚类，我所有需要的东西
 * topNkeyword 是lda生成的一组 keyword String
 *
 */
public class answerDbCluster {
    // top n 个 keyword 组成的 string ，用于分析相似度,由lda矩阵得到
    private List<String> topNkeywordString;
    // 最佳答案由下面的LIST取得分最高的
    private scoredAnswer bestAnswer;

    private List<scoredAnswer> scoredAnswerList;
    private List<String> keyWordCombineQuestionTitle;
    private String question;




    public answerDbCluster(List<String>  topNkeywordString, List<scoredAnswer> scoredAnswerList) {
        this.topNkeywordString = topNkeywordString;
        this.scoredAnswerList = scoredAnswerList;
        rateBestAnswer();
    }


    /**
     *  //根据传入的scoredAnswerList ,权衡 rate 以及 score的权重
     *  得分而言。最重要的是用户的评价，如果用户点赞相同，再算计算score
     *  所以公式就是 rate*1 + score number
     *  对于rate 超过5的，表示得到一定的赞同，则不考虑score
     *  todo 如果score 为0 ，即一点关系都没有。 rate再高也不能考虑
     *
     */
    private void rateBestAnswer() {

        Double biggest = 0.0;
        int highestRate = 0;
        Boolean rateMoreThanFive = false;
        for(scoredAnswer sa: this.scoredAnswerList){

            if(sa.getAnswer().getRate()>=5){
                rateMoreThanFive = true;
            }
            if(rateMoreThanFive){
                int thisRate = sa.getAnswer().getRate();
                if(thisRate>highestRate){
                    highestRate = thisRate;
                    this.bestAnswer = sa;
                }
            }else {
                double thisScore = sa.getScore();
                if (thisScore >= biggest) {
                    biggest = thisScore;
                    this.bestAnswer = sa;
                }
            }
        }

    }


    public List<String>  getTopNkeywordString() {
        return topNkeywordString;
    }

    public void setTopNkeywordString(List<String>  topNkeywordString) {
        this.topNkeywordString = topNkeywordString;
    }

    public scoredAnswer getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(scoredAnswer bestAnswer) {
        this.bestAnswer = bestAnswer;
    }

    public List<scoredAnswer> getScoredAnswerList() {
        return scoredAnswerList;
    }

    public void setScoredAnswerList(List<scoredAnswer> scoredAnswerList) {
        this.scoredAnswerList = scoredAnswerList;
    }

    public List<String> getKeyWordCombineQuestionTitle() {
        return keyWordCombineQuestionTitle;
    }

    public void setKeyWordCombineQuestionTitle(List<String> keyWordCombineQuestionTitle) {
        this.keyWordCombineQuestionTitle = keyWordCombineQuestionTitle;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
