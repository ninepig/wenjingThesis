package Util;

import Entity.answerDbCluster;
import Entity.categoryedAnswer;
import Entity.scoredAnswer;
import dbEntity.Answer;
import dbEntity.Question;
import lda.Corpus;
import lda.LdaGibbsSampler;
import lda.LdaUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/14.
 */
public class wenjingLdaUtil {

//    public static List<answerDbCluster> ldaProcessing(int topicNumber, List<Answer> answers , int topicKkeyword, Question thisQuestion) {
//
//        Corpus corpus = Corpus.load(answers);
//        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
//        ldaGibbsSampler.gibbs(topicNumber);
//        double[][] phi = ldaGibbsSampler.getPhi();
//
//
//
//        // 获取每一个topic下的 TOPn Keyword
//        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), topicKkeyword);
//        ArrayList<List<String>> ldaTopNkeyWordList = new ArrayList<List<String>>(topicKkeyword);
//        //获取每个cluster下面 最多 N个keyword 和 问题keyword 组成的keyword 数组
//        List<List<String>> newKeywordList = new ArrayList<List<String>>();
//
//        for (Map<String, Double> singLeMap : topicMap)
//        {
//           List<String> thisList = new ArrayList<String>();
//
//            for (Map.Entry<String, Double> entry : singLeMap.entrySet())
//            {
//                thisList.add(entry.getKey());
//            }
//            ldaTopNkeyWordList.add(thisList);
//            newKeywordList.add(rateUtil.mixTitleAndKeyword(thisQuestion.getTitle(),thisList));
//        }
//
//
//        //用lda给每个answer分组，然后在组里面给answer打分
//        double[][] theta = ldaGibbsSampler.getTheta();
//        ArrayList<categoryedAnswer> categoryedAnswersList = new ArrayList<categoryedAnswer>();
//        Double biggestDouble;
//        int biggestCategory;
//        for(int i=0;i<theta.length;i++) {
//            biggestDouble = theta[i][0];
//            biggestCategory = 1;
//            for (int j = 0; j < theta[i].length; j++) {
//                if (biggestDouble < theta[i][j]) {
//                    biggestDouble = theta[i][j];
//                    biggestCategory = j+1;
//                }
//            }
//            categoryedAnswer thisCa = new categoryedAnswer(answers.get(i),biggestCategory);
//            categoryedAnswersList.add(thisCa);
//        }
//        ArrayList<List<categoryedAnswer>> topicedCategoryedAnswerList = new ArrayList<List<categoryedAnswer>>();
//        for(int i = 1 ;i<=5;i++){
//            List<categoryedAnswer> list = new ArrayList<categoryedAnswer>();
//            for(categoryedAnswer thisAnswer:categoryedAnswersList){
//                if(thisAnswer.getCategory() == i){
//                    list.add(thisAnswer);
//                }
//            }
//            topicedCategoryedAnswerList.add(list);
//        }
//
//
//
//      List<answerDbCluster> resultList = new ArrayList<answerDbCluster>();
//        int helperCount = 0 ;
//        for (List<categoryedAnswer> cL:topicedCategoryedAnswerList) {
////            System.out.println("####");
//            List<scoredAnswer> scoredAnswerArrayList = new ArrayList<scoredAnswer>();
//            for(categoryedAnswer a : cL){
////                System.out.println("topic"+a.getCategory());
////                System.out.println("content"+a.getAnswer().getRawString());
//                //打分，通过key word（text rank） , rate 进行打分
//                scoredAnswer  sA = new scoredAnswer(a.getAnswer(),thisQuestion.getTitle());
//                //todo 计算得分，用这个cluster的combine keyword 算分
//                sA.rateAnswer(newKeywordList.get(helperCount));
//                scoredAnswerArrayList.add(sA);
//            }
//            answerDbCluster thisCluster = new answerDbCluster(ldaTopNkeyWordList.get(helperCount),scoredAnswerArrayList);
//            thisCluster.setQuestion(thisQuestion.getTitle());
//            thisCluster.setKeyWordCombineQuestionTitle(newKeywordList.get(helperCount));
//            helperCount++;
//            resultList.add(thisCluster);
//        }
//
//
//        return resultList;
//    }


    public static List<answerDbCluster> ldaProcessingTitle(int topicNumber, List<Answer> answers , int topicKkeyword, String questionTilte) {

        Corpus corpus = Corpus.load(answers);
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        ldaGibbsSampler.gibbs(topicNumber);
        double[][] phi = ldaGibbsSampler.getPhi();



        // 获取每一个topic下的 TOPn Keyword
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), topicKkeyword);
        ArrayList<List<String>> ldaTopNkeyWordList = new ArrayList<List<String>>(topicKkeyword);
        //获取每个cluster下面 最多5个keyword 和 问题keyword 组成的keyword 数组
        List<List<String>> newKeywordList = new ArrayList<List<String>>();

        for (Map<String, Double> singLeMap : topicMap)
        {
            List<String> thisList = new ArrayList<String>();

            for (Map.Entry<String, Double> entry : singLeMap.entrySet())
            {
                thisList.add(entry.getKey());
            }
            ldaTopNkeyWordList.add(thisList);
            newKeywordList.add(rateUtil.mixTitleAndKeyword(questionTilte,thisList));
        }

//        System.out.println("newKeywordList"+newKeywordList.size());


        //用lda给每个answer分组，然后在组里面给answer打分
        double[][] theta = ldaGibbsSampler.getTheta();
        ArrayList<categoryedAnswer> categoryedAnswersList = new ArrayList<categoryedAnswer>();
        Double biggestDouble;
        int biggestCategory;
        for(int i=0;i<theta.length;i++) {
//            System.out.println("theta"+theta.length);
            biggestDouble = theta[i][0];
            biggestCategory = 1;
            for (int j = 0; j < theta[i].length; j++) {
                if (biggestDouble < theta[i][j]) {
                    biggestDouble = theta[i][j];
                    biggestCategory = j+1;
                }
            }
            categoryedAnswer thisCa = new categoryedAnswer(answers.get(i),biggestCategory);
//            System.out.println(biggestCategory);
            categoryedAnswersList.add(thisCa);
        }
//        System.out.println("categoryedAnswersList  "+categoryedAnswersList.size());



        ArrayList<List<categoryedAnswer>> topicedCategoryedAnswerList = new ArrayList<List<categoryedAnswer>>();
        for(int i = 1 ;i<=topicNumber;i++){
            List<categoryedAnswer> list = new ArrayList<categoryedAnswer>();
            for(categoryedAnswer thisAnswer:categoryedAnswersList){
                if(thisAnswer.getCategory() == i){
                    list.add(thisAnswer);
                }
            }
            topicedCategoryedAnswerList.add(list);
        }
//        System.out.println(topicedCategoryedAnswerList.size());





        List<answerDbCluster> resultList = new ArrayList<answerDbCluster>();
        int helperCount = 0 ;

        for (List<categoryedAnswer> cL:topicedCategoryedAnswerList) {
//            System.out.println("####");
            List<scoredAnswer> scoredAnswerArrayList = new ArrayList<scoredAnswer>();
            for(categoryedAnswer a : cL){
//                System.out.println("topic"+a.getCategory());
//                System.out.println("content"+a.getAnswer().getRawString());
                //打分，通过key word（text rank） , rate 进行打分
                scoredAnswer  sA = new scoredAnswer(a.getAnswer(),questionTilte);
                //todo 计算得分，用这个cluster的combine keyword 算分
                sA.rateAnswer(newKeywordList.get(helperCount));
                scoredAnswerArrayList.add(sA);
            }
            answerDbCluster thisCluster = new answerDbCluster(ldaTopNkeyWordList.get(helperCount),scoredAnswerArrayList);
            thisCluster.setQuestion(questionTilte);
            thisCluster.setKeyWordCombineQuestionTitle(newKeywordList.get(helperCount));
            helperCount++;
            resultList.add(thisCluster);
        }


        return resultList;
    }




}
