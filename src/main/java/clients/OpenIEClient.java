package clients;

import data_structures.Triple;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class OpenIEClient {
    private Properties props;
    private StanfordCoreNLP pipeline;

    private NaturalLanguageUnderstandingClient nluClient;

    public OpenIEClient() {
        props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie,ner,parse,dcoref");
        props.setProperty("openie.triple.all_nominals", "true");

        pipeline = new StanfordCoreNLP(props);
        nluClient = new NaturalLanguageUnderstandingClient();
    }

    public List<Triple> annotateSentence(String highlight) {
        List<Triple> triples = new ArrayList<Triple>();
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
                Triple triple = new Triple(subject, verb, object);
                triples.add(triple);
            }
        }
        enrichTriples(triples);
        return triples;
    }

    public void enrichTriples(List<Triple> listOfTriples) {
//		for(Triple triple: listOfTriples) {
//			nluClient.enrichRichNode(triple.getSubjectTry());
//			nluClient.enrichRichNode(triple.getObjectTry());
//		}
    }

    public String printTriple(RelationTriple relationTriple) {
        return relationTriple.confidence + "\t" +
                relationTriple.subjectGloss() + "\t" +
                relationTriple.relationGloss() + "\t" +
                relationTriple.objectGloss();
    }


    public static void main(String[] args) throws Exception {
        String testString = "Obama was born in Hawaii. He is our president.";
        OpenIEClient openIEClient = new OpenIEClient();
        System.out.println(openIEClient.annotateSentence(testString));

        System.out.println("\n\nDone!");

    }
}
