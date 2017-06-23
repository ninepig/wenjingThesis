package Util;

import Entity.JMsummaryBean;
import com.google.gson.Gson;
import com.hankcs.hanlp.HanLP;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yamengwenjing on 2017-04-15.
 */
public class DocumentAux {
    /**
     * 调用han的textRankKeywords方法， 返回 topK个keywords
     * @param targetString
     * @param topKkeyword
     * @return
     */
    public static List<String> getTextRankKeywords(String targetString,int topKkeyword){
       return HanLP.extractKeyword(targetString, topKkeyword);
    }

    public static  List<String>getTextRankSentences(String targetString, int topKsentence){
            return HanLP.extractSummary(targetString,topKsentence);
    }


    public static String getSummaryFromWSsummary(String targetString){

        String resultSt = "" ;
        Gson gson = new Gson();

        // 一种是摘要长度百分比 ， 一种是固定size
        try {
            String postContent = formSendingPostContent(targetString);
//             System.out.println(postContent);
            resultSt = sendPost(postContent);
//            System.out.println(resultSt);
            JMsummaryBean jmBean = gson.fromJson(resultSt,JMsummaryBean.class);
//            System.out.println(jmBean);
            resultSt = jmBean.summary;
//            System.out.println(resultSt);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return resultSt;
    }
    /**
     * 如果传入文档过长，则限定字数，要不然按照比例生成摘要（0.3）
     * 摘要接口需要的参数是 len fizedSize（如果需要固定字数，可以省略） text
     * @param targetString
     * @return
     */

    private static  String fixedRateForSummary= "0.3";
    private static String fixedLength = "300";
    private static String formSendingPostContent(String targetString) throws Exception {
        String urlParameters = "";
        if(targetString.length()>1000){
            // we need fizedSize;
             urlParameters = "len="+ URLEncoder.encode(fixedRateForSummary, "UTF-8")+"&fizedSize="+ URLEncoder.encode(fixedLength, "UTF-8")+"&text="+URLEncoder.encode(targetString, "UTF-8");
        }else {
            // we dont need it
             urlParameters = "len="+ URLEncoder.encode(fixedRateForSummary, "UTF-8")+"&text="+URLEncoder.encode(targetString, "UTF-8");
        }
        return urlParameters;
    }

    private final static String USER_AGENT = "Mozilla/5.0";
    private static String sendPost(String postContent) throws Exception {

        String url = "http://203.195.131.67:8080/summv3/sum";
        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


//        String urlParameters = "data="+ URLEncoder.encode(question, "UTF-8");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));

        writer.write(postContent);
        writer.flush();
        wr.flush();
        wr.close();
        writer.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }




}
