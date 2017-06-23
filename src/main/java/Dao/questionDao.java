package Dao;

import dbEntity.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public interface questionDao {

    public void insert(Question qs);
    public void delete();
    public Question get(int questionID);
    public List<Question> getList();


}
