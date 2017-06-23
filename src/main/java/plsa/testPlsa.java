package plsa;

import java.io.IOException;

import static Util.FileOperation.fileToWordList;

/**
 * Created by yamengwenjing on 2017-04-27.
 */
public class testPlsa {
    public static void main(String[] args) throws IOException {
        String targetFolder = "C:\\Users\\yamengwenjing\\Desktop\\wenjingWork\\interviewAlgorithm\\wenjingThesis\\src\\main\\res\\grabbedAnswer\\zhihu\\ldaCorpus\\3.html";

        Documents docSet = new Documents();
        System.out.println("0 Read Docs ...");
        docSet.readDocs(targetFolder);
        System.out.println("docSet: " + docSet.docs.size());
        Plsa model = new Plsa();
        System.out.println("1 Initialize the model ...");
        model.initializeModel(docSet);
        System.out.println("2 Learning and Saving the model ...");
        model.inferenceModel(docSet);
        System.out.println("3 Output the final model ...");
        model.saveIteratedModel(ParamConfig.iteration, docSet);
        System.out.println("Done!");
    }
}