package Enterance;

import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.dictionary.stopword.Filter;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.BasicTokenizer;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;

import java.util.List;

import static com.hankcs.hanlp.corpus.tag.Nature.nz;

/**
 * Created by yamengwenjing on 2017-04-01.
 */
public class testHanNlp {

    public static  void main(String[] args){
        String text = "这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++";
        StringBuffer result = new StringBuffer();
        // 可以动态修改停用词词典
//        CoreStopWordDictionary.add("居民");
        System.out.println(NotionalTokenizer.segment(text));

        for(Term term :NotionalTokenizer.segment(text)){

//            System.out.println(term.word);
            result.append(term.word+" ");
        }
        System.out.println(result.toString());

//        CoreStopWordDictionary.remove("居民");
//        System.out.println(NotionalTokenizer.segment(text));
//        // 可以对任意分词器的结果执行过滤
//        List<Term> termList = BasicTokenizer.segment(text);
//        System.out.println(termList);
//        CoreStopWordDictionary.apply(termList);
//        System.out.println(termList);
//        // 还可以自定义过滤逻辑
//        CoreStopWordDictionary.FILTER = new Filter()
//        {
//            @Override
//            public boolean shouldInclude(Term term)
//            {
//                switch (term.nature)
//                {
//                    case nz:
//                        return !CoreStopWordDictionary.contains(term.word);
//                }
//                return false;
//            }
//        };
//        System.out.println(NotionalTokenizer.segment(text));
    }



    }

