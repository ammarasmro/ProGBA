package algorithms;

import clients.CorefClient;
import clients.OpenIEClient;
import data_structures.SemanticRole;
import data_structures.Triple;
import interfaces.CoreNLPInterface;
import interfaces.CorefInterface;
import interfaces.OpenIEInterface;
import utils.DataStructureConverter;

import java.util.*;

public class SentenceProcessingPipeline {


    private String sentence;
    private Set<Triple> triples;
    private Map<String, List<Triple>> sentenceToTriplesMap;
    private List<Triple> openIETriples;
    private List<Triple> corefTriples;

    public SentenceProcessingPipeline(){
        sentence = "";
    }

    public SentenceProcessingPipeline(SemanticRole semanticRole){
        triples = new HashSet<>();
        openIETriples = new ArrayList<>();
        corefTriples = new ArrayList<>();

        this.sentence = semanticRole.getSentence();
        this.triples.add(semanticRole.getTriple());

    }

    public void progressThroughPipeline(){
        processSentenceWithOpenIE();
        processSentenceWithCoref();
    }

    private void processSentenceWithOpenIE(){
        openIETriples.addAll(CoreNLPInterface.getOpenIETriplesOf(sentence));
        triples.addAll(openIETriples);
    }

    private void processSentenceWithCoref(){
//        corefTriples.addAll(CoreNLPInterface.getCorefTriplesOf(sentence));
        triples.addAll(corefTriples);
    }

    public Set<Triple> getTriples(){
        return triples;
    }

    public static void main(String[] args) {
        SentenceProcessingPipeline pipeline = new SentenceProcessingPipeline(new SemanticRole("ammar is the best in the whole world",
                DataStructureConverter.stringsToTriple("ammar", "is", "the best")));
        pipeline.progressThroughPipeline();

        System.out.println(pipeline.getTriples());

//        Scanner scanner = new Scanner(System.in);
//
//        String input;
//        while(true){
//            System.out.println("Please enter the next sentence: ");
//            input = scanner.nextLine();
//            if(input.equals("stop")) break;
//            System.out.println(corefClient.getTriples(input));
//        }
//        System.out.println("\n\nStopped!");
//

    }

}
