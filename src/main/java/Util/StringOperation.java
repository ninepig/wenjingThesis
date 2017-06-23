package Util;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/5/14.
 */
public class StringOperation {

    public static int StringToHash(String targetString){

        int myInteger = 1;
        StringBuilder builder = new StringBuilder();
        builder.append(targetString);
        builder.append(myInteger);
        return Math.abs(builder.toString().hashCode());
    }

    //分词
    public static String textHandling(String content) {
        StringBuffer result = new StringBuffer();
        for(Term term : NotionalTokenizer.segment(content)){
            result.append(term.word+" ");
        }
        return result.toString();
    }

    //分词
    public static List<String> textHandlingList(String content) {
        List<String> list = new ArrayList<String>();
        for(Term term : NotionalTokenizer.segment(content)){
            list.add(term.word);
        }
        return list;
    }


    public  static int zhihuRateNumber(String content) {
        String pattern = "(\\d+) 人";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(content);
        if (m.find()) {
//            System.out.println("Found value: " + m.group(0));
//            System.out.println("Found value: " + m.group(1));
            return Integer.parseInt(m.group(1));
        } else {
//            System.out.println("NO MATCH");
            return 0;
        }

    }

    public static  String zhihufilter(String content){
        int index = content.indexOf("发布于");
        String newString = content.substring(0,index);
        return newString;
    }

    public static  boolean zhihuContentJudge(String content){
        if (content.contains("发布于")&&content.contains("分享 收藏 感谢")){
            return true;
        }
        return false;
    }
}
