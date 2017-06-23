package Enterance;

import Entity.document;
import Entity.documentCluster;
import Util.DocumentOp;
import Util.ldSimliarty;
import Util.textSimilarity;
import lda.Corpus;
import lda.LdaGibbsSampler;
import lda.LdaUtil;
import org.apdplat.word.analysis.CosineTextSimilarity;
import org.apdplat.word.analysis.JaccardTextSimilarity;
import org.apdplat.word.analysis.JaroWinklerDistanceTextSimilarity;
import org.apdplat.word.analysis.TextSimilarity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yamengwenjing on 2017-04-11.
 */
public class handleDocument {


    //TODO DOCUMENT CLUSTERING  INSIDE METHOD
    //TODO THIS CLASS CLUSTER SIMILARITYmATRIX


   private String targetDocumentPath;
   private String targetDocumentPathRawFile;
   private int clusterNumber = 4;
   private List<documentCluster> clusterList;

    //add
    private int topNkeyword;

    public double[][] getCosinSimilarityMatrix() {
        return cosinSimilarityMatrix;
    }


    public double[][] getjdSimilartiyMatrix() {
        return jdSimilartiyMatrix;
    }

    private double[][] cosinSimilarityMatrix;
    private double[][] jdSimilartiyMatrix;



    private double[][] topKcosinSimilartiyMatrix;


    public handleDocument(String path,int topicK,int keywordsNumber){
        this.targetDocumentPath = path;
        this.clusterNumber = topicK;
        this.topNkeyword = keywordsNumber;
        targetDocumentPathRawFile = targetDocumentPath+"raw";
        clusterList = new ArrayList<documentCluster>(clusterNumber);

        this.textProcessing();

    }

    public handleDocument(String path,int topicK){
        this.targetDocumentPath = path;
        this.clusterNumber = topicK;
        targetDocumentPathRawFile = targetDocumentPath+"raw";
        clusterList = new ArrayList<documentCluster>(clusterNumber);
        this.textProcessing();

    }


    public handleDocument(String path){
        this.targetDocumentPath = path;
        targetDocumentPathRawFile = targetDocumentPath+"raw";
        clusterList = new ArrayList<documentCluster>(clusterNumber);
        this.textProcessing();

    }

    private void textProcessing() {

        try {
            ldaProcessing();
            clusterSimlarHandle();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //处理 clustering 之间的 相似度
    private void clusterSimlarHandle() {
        cosinSimilarityMatrix = new double[clusterNumber][clusterNumber];
        jdSimilartiyMatrix = new double[clusterNumber][clusterNumber];
        topKcosinSimilartiyMatrix = new double[clusterNumber][clusterNumber];
        TextSimilarity textSimilarity = new CosineTextSimilarity();
        TextSimilarity jaccrdSim = new JaccardTextSimilarity();

        for(int i = 0 ; i<clusterNumber;i++){
            String target = clusterList.get(i).getContent();
            //topicK Simliartiy
            String keyword  = clusterList.get(i).getTopNkeywordString();
            for(int j = 0 ; j<clusterNumber;j++){
                if(i==j){
                    continue;
                }else {
                    cosinSimilarityMatrix[i][j] = textSimilarity.similarScore(target,clusterList.get(j).getContent());
                    jdSimilartiyMatrix[i][j] = jaccrdSim.similarScore(target,clusterList.get(j).getContent());
                    topKcosinSimilartiyMatrix[i][j] =textSimilarity.similarScore(keyword,clusterList.get(j).getTopNkeywordString());
                }
            }
        }


    }

    /**
     * lda processing , 关键是获取了各种矩阵，然后初始化了 cluster的list， 获取了topicKwordString，topic10WordStringWthProbalty
     * document答案的数量，以及文档的文本
     * @throws IOException
     */
    private void ldaProcessing() throws IOException {

        Corpus corpus = Corpus.load(targetDocumentPath);
        LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());
        ldaGibbsSampler.gibbs(clusterNumber);
        //get lda phi
        double[][] phi = ldaGibbsSampler.getPhi();
        Map<String, Double>[] topicMap = LdaUtil.translate(phi, corpus.getVocabulary(), 10);
//        LdaUtil.explain(topicMap);

        // 这个重复了，是选top10的keyword
        ArrayList<String> ldaWordList = new ArrayList<String>(clusterNumber);

        for (Map<String, Double> singLeMap : topicMap)
        {
            StringBuffer clusterKeyWordBuffer = new StringBuffer();

            for (Map.Entry<String, Double> entry : singLeMap.entrySet())
            {
                clusterKeyWordBuffer.append(entry+"\n");
            }
            ldaWordList.add(clusterKeyWordBuffer.toString());
        }

        //增加topic N的 keyword的list，用于cosine 相似度
        Map<String, Double>[] topicKeyWordMap = LdaUtil.translate(phi, corpus.getVocabulary(),topNkeyword);


        ArrayList<String> ldaTopNkeyWordList = new ArrayList<String>(clusterNumber);

        for (Map<String, Double> singLeMap : topicKeyWordMap)
        {
            StringBuffer clusterKeyWordBuffer = new StringBuffer();

            for (Map.Entry<String, Double> entry : singLeMap.entrySet())
            {
                clusterKeyWordBuffer.append(entry.getKey()+" ");
            }
            ldaTopNkeyWordList.add(clusterKeyWordBuffer.toString());
        }


        //get lda theta
        double[][] theta = ldaGibbsSampler.getTheta();

        //base lda get clustering text;
        ArrayList<document> report = new ArrayList<document>();
        try {
            DocumentOp document = new DocumentOp(targetDocumentPathRawFile);
            Double biggestDouble;
            int biggest;
            for(int i=0;i<theta.length;i++){
                biggestDouble = theta[i][0];
                biggest = 0;
                for(int j = 0 ; j< theta[i].length;j++){
                    if(biggestDouble<theta[i][j]){
                        biggestDouble = theta[i][j];
                        biggest = j;
                    }
                }
                document.getDocumentList().get(i).setCategory(biggest);
                report = document.getDocumentList();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int categoryNumber =clusterNumber;
        //每一个category的cluster的文章的document
        ArrayList<String> reportResult = new ArrayList<String>();
        ArrayList<Integer> reportResultNumber = new ArrayList<Integer>();
        // handle report
        for(int j = 0;j<categoryNumber;j++) {
            StringBuffer sb = new StringBuffer();
            int count = 0 ;
            for (int i = 0; i < report.size(); i++) {
                if(report.get(i).getCategory()==j){
                    sb.append(report.get(i).getContent());
//                    sb.append("\n");
                    count++;
                }
            }
            reportResult.add(sb.toString());
            reportResultNumber.add(count);
        }

//        for(int i = 0;i<clusterNumber;i++){
//            // 增加了 cluster里面的answer 数量
//            documentCluster thisCluster = new documentCluster(reportResult.get(i),ldaWordList.get(i),reportResultNumber.get(i));
//            clusterList.add(thisCluster);
//        }
        //新的构造方法
        for(int i = 0;i<clusterNumber;i++){
            // 增加了 cluster里面的answer 数量
            documentCluster thisCluster = new documentCluster(reportResult.get(i),ldaWordList.get(i),reportResultNumber.get(i),ldaTopNkeyWordList.get(i));
            clusterList.add(thisCluster);
        }

    }
    public List<documentCluster> getClusterList() {
        return clusterList;
    }

    public void setClusterList(List<documentCluster> clusterList) {
        this.clusterList = clusterList;
    }

    public static double perplexity(double[][] theta, double[][] phi,
                                    int[][] docs) {
        double perplexity = 0.0;

        int total_length = 0;
        for (int i = 0; i < docs.length; i++) {
            total_length += docs[i].length;
        }

        for (int i = 0; i < docs.length; i++) {

            for (int j = 0; j < docs[i].length; j++) {

                double prob = 0.0;
                for (int k = 0; k < phi.length; k++) {
                    prob += theta[i][k] * phi[k][docs[i][j]];
                }

                perplexity += Math.log(prob);

            }
        }

        perplexity = Math.exp(-1 * perplexity / total_length);

        return perplexity;
    }
    public double[][] getTopKcosinSimilartiyMatrix() {
        return topKcosinSimilartiyMatrix;
    }

    public void setTopKcosinSimilartiyMatrix(double[][] topKcosinSimilartiyMatrix) {
        this.topKcosinSimilartiyMatrix = topKcosinSimilartiyMatrix;
    }
}
