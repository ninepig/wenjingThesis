package Enterance;

import java.awt.datatransfer.StringSelection;

/**
 * Created by Administrator on 2017/5/13.
 */
public class testHash {
    public static void main(String[] args){

        String myString ="abc";
        int myInteger = 1;
        StringBuilder builder = new StringBuilder();
        builder.append(myString);
        builder.append(myInteger);

        System.out.print(builder.toString().hashCode());
    }
}
