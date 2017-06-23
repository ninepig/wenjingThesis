package Enterance;

import Dao.answerDao;
import Dao.initialDao;
import Dao.questionDao;
import Entity.answerDbCluster;
import dbEntity.Answer;
import dbEntity.Question;
import org.apache.log4j.Logger;

import java.util.List;

import static Util.rateUtil.judgeConverge;

/**
 * Created by yangw on 2017/5/20.
 */
public class testWholeProcessing {

    static int topKkeyword = 20;
    final static Logger logger = Logger.getLogger(testWholeProcessing.class);
    public static void main(String[] args){
        //get db dao
        questionDao qDb = initialDao.getquestionDao();
        answerDao questionDb = initialDao.getAnserDao();

        //get target Question and answers list
        Question thisQ = qDb.get(524824840);
        List<Answer> list = questionDb.getListByQuestionId(524824840);

        handleDocumentDbVersion covergingHd = new handleDocumentDbVersion();
        // loop for best k and get converingDocument;
        for(int topicNumber = 3;topicNumber<=20;topicNumber++) {
            handleDocumentDbVersion hd = new handleDocumentDbVersion(thisQ, list,topicNumber, topKkeyword);
            if(judgeConverge(hd)){
               System.out.println("converging at "+topicNumber+"topic");
               break;
            }
            covergingHd = hd;

        }
        System.out.println("currenthd's cluster "+ covergingHd.getTopicNumber());

        logger.info("question title is" + thisQ.getTitle());
        logger.info("question sub topic number is "+ covergingHd.getTopicNumber());

        for (answerDbCluster a : covergingHd.getClusterList()) {
            System.out.println("topic######");
            System.out.println("topic######");
            logger.info("topic######");

            System.out.println("size" + a.getScoredAnswerList().size());
            logger.info("size" + a.getScoredAnswerList().size());

            List<String> topNkeywordString = a.getTopNkeywordString();
            System.out.println("topNkeywordString" + topNkeywordString);
            logger.info("this cluster 's topNkeywordString" + topNkeywordString.toString());

           System.out.println("clusterCombineKeyword" +a.getKeyWordCombineQuestionTitle());
            logger.info("this cluster 's clusterCombineKeyword" + a.getKeyWordCombineQuestionTitle());

           //
           System.out.println("best Answer"+a.getBestAnswer().getAnswer().getRawString());
           System.out.println("best Answer rate "+a.getBestAnswer().getAnswer().getRate());
           System.out.println("best Answer score "+a.getBestAnswer().getScore());
            logger.info("best Answer" + a.getBestAnswer().getAnswer().getRawString());
            logger.info("best Answer rate" + a.getBestAnswer().getAnswer().getRate());
            logger.info("best Answer score" + a.getBestAnswer().getScore());
           //
//           for (scoredAnswer sa : scoredAnswerList) {
//               System.out.println(sa.getAnswer().getRawString());
//               System.out.println("score" + sa.getScore());
//               System.out.println("rate" + sa.getAnswer().getRate());
//           }
        }







    }


}
