package Enterance;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yamengwenjing on 2017-02-24.
 */
public class main {


    public static  void main(String[] args){

//        String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
//        Document doc = Jsoup.parse(html);
        //读取文件时的技巧，在intelj之中相对路径的开始是基于wenjingThesis这个路径的， 所以要以src开始
        String rawTestData = readHtmlFromFile("src/main/res/rawHtml/zhihu.html");

        Document doc = Jsoup.parse(rawTestData);

        //选出body的内容 过滤掉 js等多余信息

        Element bodyNode = doc.body();

        //get all the div in the body.it has condition that div in side the div
        Elements divSets = bodyNode.select("div");

//        FindSimilarChildren()
//        extractNode()




       stringHelper(divSets.size()+"");





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
