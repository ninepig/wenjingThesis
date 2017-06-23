package dbEntity;

/**
 * Created by Administrator on 2017/5/13.
 */
public class Question {

    private int questionID;

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    private String title;
    private int answerNumber;
    public Question(int questionID, String title, int answerNumber) {
        this.questionID = questionID;
        this.title = title;
        this.answerNumber = answerNumber;
    }



    public Question(){}


}
