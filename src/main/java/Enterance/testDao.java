package Enterance;

import Dao.answerDao;
import Dao.initialDao;
import Dao.questionDao;
import Dao.questionDaoImp;
import Entity.answerDbCluster;
import Entity.categoryedAnswer;
import Entity.scoredAnswer;
import Util.rateUtil;
import Util.wenjingLdaUtil;
import dbEntity.Answer;
import dbEntity.Question;
import lda.Corpus;
import lda.LdaGibbsSampler;
import lda.LdaUtil;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/13.
 */
public class testDao {
    public static void main(String[] args) {
//       questionDao questionDb = initialDao.getquestionDao();
//        Question testQuestion = new Question(1,"test",3);
//        questionDb.insert(testQuestion);
//        List<Question> list = questionDb.getList();
        questionDao qDb =initialDao.getquestionDao();
         answerDao questionDb = initialDao.getAnserDao();
//        Answer aAnswer = new Answer(1,2,"123",2);
//        questionDb.insert(aAnswer);
        Question thisQ = qDb.get(1953531614);
        List<Answer> list = questionDb.getListByQuestionId(1953531614);

        handleDocumentDbVersion hd = new handleDocumentDbVersion(thisQ,list,3,20);
        List<answerDbCluster> ClusterList = hd.getClusterList();
//       List<answerDbCluster> ClusterList = wenjingLdaUtil.ldaProcessingTitle(5,list,10,"有哪些关于糖尿病的谣言？ ");


       for (answerDbCluster a : ClusterList) {
           System.out.println("topic######");

           System.out.println("size" + a.getScoredAnswerList().size());
           List<String> topNkeywordString = a.getTopNkeywordString();
//           List<scoredAnswer> scoredAnswerList = a.getScoredAnswerList();
//           String qtitle = ClusterList.get(0).getQuestion();

           System.out.println("topNkeywordString" + topNkeywordString);
//           System.out.println("qtitle" + qtitle);
//           System.out.println("clusterCombineKeyword" +a.getKeyWordCombineQuestionTitle());
//
//           System.out.println("best Answer"+a.getBestAnswer().getAnswer().getRawString());
//           System.out.println("best Answer rate "+a.getBestAnswer().getAnswer().getRate());
//           System.out.println("best Answer score "+a.getBestAnswer().getScore());
//
//           for (scoredAnswer sa : scoredAnswerList) {
//               System.out.println(sa.getAnswer().getRawString());
//               System.out.println("score" + sa.getScore());
//               System.out.println("rate" + sa.getAnswer().getRate());
//           }
       }
        System.out.println("##topKeyWordCosinSimliar##");
        for (int i = 0; i < ClusterList.size(); i++) {
            System.out.println("####" + i);
            for (int j = 0; j < hd.getTopKcosinSimilartiyMatrix()[i].length; j++) {
                System.out.print(hd.getTopKcosinSimilartiyMatrix()[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }


}
