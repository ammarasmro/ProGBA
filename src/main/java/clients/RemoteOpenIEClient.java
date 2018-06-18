package clients;

import algorithms.CreativeOpenIEProcessor;
import data_structures.Triple;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;
import edu.stanford.nlp.util.CoreMap;
import utils.DataStructureConverter;

import java.util.*;

public class RemoteOpenIEClient {


    Properties props;
    StanfordCoreNLPClient pipeline;
    Annotation document;

    private boolean status = false;

    public RemoteOpenIEClient(){
        props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
        props.setProperty("openie.triple.all_nominals", "true");

        try {
            pipeline = new StanfordCoreNLPClient(props, "http://54.213.71.86", 8000, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        status = true;

    }

    public List<Triple> getTriples(String sentence){
        annotateThis(sentence);
        List<Triple> triples = new ArrayList<>();
        for (CoreMap line : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            // Get the OpenIE triples for the sentence
            Collection<RelationTriple> relationTriples =
                    line.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            triples.addAll(CreativeOpenIEProcessor.processRelationsIntoTriples(relationTriples));
        }

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
        RemoteOpenIEClient openIEClient = new RemoteOpenIEClient();
        System.out.println(openIEClient.getTriples("Ammar is the best"));
    }


}
