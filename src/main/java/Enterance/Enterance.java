package Enterance;

import Util.TreeSimliarity;
import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.Arrays;

/**
 * Created by yamengwenjing on 2017-03-31.
 *数据源处理
 * 对每一个网页文件做处理，
 * 1提取出问答部分。这一个是对知乎的处理（div size）
 * 2 对问答部分提取正文，提取正文后，将正文保存到两个文件之中 1个是分词以后的 2 是raw 文件
 *
 */

public class Enterance {

    public static void  main(String[] args){

        String targetFolderPath="src/main/res/grabbedAnswer/zhihu";
        //todo 要加一行代码，这样不用手动创建ldaCorpus
        String corpusFolderPath = "src/main/res/grabbedAnswer/zhihu/ldaCorpus";

        File folder = new File(targetFolderPath);
        File[] listOfFiles = folder.listFiles();


        for (File file : listOfFiles) {
            if (file.isFile()) {
//                System.out.println(file.getAbsoluteFile());
                String thisFileName = file.getName();
                String rawTestData = readHtmlFromFile(file.getAbsolutePath());
                //do the analysis, then created a folder to put result,
                Elements targetResult = getTargetElementList(rawTestData);
                System.out.println("targetSize"+targetResult.size());
                //create a folder
                creatCorpusFolder(corpusFolderPath+"/"+thisFileName);
                creatCorpusFolder(corpusFolderPath+"/"+thisFileName+"raw");
                int counter = 1;
                for(Element result:targetResult){
                    try {
                        //把原数据和 新数据全部写在文件之中
                        String content = ContentExtractor.getContentByHtml(result.toString());
                        String splitContent = textHandling(content);
                        File newSplitTextFile = new File(corpusFolderPath+"/"+thisFileName+"/"+counter+".txt");
                        File newTextFile = new File(corpusFolderPath+"/"+thisFileName+"raw/"+counter+".txt");
                        FileWriter fw = new FileWriter(newTextFile);
                        FileWriter fw2 = new FileWriter(newSplitTextFile);
                        fw.write(content);
                        fw2.write(splitContent);
                        fw.close();
                        fw2.close();
                        counter++;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // write result into folder
                }

            }
        }

    }
    //todo 分词 停用词
    private static String textHandling(String content) {
        StringBuffer result = new StringBuffer();
        for(Term term : NotionalTokenizer.segment(content)){
            result.append(term.word+" ");
        }
        return result.toString();
    }


    public static void creatCorpusFolder(String folderName){
        File file = new File(folderName);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public static String readHtmlFromFile(String fileName){
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;

            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
//                stringHelper(str);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = contentBuilder.toString();
        return content;
    }

    public static Elements getTargetElementList(String sourcingInput){
        Document doc = Jsoup.parse(sourcingInput);
        Element bodyNode = doc.body();
        Elements divSets = bodyNode.select("div");
        Element biggest = divSets.first();
        int biggestNumber = 0;

        for(Element divElment:divSets){
            if(divElment.select("div").size()>biggestNumber ){
                biggest = divElment;
                biggestNumber=divElment.select("div").size();
            }
        }
        Elements biggestDiv = biggest.children();
//        stringHelper("biggestDivChildren"+biggestDiv.size());
        Elements resultElements = new Elements();
        TreeSimliarity tool = new TreeSimliarity(biggestDiv);
        tool.start();
        resultElements = tool.getResultElements();

        return  resultElements;
    }
}
