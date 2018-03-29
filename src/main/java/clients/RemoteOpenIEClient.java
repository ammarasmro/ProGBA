package clients;

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


    public RemoteOpenIEClient(){
        props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
        props.setProperty("openie.triple.all_nominals", "true");

        pipeline = new StanfordCoreNLPClient(props, "http://localhost", 8000, 2);


    }

    public List<Triple> getTriples(String sentence){
        annotateThis(sentence);
        List<Triple> triples = new ArrayList<>();
        for (CoreMap line : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            // Get the OpenIE triples for the sentence
            Collection<RelationTriple> relationTriples =
                    line.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            for (RelationTriple relationTriple : relationTriples) {
                String subject = relationTriple.subjectGloss();
                String verb = relationTriple.relationGloss();
                String object = relationTriple.objectGloss();
                triples.add(DataStructureConverter.stringsToTriple(subject, verb, object));
            }
        }

        return triples;
    }

    private void annotateThis(String sentence){
        document = new Annotation(sentence);
        pipeline.annotate(document);
    }


}
