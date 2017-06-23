package pageHandle;

import Util.TreeSimliarity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2017/5/14.
 */
public class HtmlHandling {

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

    public static String getTargetTitle(String sourcingInput) {
        Document doc = Jsoup.parse(sourcingInput);
        return doc.title();

    }
}
