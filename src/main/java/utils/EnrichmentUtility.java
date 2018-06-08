package utils;

import clients.NaturalLanguageUnderstandingClient;
import conversation_datastructures.UserUtterance;
import data_structures.Concept;
import data_structures.Keyword;
import data_structures.RichTextNode;

import java.util.List;

public class EnrichmentUtility {

    static NaturalLanguageUnderstandingClient client = new NaturalLanguageUnderstandingClient();

    public static void enrichNodeWithKeywords(RichTextNode node){
        node.addAllKeywords(client.getKeywordsOfSentence(node.getSentence()));
    }

    public static void enrichKeywordWithCategories(Keyword keyword){
        keyword.setCategories(client.getCategoryOfSentence(keyword.getKeyword()));
    }

    public static List<Concept> getConceptsOfText(String utterance){
        return client.getConceptsOfSentence(utterance);
    }

}
