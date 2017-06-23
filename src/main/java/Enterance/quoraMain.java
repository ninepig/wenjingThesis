package Enterance;

import Util.TreeSimliarity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static Util.FileOperation.readHtmlFromFile;

/**
 * Created by yamengwenjing on 2017-03-03.
 */
public class quoraMain {

    public static  void main(String[] args){

//        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
//        Document doc = Jsoup.parse(html);
        //读取文件时的技巧，在intelj之中相对路径的开始是基于wenjingThesis这个路径的， 所以要以src开始





//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/quora.html");
//        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/baidu.html");
        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/quora.html");
        Document doc = Jsoup.parse(rawTestData);

        //选出body的内容 过滤掉 js等多余信息

        Element bodyNode = doc.body();

        //get all the div in the body.it has condition that div in side the div
        Elements divSets = bodyNode.select("div");

//        FindSimilarChildren(divSets);
//        extractNode()
//       stringHelper(divSets.size()+"");
        stringHelper("*************");
        int testCount = 0;
        int tenCount = 0 ;
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


        for(int i = 0 ;i<resultElements.size();i++){
            stringHelper(resultElements.get(i).toString());
            stringHelper("#################");
            stringHelper("#################");
            stringHelper("#################");
            stringHelper("no."+i);
            stringHelper("childrenSize"+resultElements.get(i).children().size()+"");
            stringHelper("divSize"+resultElements.get(i).select("div").size()+"");
            stringHelper("#################");
            stringHelper("#################");
            stringHelper("#################");

        }



    }

    /*
    输入为div的elements sets 输出为把element 作为元素的arraylist,也就是有相似的子节点的结点的集合作为结果
     因为有可能会递归找子节点的情况，所以我们用全局变量保存 resultSET
     */




    public static  void stringHelper(String output){
        System.out.println(output);
    }

}
