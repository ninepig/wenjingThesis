package Util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by yamengwenjing on 2017-03-03.
 */
public class TreeSimliarity {

    Elements targetElements;
    Elements resultElements;



    public TreeSimliarity(Elements targetElements){
        this.resultElements = new Elements();
        this.targetElements = targetElements;
    }



    public  void start(){
        FindSimilarChildren(targetElements);
    }

    public  void FindSimilarChildren(Elements divSets) {
        System.out.println("looping");
        if(divSets.size()==0){
            return ;
        }
        Elements childSet = new Elements();
        Element compareNode,compareNodeSecond;
        int repeatNodeCounter;
        for(int i = 0 ; i<divSets.size();i++){
            repeatNodeCounter = 0;
            compareNode = divSets.get(i);
            //judge 不能用 compareNode.children().size()<3，因为有可能上一层只有1个children，用了这个 这个div就会被跳过，
            if(compareNode.select("div").size()<5){
                continue;
            }
            for(Element childNode:compareNode.children()){
                childSet.add(childNode);
            }
            for(int j = 0 ; j<divSets.size();j++){
                //因为要重复比较，跳过同一个
                if(i==j){
                    continue;
                }
                compareNodeSecond= divSets.get(j);
                if(compareNodeSecond.select("div").size()<5){
                    continue;
                }
                if(doNodeSimilarity(compareNode,compareNodeSecond)){
                    repeatNodeCounter++;
                }
            }
            if(repeatNodeCounter>=2){
                resultElements.add(compareNode);
            }
        }

        if(resultElements.size()==0){
            FindSimilarChildren(childSet);
        }

    }

    private  boolean doNodeSimilarity(Element compareNode, Element compareNodeSecond) {


        StringBuffer TAGoneBuffer = new StringBuffer(),TAGtwoBuffer = new StringBuffer();
        for(Element firstChild:compareNode.children()){
            TAGoneBuffer.append(firstChild.tagName()+"");
        }
        for(Element secChild:compareNodeSecond.children()){
            TAGtwoBuffer.append(secChild.tagName()+"");
        }
        Cosine_Similarity cs1 = new Cosine_Similarity();
        double sim_score = cs1.Cosine_Similarity_Score(TAGoneBuffer.toString(),TAGtwoBuffer.toString());
        if(sim_score>0.9){
            return true;
        }else {
            return false;
        }

    }

    public Elements getResultElements() {
        return resultElements;
    }

    public void setResultElements(Elements resultElements) {
        this.resultElements = resultElements;
    }
}
