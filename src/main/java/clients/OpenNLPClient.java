package clients;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class OpenNLPClient {

    private SentenceDetectorME sentenceDetector;

    public OpenNLPClient(){
        try (InputStream modelIn = new FileInputStream("src/main/resources/opennlp-resources/en-sent.bin")) {
            SentenceModel model = new SentenceModel(modelIn);
            sentenceDetector = new SentenceDetectorME(model);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] StringToSentences(String text){
        return sentenceDetector.sentDetect(text);
    }

    public static void main(String[] args) {
        SentenceModel model = null;
        try (InputStream modelIn = new FileInputStream("src/main/resources/opennlp-resources/en-sent.bin")) {
            model = new SentenceModel(modelIn);
        } catch (Exception e){
            e.printStackTrace();
        }

        SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

        String sentences[] = sentenceDetector.sentDetect("  First sentence. Second sentence. ");

        System.out.println(Arrays.toString(sentences));

    }
}
