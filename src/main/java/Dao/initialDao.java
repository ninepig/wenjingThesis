package Dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2017/5/13.
 */
public class initialDao {
    public static DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/wenjingthesis");
        dataSource.setUsername("root");
        dataSource.setPassword("ywj1988");

        return dataSource;
    }


    public static questionDao getquestionDao() {
        return new questionDaoImp(getDataSource());
    }

    public static answerDao getAnserDao(){
        return new answerDaoImp(getDataSource());
    }
}
