package interfaces;


import clients.NaturalLanguageUnderstandingClient;
import data_structures.Category;
import data_structures.Concept;
import data_structures.Keyword;
import data_structures.SemanticRole;

import java.util.List;

public class NLUInterface {

    private static NaturalLanguageUnderstandingClient nluClient = new NaturalLanguageUnderstandingClient();


    public static List<Keyword> getKeywordsOf(String sentence){
        return nluClient.getKeywordsOfSentence(sentence);
    }

    public static List<Category> getCategoryOf(String sentence){
        return nluClient.getCategoryOfSentence(sentence);
    }

    public static List<Concept> getConceptsOf(String sentence){
        return nluClient.getConceptsOfSentence(sentence);
    }

    public static List<SemanticRole> getSemanticRolesOf(String document){
        return nluClient.getSemanticRolesOfDocument(document);
    }
}
