package Dao;

import dbEntity.Answer;
import dbEntity.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public class answerDaoImp implements  answerDao{

    private JdbcTemplate jdbcTemplate;
    public answerDaoImp(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void insert(Answer as) {
        String sql = "INSERT INTO answer (questionID, rawString, rate,splitWordString)"
                + " VALUES ( ?, ?,?,?)";
        jdbcTemplate.update(sql,as.getQuestionID(),as.getRawString(),as.getRate(),as.getSplitWordString());
    }

    @Override
    public void delete() {

    }

    @Override
    public Answer get(int answerId) {
        return null;
    }

    @Override
    public List<Answer> getList() {
        String sql = "SELECT * FROM answer";
        List<Answer> aAnswers = jdbcTemplate.query(sql, new RowMapper<Answer>() {

            @Override
            public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Answer aAnswer = new Answer();

                aAnswer.setQuestionID(rs.getInt("questionID"));
                aAnswer.setAnswerID(rs.getInt("answerID"));
                aAnswer.setRawString(rs.getString("rawString"));
                aAnswer.setRate(rs.getInt("rate"));
                aAnswer.setSplitWordString(rs.getString("splitWordString"));
                return aAnswer;
            }

        });

        return aAnswers;
    }

    @Override
    public List<Answer> getListByQuestionId(int questionId) {
        String sql = "SELECT * FROM answer WHERE questionID= "+questionId;
        List<Answer> aAnswers = jdbcTemplate.query(sql, new RowMapper<Answer>() {

            @Override
            public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Answer aAnswer = new Answer();

                aAnswer.setQuestionID(rs.getInt("questionID"));
                aAnswer.setAnswerID(rs.getInt("answerID"));
                aAnswer.setRawString(rs.getString("rawString"));
                aAnswer.setRate(rs.getInt("rate"));
                aAnswer.setSplitWordString(rs.getString("splitWordString"));
                return aAnswer;
            }

        });

        return aAnswers;
    }
}
