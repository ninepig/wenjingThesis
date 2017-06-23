/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2015/1/29 17:22</create-date>
 *
 * <copyright file="TestCorpus.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package ldaTest;

import Entity.document;
import Util.DocumentOp;
import junit.framework.TestCase;
import lda.Corpus;
import lda.LdaGibbsSampler;
import lda.LdaUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wenjing
 *  lda 处理
 *  1是topic word 矩阵
 *  2 是 document topic 矩阵
 *  3是report
 */
public class TestCorpus extends TestCase
{


    public void testMultiArray() throws Exception{

        int[][] abc = {{1,2,3},{4,5,6}};
        System.out.println(abc.length);
        System.out.println(abc[0].length);

    }
    public void testAddDocument() throws Exception
    {
        List<String> doc1 = new ArrayList<String>();
        doc1.add("hello");
        doc1.add("word");
        List<String> doc2 = new ArrayList<String>();
        doc2.add("hankcs");
        Corpus corpus = new Corpus();
        corpus.addDocument(doc1);
        corpus.addDocument(doc2);
        System.out.println(corpus);
    }

    public void testAll() throws Exception
    {
        // 1. Load corpus from disk
        Corpus corpus = Corpus.load("data/mini");
        // 2. Create a LDA sampler
        System.out.println("word size"+corpus.getVocabularySize());
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        // 3. Train it
        ldaGibbsSampler.gibbs(10);
        // 4. The phi matrix is a LDA model, you can use LdaUtil to explain it.
        double[][] phi = ldaGibbsSampler.getPhi();
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
        LdaUtil.explain(topicMap);
        //
        System.out.println("22222");

        double[][] theta = ldaGibbsSampler.getTheta();
        System.out.println("多少个文档"+theta.length);
        System.out.println("多少个topic"+theta[0].length);



        // 5. TODO:Predict. I'm not sure whether it works, it is not stable.
//        System.out.println("123123123");
//        int[] document = Corpus.loadDocument("data/mini/军事_510.txt", corpus.getVocabulary());
//        double[] tp = LdaGibbsSampler.inference(phi, document);
//        System.out.println("123123123");
//        for (double number:tp
//             ) {
//            System.out.println("output"+number);
//        }
//        System.out.println("123123123");
//        Map<String, Double> topic = LdaUtil.translate(tp, phi, corpus.getVocabulary(), 10);
//        LdaUtil.explain(topic);
    }
    public void testZhihuAll() throws Exception{
        String targetFolder = "C:\\Users\\yamengwenjing\\Desktop\\wenjingWork\\interviewAlgorithm\\wenjingThesis\\src\\main\\res\\grabbedAnswer\\zhihu\\ldaCorpus\\3.html";
        String reportFolder = "C:\\Users\\yamengwenjing\\Desktop\\wenjingWork\\interviewAlgorithm\\wenjingThesis\\src\\main\\res\\grabbedAnswer\\zhihu\\ldaCorpus";

        Corpus corpus = Corpus.load(targetFolder);
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        ldaGibbsSampler.gibbs(3);

        //topic to word
        double[][] phi = ldaGibbsSampler.getPhi();
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
        LdaUtil.explain(topicMap);

        //document--topic
//        System.out.println("document--topic");
        double[][] theta = ldaGibbsSampler.getTheta();
//        System.out.println("document + topic p");
//        for(int i=0;i<theta.length;i++){
//            System.out.println("document "+i+"");
//            for(int j = 0 ; j< theta[i].length;j++){
//                System.out.print(theta[i][j]+" ");
//                System.out.println(" ");
//
//            }
//        }

        //todo get raw data files
        ArrayList<document> report = new ArrayList<document>();
        try {
        DocumentOp document = new DocumentOp(targetFolder+"raw");
        Double biggestDouble;
        int biggest;
        for(int i=0;i<theta.length;i++){
//            System.out.println("document "+(i+1)+"");

            biggestDouble = theta[i][0];
            biggest = 0;
            for(int j = 0 ; j< theta[i].length;j++){
//                System.out.print(theta[i][j]+" ");
//                System.out.println(" ");
                if(biggestDouble<theta[i][j]){
                    biggestDouble = theta[i][j];
                    biggest = j;
                }
            }
            document.getDocumentList().get(i).setCategory(biggest);
            report = document.getDocumentList();
//            System.out.println("category is "+(biggest+1));
//            System.out.println(" ");
        }
        }catch (Exception e){
        e.printStackTrace();
        }
        int categoryNumber =3;
        ArrayList<String> reportResult = new ArrayList<String>();
        // handle report
        for(int j = 0;j<categoryNumber;j++) {
            StringBuffer sb = new StringBuffer();
            int realCategoryNumber = j + 1;
            sb.append("category"+realCategoryNumber+"\n");
            for (int i = 0; i < report.size(); i++) {
//                System.out.println(report.get(i).getContent());
//                System.out.println(report.get(i).getCategory());
//                System.out.println("####");
                if(report.get(i).getCategory()==j){
                    sb.append(report.get(i).getContent());
                        sb.append("\n###\n");
                }
            }
            reportResult.add(sb.toString());
        }

        //print dirctly
            for(int i =0;i<reportResult.size();i++){
            System.out.println(reportResult.get(i));
        }

        //generate report
//
//        StringBuffer reportTxt = new StringBuffer();
//        for(int i =0;i<reportResult.size();i++){
//            reportTxt.append(reportResult.get(i)+"\n");
//        }
//        File newReportTextFile = new File(reportFolder+"\\"+"1report.txt");
//
//        FileWriter fw = new FileWriter(newReportTextFile);
//        fw.write(reportTxt.toString());
//        fw.close();

        System.out.println("end");

    }


}
