package algorithms;

import data_structures.Triple;
import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import utils.DataStructureConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreativeOpenIEProcessor {

    public static List<Triple> processRelationsIntoTriples(Collection<RelationTriple> relationTriples){
        List<Triple> triples = new ArrayList<>();

        relationTriples.removeIf(s -> {return isRelationIN(s) || isObjectEndWithVBN(s);});

        for(RelationTriple relationTriple: relationTriples)
            triples.add(DataStructureConverter.relationTripleToTriple(relationTriple));

        return triples;
    }

    private static boolean isRelationIN(RelationTriple relationTriple){
        if(relationTriple.relation.size() == 1 &&
                (relationTriple.relation.get(0).getString(CoreAnnotations.PartOfSpeechAnnotation.class).equals("IN"))){
            return true;
        }
        return false;
    }

    private static boolean isObjectEndWithVBN(RelationTriple relationTriple){
        if(relationTriple.object.get(relationTriple.object.size() - 1)
                .getString(CoreAnnotations.PartOfSpeechAnnotation.class).equals("VBN")){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("one");
        strings.add("two");
        strings.add("three");

        strings.removeIf(s -> {return s.equals("two");});

        System.out.println(strings);
    }


}
