package dbEntity;

/**
 * Created by Administrator on 2017/5/13.
 */
public class Answer {
    public Answer(int questionID, String rawString, int rate,String sw) {
        this.questionID = questionID;
        this.rawString = rawString;
        this.rate = rate;
        this.splitWordString = sw;
    }

    // answer id can be auto generate!
    private int answerID;
    private int questionID;

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getRawString() {
        return rawString;
    }

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }


    private String splitWordString;
    private  String rawString;
    private int rate;
    public Answer(int answerID, int questionID, String rawString, int rate) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.rawString = rawString;
        this.rate = rate;
    }

    public Answer(int answerID, int questionID, String rawString, int rate,String splitWord) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.rawString = rawString;
        this.rate = rate;
        this.splitWordString = splitWord;
    }

    public Answer(){

    }

    public String getSplitWordString() {
        return splitWordString;
    }

    public void setSplitWordString(String splitWordString) {
        this.splitWordString = splitWordString;
    }
}
