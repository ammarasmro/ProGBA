package clients;

import algorithms.CreativeTripleProcessor;
import data_structures.Triple;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;


public class CorefClient {

    private StanfordCoreNLP pipeline;

    public CorefClient() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref.mention,coref");
        props.setProperty("coref.algorithm", "neural");

        pipeline = new StanfordCoreNLP(props);
    }

    public List<Triple> getTriples(String highlight) {
        List<Triple> triples = new ArrayList<>();
        Annotation document = new Annotation(highlight);

        System.out.println("Sentence: "+ document.toString());

        pipeline.annotate(document);

        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class))
			triples.addAll(CreativeTripleProcessor.processMentionsIntoTriples(
			        sentence.get(CorefCoreAnnotations.CorefMentionsAnnotation.class)));

        return triples;

    }




    public static void main(String[] args) {
        String testString = "Obama was born in Hawaii. He is our president.";
        CorefClient corefClient = new CorefClient();
        System.out.println(corefClient.getTriples(testString));

        testString = "Ammar was born in Iraq, and he never goes to Iraq";
        System.out.println(corefClient.getTriples(testString));



        testString = "Car hydraulics are installed into an automobile that allows for an adjustment in height of the vehicle.";
        System.out.println(corefClient.getTriples(testString));
        testString = "These suspensions are placed often in a lowrider, a vehicle modified so that its ground clearance is less than its original design, to give extra leverage when encountering harsh road conditions.";
        System.out.println(corefClient.getTriples(testString));
        testString = "These modifications done to the automobile can enable the body and wheels of the car to be electronically lifted off the ground, while being remote controlled.";
        System.out.println(corefClient.getTriples(testString));
        testString = "Automotive suspension design is an aspect of automotive engineering, concerned with designing the suspension for cars and trucks.";
        System.out.println(corefClient.getTriples(testString));
        testString = "Suspension design for other vehicles is similar, though the process may not be as well established.";
        System.out.println(corefClient.getTriples(testString));
        testString = "Designing the structure of each component so that it is strong, stiff, light, and cheap Analysing the vehicle dynamics of the resulting design";
        System.out.println(corefClient.getTriples(testString));
        testString = "Options included internal combustion engines fueled by petrol, diesel, propane, or natural gas; hybrid vehicles, plug-in hybrids, fuel cell vehicles fueled by hydrogen and all electric cars.";
        System.out.println(corefClient.getTriples(testString));
        testString = "internal combustion engines fueled by petrol, diesel, propane, or natural gas; hybrid vehicles, plug-in hybrids, fuel cell vehicles fueled by hydrogen and all electric cars.";
        System.out.println(corefClient.getTriples(testString));
        testString = "Fueled vehicles seemed to have the short term advantage due to the limited range and high cost of batteries.";
        System.out.println(corefClient.getTriples(testString));



        System.out.println("\n\nDone!");

        Scanner scanner = new Scanner(System.in);

        String input;
        while(true){
            System.out.println("Please enter the next sentence: ");
            input = scanner.nextLine();
            if(input.equals("stop")) break;
            System.out.println(corefClient.getTriples(input));
        }
        System.out.println("\n\nStopped!");

    }
}
