package Enterance;

import com.hankcs.hanlp.HanLP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by yamengwenjing on 2017-04-09.
 */
public class textRankGetKeyWord {
    public static  void main(String[] args){
        String fileName ="C:\\Users\\yamengwenjing\\Desktop\\wenjingWork\\interviewAlgorithm\\wenjingThesis\\src\\main\\res\\grabbedAnswer\\zhihu\\ldaCorpus\\textrank";
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(Paths.get(fileName)));
            System.out.println(contents);
        List<String> keywordList = HanLP.extractKeyword(contents, 10);
//            System.out.println(keywordList);

        System.out.println(keywordList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
