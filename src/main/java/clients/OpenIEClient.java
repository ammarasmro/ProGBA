package clients;

import data_structures.Triple;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import utils.DataStructureConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


public class OpenIEClient {
    private StanfordCoreNLP pipeline;


    public OpenIEClient() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner,parse,dcoref,coref.mention");
        props.setProperty("openie.triple.all_nominals", "true");

        pipeline = new StanfordCoreNLP(props);
    }

    public List<Triple> getTriples(String highlight) {
        List<Triple> triples = new ArrayList();
        Annotation doc = new Annotation(highlight);
        pipeline.annotate(doc);

        // Loop over sentences in the document
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            // Get the OpenIE triples for the sentence
            Collection<RelationTriple> relationTriples =
                    sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            for (RelationTriple relationTriple : relationTriples) {
                String subject = relationTriple.subjectGloss();
                String verb = relationTriple.relationGloss();
                String object = relationTriple.objectGloss();
                triples.add(DataStructureConverter.stringsToTriple(subject, verb, object));
            }
        }
        return triples;
    }

    public static void main(String[] args) throws Exception {
        String testString = "Obama was born in Hawaii. He is our president.";
        OpenIEClient openIEClient = new OpenIEClient();
        System.out.println(openIEClient.getTriples(testString));

        System.out.println("\n\nDone!");

    }
}
