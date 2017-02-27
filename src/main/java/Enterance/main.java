package Enterance;

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
 */
public class main {

    ArrayList<Element> result = new ArrayList<Element>();

    public static  void main(String[] args){

//        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
//        Document doc = Jsoup.parse(html);
        //读取文件时的技巧，在intelj之中相对路径的开始是基于wenjingThesis这个路径的， 所以要以src开始
        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/quora.html");

        Document doc = Jsoup.parse(rawTestData);

        //选出body的内容 过滤掉 js等多余信息

        Element bodyNode = doc.body();

        //get all the div in the body.it has condition that div in side the div
        Elements divSets = bodyNode.select("div");

//        FindSimilarChildren(divSets);
//        extractNode()
       stringHelper(divSets.size()+"");
        stringHelper("*************");
        int testCount = 0;
        int tenCount = 0 ;
        for(Element divElment:divSets){
            stringHelper(divElment.select("div").size()+"");
            testCount+=divElment.select("div").size();
            if(divElment.select("div").size()>50){
                tenCount++;
            }
        }
        stringHelper("*************");
        stringHelper(testCount+"");
        stringHelper("tenCount"+tenCount+"");
    }
    /*
    输入为div的elements sets 输出为把element 作为元素的arraylist,也就是有相似的子节点的结点的集合作为结果
     因为有可能会递归找子节点的情况，所以我们用全局变量保存 resultSET
     */
    private static void FindSimilarChildren(Elements divSets) {







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

    public static  void stringHelper(String output){
        System.out.println(output);
    }

}
