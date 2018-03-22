package utils;

import data_structures.*;

public class DataStructureConverter {

    public static Triple stringsToTriple(String subject, String verb, String object){
        return new Triple(subject, verb, object);
    }

    public static Keyword stringToKeyword(String keyword){
        return new Keyword(keyword);
    }

    public static Category stringToCategory(String category){
        return new Category(category);
    }

    public static Concept stringToConcept(String concept, String linkToDBPedia){
        return new Concept(concept, linkToDBPedia);
    }

    public static SemanticRole stringsToSemanticRole(String sentence, String subject, String verb, String object){
        return new SemanticRole(sentence, stringsToTriple(subject, verb, object));
    }

    public static void main(String[] args) {

    }
}
