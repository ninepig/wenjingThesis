package Enterance;

import Util.Cosine_Similarity;
import Util.TreeSimliarity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by yamengwenjing on 2017-02-24.
 */

/*
这个文件 body 下 div 464个
下面464个div下面每一个div加起来 3275 个
但没有超过400的，所以有些是重复嵌套的
整个html文件下的div数不超过480（用div搜索）
 */

/*
todo
第一步应该做找到最多div块的content块
把这个当成最终的node 传入找similar的算法

todo2
有一种可能的条件某个div下面的子div 有相似的数量
 */
public class main {






    public static  void main(String[] args){

//        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
//        Document doc = Jsoup.parse(html);
        //读取文件时的技巧，在intelj之中相对路径的开始是基于wenjingThesis这个路径的， 所以要以src开始





//        百度字符问题，gbk的问题，360 知乎 uclue 全部出来
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/baidu.html");
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/360.html");
        //todo 知乎用了第二种算法，但是会出现第一个child div数量多一个的情况
        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/zhihu.html");
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/uclue.html");
        //评论和回答一起出来，分层了
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/stackoverFlow.html");

        //网页比较复杂。
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/yahoo.html");
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/quora.html");

//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/uclue.html");

        Document doc = Jsoup.parse(rawTestData);

        //选出body的内容 过滤掉 js等多余信息

        Element bodyNode = doc.body();

        //get all the div in the body.it has condition that div in side the div
        Elements divSets = bodyNode.select("div");

//        FindSimilarChildren(divSets);
//        extractNode()
//       stringHelper(divSets.size()+"");
        stringHelper("*************");

        Element biggest = divSets.first();


        int biggestNumber = 0;


        //quora出现了第一级就找到节点了，没有到子节点去找的问题
        for(Element divElment:divSets){
            if(divElment.select("div").size()>biggestNumber ){
                biggest = divElment;
                biggestNumber=divElment.select("div").size();
            }
//            stringHelper(divElment.select("div").size()+"");
        }
//        stringHelper(biggest.toString());
//        //TODO node children 是获取最大一层下一层的dom结构，就一层,所以judge的时候要考虑他可能只有非常少的childsize
//        //DONE
//
        //biggestDiv 是mainContent（正文部分）下面的子节点，
        Elements biggestDiv = biggest.children();
//        stringHelper("biggestDivChildren"+biggestDiv.size());
        Elements resultElements = new Elements();
        TreeSimliarity tool = new TreeSimliarity(biggestDiv);
        tool.start();
        resultElements = tool.getResultElements();
        stringHelper("resultElmentSize"+resultElements.size()+"");



//        for(Element testResult:resultElements){
//
//
//        }
        for(int i = 0 ;i<resultElements.size();i++){
            stringHelper(resultElements.get(i).toString());
            stringHelper("#################");
            stringHelper("#################");
            stringHelper("#################");
            stringHelper("no."+i);
            stringHelper("childrenSize"+resultElements.get(i).children().size()+"");
            stringHelper("ContainDivSize"+resultElements.get(i).select("div").size()+"");
            stringHelper("#################");
            stringHelper("#################");
            stringHelper("#################");

        }



//        stringHelper(biggest.toString());
//        stringHelper(biggest.select("div").size()+"");


        //get main part
//        stringHelper("#######");
//        Element test = biggest.select("#zh-question-answer-wrap").first();
//        stringHelper(test.toString());
//        int testCounter=0;
//        int oneCounter =0;

        //our target div
//        Elements target = new Elements();
//
//
//        for(Element divElment:test.select("div")){
//            if(divElment.select("div").size()==14||divElment.select("div").size()==15){
////               stringHelper(divElment.toString());
////                stringHelper("#######");
//                target.add(divElment);
//
////                stringHelper("childrenSize"+divElment.children().size()+"");
//                testCounter++;
//            }
//            stringHelper("containDivSize"+divElment.select("div").size()+"");
//            stringHelper("childrenSize"+divElment.children().size()+"");

//            if(divElment.select("div").size()==1){
//                oneCounter++;
//            }
//        }
//        stringHelper("#######");
//        stringHelper(test.select("div").size()+"");
//        stringHelper("oneCounter"+oneCounter);

//        test function from jsoup
//        Element first = target.get(0);
//
//        stringHelper("##test function of Jsoup##");
//        stringHelper("#######");
//        //get child
//        for(Element children:first.children()){
////            get children
////            stringHelper(children.toString());
////            stringHelper("#######");
//            stringHelper(children.tagName());
//            stringHelper("#######");
//        }


        // get all elements of targetDiv ,including children of children
//        for(Element allElement:first.getAllElements()){
//            stringHelper("#######");
//            stringHelper(allElement.tagName());
//
//        }

//
//
//
//        stringHelper("#######");
//        stringHelper(testCounter+"");




        //test part to get div size bigger than 50
//        for(Element divElment:divSets){
//            stringHelper(divElment.select("div").size()+"");
//            testCount+=divElment.select("div").size();
//            if(divElment.select("div").size()>50 ){
//                tenCount++;
//            }
//        }
//        stringHelper("*************");
//        stringHelper(testCount+"");
//        stringHelper("tenCount"+tenCount+"");
    }

    /*
    输入为div的elements sets 输出为把element 作为元素的arraylist,也就是有相似的子节点的结点的集合作为结果
     因为有可能会递归找子节点的情况，所以我们用全局变量保存 resultSET
     */


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

    public static  void stringHelper(String output){
        System.out.println(output);
    }

}
