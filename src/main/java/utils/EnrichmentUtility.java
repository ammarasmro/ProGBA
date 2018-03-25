package utils;

import clients.NaturalLanguageUnderstandingClient;
import data_structures.Keyword;
import data_structures.RichTextNode;

public class EnrichmentUtility {

    static NaturalLanguageUnderstandingClient client = new NaturalLanguageUnderstandingClient();

    public static void enrichNodeWithKeywords(RichTextNode node){
        node.addAllKeywords(client.getKeywordsOfSentence(node.getSentence()));
    }

    public static void enrichKeywordWithCategories(Keyword keyword){
        keyword.setCategories(client.getCategoryOfSentence(keyword.getKeyword()));
    }

}
