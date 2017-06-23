package Enterance;

import Dao.answerDao;
import Dao.initialDao;
import Dao.questionDao;
import cn.edu.hfut.dmic.contentextractor.ContentExtractor;
import dbEntity.Answer;
import dbEntity.Question;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;


import static Util.FileOperation.readHtmlFromFile;
import static Util.StringOperation.*;
import static pageHandle.HtmlHandling.getTargetElementList;
import static pageHandle.HtmlHandling.getTargetTitle;

/**
 * Created by Administrator on 2017/5/13.
 * 预处理文件，从html到抽取网页到数据库的存储
 */
public class preProcessingRawData {

    final static Logger logger = Logger.getLogger(preProcessingRawData.class);
    public static void main(String[] args) {
        //rawdata position
        String targetFolderPath = "src/main/res/dbVersionRawData";
        File folder = new File(targetFolderPath);
        File[] listOfFiles = folder.listFiles();

        //get questionDao
        questionDao questionDb = initialDao.getquestionDao();
        answerDao anwerDb = initialDao.getAnserDao();


        for (File file : listOfFiles) {
            if (file.isFile()) {
                //html raw file to string
                String rawTestData = readHtmlFromFile(file.getAbsolutePath());
                String title = getTargetTitle(rawTestData);
                //根据知乎处理的 少4位长度 删掉 - 知乎
                title = title.substring(0, title.length() - 4);
                logger.debug("title" + title);
                //通过算法获取位置文件的目标集合，然后获取集合size
                Elements targetResult = getTargetElementList(rawTestData);
                logger.debug("size" + targetResult.size());
                int titleId = StringToHash(title);
                logger.debug("ID" + titleId);
                Question thisQuestion = new Question(titleId,title,targetResult.size());
                questionDb.insert(thisQuestion);

                //handle answer part

                for (Element result : targetResult) {
                    try {
                        //用的webcollectoer的抽取，利用的是网页密度
                        String rawContent = ContentExtractor.getContentByHtml(result.toString());
                        if(zhihuContentJudge(rawContent)){
                            continue;
                        }
                        String splitContent = textHandling(rawContent);

                        int rateNumber = zhihuRateNumber(result.toString());

                        logger.debug("rawContent" + rawContent);
                        logger.debug("splitContent" + splitContent);
                        logger.debug("rateNumber" + rateNumber);
                        Answer thisAnswer = new Answer(titleId,rawContent,rateNumber,splitContent);
                        anwerDb.insert(thisAnswer);
                        logger.debug("insertSuccess");
                    } catch (Exception e) {
                       logger.error(e.toString());
                    }

                }
            }


        }
    }


}
