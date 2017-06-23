package Entity;

import dbEntity.Answer;

/**
 * Created by Administrator on 2017/5/15.
 */
public class categoryedAnswer {
    public categoryedAnswer(Answer answer, int category) {
        this.answer = answer;
        this.category = category;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    private Answer answer;
    private int category;
}
