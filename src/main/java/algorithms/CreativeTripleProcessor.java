package algorithms;

import data_structures.Triple;

import edu.stanford.nlp.coref.data.Mention;
import utils.DataStructureConverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreativeTripleProcessor {


    public static List<Triple> processMentionsIntoTriples(List<Mention> mentions){
        String subject = "", verb, object;
        int depth = 4, i = 1;
        List<Triple> triples = new ArrayList<Triple>();
        if(mentions.size() < 2) return triples;

        Iterator<Mention> iterator = mentions.iterator();
        Mention temp = iterator.next();
        if(isVerbNull(temp)) {
            triples.add(extractIsRelation(mentions));
            return triples;
        }

        subject = (isSubjectMaterial(temp)) ? temp.spanToString() : findSubject(iterator);
        while(iterator.hasNext() && i < depth){
            temp = iterator.next();
            if(isObjectMaterial(temp)){
                triples.add(DataStructureConverter.stringsToTriple(subject, temp.dependingVerb.word(), temp.spanToString()));
            }
            i++;
        }
        return triples;
    }

    private static boolean isSubjectMaterial(Mention mention){
        return (mention.isSubject || mention.isDirectObject);
    }

    private static boolean isObjectMaterial(Mention mention){
        return (mention.isDirectObject || mention.isIndirectObject || mention.isPrepositionObject);
    }

    private static boolean isVerbNull(Mention mention){
        return mention.dependingVerb == null;
    }

    private static Triple extractIsRelation(List<Mention> mentions){
        return DataStructureConverter.stringsToTriple(mentions.get(0).spanToString(),"is", mentions.get(1).spanToString());
    }

    private static String findSubject(Iterator<Mention> iterator){
        Mention temp;
        while(iterator.hasNext()){
            temp = iterator.next();
            if(isSubjectMaterial(temp))
                return temp.spanToString();
        }
        return "";
    }
}