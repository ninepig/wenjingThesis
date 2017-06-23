package Dao;

import dbEntity.Answer;
import dbEntity.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public interface answerDao {
    public void insert(Answer as);
    public void delete();
    public Answer get(int answerId);
    public List<Answer> getList();
    public List<Answer> getListByQuestionId(int questionId);

}
