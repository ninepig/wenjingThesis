package Dao;

import dbEntity.Question;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/13.
 */
public class questionDaoImp implements  questionDao{

    private JdbcTemplate jdbcTemplate;

    public questionDaoImp(DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public void insert(Question qs) {
        String sql = "INSERT INTO question (questionID, title, answerNumber)"
                + " VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, qs.getQuestionID(),qs.getTitle(),qs.getAnswerNumber());
    }

    @Override
    public void delete() {

    }

    @Override
    public Question get(int questionID) {
        String sql = "SELECT * FROM question WHERE questionID = ?";
        Question thisQuestion = jdbcTemplate.queryForObject(sql, ParameterizedBeanPropertyRowMapper.newInstance(Question.class),questionID);

        return thisQuestion;
    }

    @Override
    public List<Question> getList() {
        String sql = "SELECT * FROM question";
        List<Question> Questions = jdbcTemplate.query(sql, new RowMapper<Question>() {

            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                Question aQuestion = new Question();

                aQuestion.setQuestionID(rs.getInt("questionID"));
                aQuestion.setTitle(rs.getString("title"));
                aQuestion.setAnswerNumber(rs.getInt("answerNumber"));

                return aQuestion;
            }

        });

        return Questions;
    }
}
