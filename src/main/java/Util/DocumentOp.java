package Util;

import Entity.document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by yamengwenjing on 2017-04-02.
 */
public class DocumentOp {
    public ArrayList<document> getDocumentList() {
        return documentList;
    }

    ArrayList<document> documentList;
    public DocumentOp(String folderPath) throws IOException {
        documentList = new ArrayList<document>();
        loadingDocument(folderPath);
    }

    private void loadingDocument(String folderPath) throws IOException {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
//                System.out.println(file.getAbsolutePath());
                String contents = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                document thisD = new document(contents);
                documentList.add(thisD);
            }
        }
    }


}
