package algorithms;

import data_structures.PipelineDocument;
import data_structures.Triple;
import interfaces.DataManagerInterface;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.CosineSimilarity;

public class Cleanser {

    private PipelineDocument currentDocument;

    private Collection<Triple> triplesCollection;

    private Set<Triple> wordToWordTriples;
    private Set<Triple> normalTriples;




    public Cleanser(PipelineDocument document){
        this.currentDocument = document;
//        this.triplesCollection = triplesCollection;

        this.wordToWordTriples = new HashSet<>();
        this.normalTriples = new HashSet<>();
    }

    public void cleanseCollection(PipelineDocument document, Collection<Triple> triplesCollection){
        this.currentDocument = document;
        this.triplesCollection = triplesCollection;
        for(Triple triple: triplesCollection){
            if(isUnique(triple)) {
                extractWordToWordTriples(triple);
                document.getTriples().add(triple);
            }
        }
    }

    public void extractWordToWordTriples(Triple triple){
        if(isShortTriple(triple))
            wordToWordTriples.add(triple);
        else
            normalTriples.add(triple);

    }

    public boolean isUnique(Triple triple){
        CosineDistance dist = new CosineDistance();
        for(Triple storedTriple: currentDocument.getTriples()){
            System.out.println("Distance");
            System.out.println(triple.getTripleAsString());
            System.out.println(storedTriple.getTripleAsString());
            double distance = dist.apply(triple.getTripleAsString(), storedTriple.getTripleAsString());
            System.out.println("Distance " + distance);
            if(distance < 0.1) return false;
        }
        return true;
    }

    private boolean isShortTriple(Triple triple){
        return triple.getTripleAsString().split(" ").length <= 4;
    }

    public Collection<Triple> getNormalTriples(){ return this.normalTriples; }

    public Collection<Triple> getWordToWordTriples(){ return this.wordToWordTriples; }

    public static void main(String[] args) {
        CosineDistance sim = new CosineDistance();
        double dist = sim.apply("typically done by team from different disciplines included within automotive engineering",
                "typically done by team from many different disciplines included within engineering");
        System.out.println(dist);

    }




}
