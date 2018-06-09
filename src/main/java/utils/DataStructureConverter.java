package utils;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SemanticRolesKeyword;
import data_structures.*;
import edu.stanford.nlp.ie.util.RelationTriple;

import java.util.List;

public class DataStructureConverter {

    public static Triple stringsToTriple(String subject, String verb, String object){
        Triple triple = new Triple(subject, verb, object);
//        EnrichmentUtility.enrichNodeWithKeywords(triple.getSubject());
//        EnrichmentUtility.enrichNodeWithKeywords(triple.getObject());
        return triple;
    }

    public static Keyword stringToKeyword(String keywordString){
        Keyword keyword = new Keyword(keywordString);
        EnrichmentUtility.enrichKeywordWithCategories(keyword);
        return keyword;
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

    public static SemanticRole semanticResultStringsToSemanticRole(String sentence, String subject, String verb, String object,
                                                                   List<SemanticRolesKeyword> subjectKeywords,
                                                                   List<SemanticRolesKeyword> objectKeywords){
        SemanticRole semanticRole = new SemanticRole(sentence, stringsToTriple(subject, verb, object));
        if(subjectKeywords != null)
            for(SemanticRolesKeyword keyword: subjectKeywords){
                semanticRole.getTriple().getSubject().addKeyword(new Keyword(keyword.getText()));
            }
        if(objectKeywords != null)
            for(SemanticRolesKeyword keyword: objectKeywords){
                semanticRole.getTriple().getObject().addKeyword(new Keyword(keyword.getText()));
            }
        return semanticRole;
    }

    public static DrQAResponseDocument[] jsonStringToDrQADocsArray(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, DrQAResponseDocument[].class);
    }

    public static WikipediaDocument[] jsonStringToWikipediaDocumentsArray(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, WikipediaDocument[].class);
    }

    public static Triple relationTripleToTriple(RelationTriple relationTriple){
        return stringsToTriple(relationTriple.subjectGloss(), relationTriple.relationGloss(), relationTriple.objectGloss());
    }

    public static PipelineDocument wikiDocumentToPipelineDocument(WikipediaDocument wikiDoc, int resultNumber){
        return new PipelineDocument(wikiDoc.getTitle(), wikiDoc.getContent(), resultNumber);
    }

    public static void main(String[] args) {

    }
}
