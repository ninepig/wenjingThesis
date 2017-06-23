package Entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by yamengwenjing on 2017-04-15.
 * 用于解析摘要的json 对应的bean
 */
public class JMsummaryBean {
    public boolean overLength;
    public String summary;
    public List<List<Text>> text;
    public List<HighAverScoreSnts> highAverScoreSnts;
    public List<HighScoreSnts> highScoreSnts;
    public String info;

    @Override
    public String toString() {
        return "JMsummaryBean{" +
                "overLength=" + overLength +
                ", summary='" + summary + '\'' +
                ", highAverScoreSnts=" + highAverScoreSnts +
                ", highScoreSnts=" + highScoreSnts +
                ", info='" + info + '\'' +
                '}';
    }
    public  class Text {
        public Boolean title;
        public String processed;
        public Boolean flag;
        public String sentence;
    }

    public  class HighScoreSnts{
        public double score;
        public String sentence;
    }

    public  class HighAverScoreSnts{
        public double averscore;
        public double score;
        public String sentence;
    }


}
