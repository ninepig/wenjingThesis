package Util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by yamengwenjing on 2017-03-31.
 */
public class FileOperation {

    // list all file in target path
        public static void findFileInThePath(String path){
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println(file.getAbsolutePath());
                    //do the analysis, then created a folder to put result,
                    //do anyasis
                    //create a folder
                    // write result into folder
                }
            }
        }
    // list all sub Folder in one folder
        public String[] getSubdirectFromFolder(String path){
            File file = new File(path);
            String[] directories = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
           return directories;
        }

        // get file content using later 7.0 jdk
        public static String fileToString(String fileName) throws IOException {
            String contents = new String(Files.readAllBytes(Paths.get(fileName)));
//            System.out.println("Contents (Java 7) : " + contents);
            return  contents;

        }

        public static void fileToWordList(String fileName , ArrayList<String> abc) throws IOException {
            String content = fileToString(fileName);
            String[] words = content.split(" ");
            for(String word: words){
                abc.add(word);
            }
        }
    /*
根据文件名 ，读取全部内容，转换为string
*/
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
}
