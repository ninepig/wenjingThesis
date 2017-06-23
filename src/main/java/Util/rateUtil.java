package Util;

import Enterance.handleDocumentDbVersion;
import Entity.answerDbCluster;
import Entity.scoredAnswer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static Util.StringOperation.textHandlingList;

/**
 * Created by Administrator on 2017/5/16.
 */
public class rateUtil {
    /**
     * 取title的分词，以及ldaKeyword的topic 分词 ，组成 cluster 的question 分词
     * 关键是参数 count ，加入几个clustering里的word
     * @param title
     * @param ldaKeyword
     * @return
     */
    final static Logger logger = Logger.getLogger(rateUtil.class);
    public static List<String> mixTitleAndKeyword(String title, List<String> ldaKeyword){

        List<String> titleWord = textHandlingList(title);

        List<String> newList = new ArrayList<String>();
        int count = 6;
        for (String a : titleWord){
            newList.add(a);
        }

        for(String b : ldaKeyword){
            if(newList.contains(b)){
                continue;
            }else {
                if(count>0){
                    newList.add(b);
                    count--;
                }
            }
        }

        return newList;
    }

    static double cosineJudgeKey = 0.51;
    static double similarityJudgeKey = 0.18;

    /**
     * 收敛的情况界定
     * 1 cluster之间的相似度高于0.51
     * 2 所有answer的和cluster的相似度小于0.18
     * 3 如果topic 下没有answer ！
     * @param hd
     *
     * @return
     */
    public static boolean judgeConverge(handleDocumentDbVersion hd){

        List<answerDbCluster> ClusterList = hd.getClusterList();

        // judge cosine similarity
//        System.out.println("##topKeyWordCosinSimliar##");
        for (int i = 0; i < ClusterList.size(); i++) {
//            System.out.println("####" + i);
            for (int j = 0; j < hd.getTopKcosinSimilartiyMatrix()[i].length; j++) {
//                System.out.print(hd.getTopKcosinSimilartiyMatrix()[i][j]);
//                System.out.print(" ");
                 if(hd.getTopKcosinSimilartiyMatrix()[i][j]>cosineJudgeKey){
                     System.out.print("the cosine similarity converging!!");
                     logger.info("the cosine similarity converging!!");
                     return true;
                 }
            }
        }


        for (answerDbCluster a : ClusterList) {

            if(a.getScoredAnswerList().size()==0){
                System.out.print("the scored Answer list eqaul 0 converging!!");
                logger.info("the scored Answer list eqaul 0 converging!!");
                return true;
            }
            //all answer less than one score
            Boolean allscoreLess = true;
            for(scoredAnswer sa : a.getScoredAnswerList()){
                if(sa.getScore()>similarityJudgeKey){
                    allscoreLess = false;
                }
            }
            if(allscoreLess){
                System.out.print("the scored Answer list score less converging!!");
                logger.info("the scored Answer list score less converging!!!");
                return true;
            }

        }
        return false;
    }
}
