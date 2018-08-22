package clients;

import algorithms.CreativeTripleProcessor;
import data_structures.Triple;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;
import edu.stanford.nlp.util.CoreMap;
import utils.DataStructureConverter;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class RemoteCorefClient {

    private Properties props;
    private StanfordCoreNLPClient pipeline;
    private Annotation document;
    private String CYBERA_IP;

    private boolean status = false;


    public RemoteCorefClient(){
        props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref.mention,coref");
        props.setProperty("coref.algorithm", "neural");

        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream("src/main/resources/ProGBA.config"));
            CYBERA_IP = prop.getProperty("CYBERA_IP");
        } catch(Exception e){
            e.printStackTrace();
        }


        try{
            pipeline = new StanfordCoreNLPClient(props, CYBERA_IP, 9000, 2);
        } catch (Exception e){
            e.printStackTrace();
        }

        status = true;

    }

    public List<Triple> getTriples(String sentence){
        annotateThis(sentence);
        List<Triple> triples = new ArrayList<>();
        for (CoreMap line : document.get(CoreAnnotations.SentencesAnnotation.class))
            triples.addAll(CreativeTripleProcessor.processMentionsIntoTriples(
                    line.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)));

        return triples;
    }

    private void annotateThis(String sentence){
        document = new Annotation(sentence);
        pipeline.annotate(document);
    }

    public boolean getStatus(){
        return status;
    }

    public static void main(String[] args) {
        RemoteCorefClient client = new RemoteCorefClient();
        System.out.println(client.getTriples("Ammar is the best. He was born in Iraq"));
    }
}
