package Util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by yamengwenjing on 2017-03-31.
 */
public class FileOperation {

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

        public static String fileToString(String fileName) throws IOException {
            String contents = new String(Files.readAllBytes(Paths.get(fileName)));
//            System.out.println("Contents (Java 7) : " + contents);
            return  contents;

        }


}
