package algorithms;

import data_structures.Category;
import data_structures.Keyword;
import data_structures.Triple;
import interfaces.DataManagerInterface;
import interfaces.GraphDBInterface;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GraphBuilder {

    private String[] currentDocTags;
    private String[] currentUserTags;

    public GraphBuilder(){

    }

    public void updateTags(){
        currentDocTags = DataManagerInterface.getDocTags().toArray(new String[0]);
        currentUserTags = DataManagerInterface.getUserTags().toArray(new String[0]);
    }

    public void persistTriple(Triple triple){
        persistSubjectObject(triple);
        persistKeywords(triple);
    }

    private void persistSubjectObject(Triple triple){
        GraphDBInterface.addNodeWithTags("Subject", triple.getSubject().getSentence(),
                currentDocTags, currentUserTags);
        GraphDBInterface.addNodeWithTags("Object", triple.getObject().getSentence(),
                currentDocTags, currentUserTags);
        GraphDBInterface.addSubjectObjectTriple(triple.getSubject().getSentence(), triple.getVerb(),
                triple.getObject().getSentence());
    }

    private void persistKeywords(Triple triple){
        for(Keyword keyword: triple.getSubject().getKeywords()){
            GraphDBInterface.addNodeWithTags("Keyword", keyword.getKeyword(), currentDocTags, currentUserTags);
            GraphDBInterface.addAspectToKeywordLink(DataManagerInterface.getCurrentAspect(), "hasKeyword",
                    keyword.getKeyword());
            GraphDBInterface.addKeywordToSubjectLink(keyword.getKeyword(), "linksTo",
                    triple.getSubject().getSentence());
            persistCategories(keyword);
        }
        for(Keyword keyword: triple.getObject().getKeywords()){
            GraphDBInterface.addNodeWithTags("Keyword", keyword.getKeyword(), currentDocTags, currentUserTags);
            GraphDBInterface.addAspectToKeywordLink(DataManagerInterface.getCurrentAspect(), "hasKeyword",
                    keyword.getKeyword());
            GraphDBInterface.addObjectToKeywordLink(triple.getObject().getSentence(), "hasDefinition",
                    keyword.getKeyword());
            persistCategories(keyword);
        }
    }

    private void persistCategories(Keyword keyword){
        keyword.getCategories().forEach((elem) -> {
                Iterator<String> iter =  elem.getCategoryStructure().iterator();
                String currentCategory = iter.next();
                GraphDBInterface.addNodeWithTags("Category", currentCategory, currentDocTags, currentUserTags);
                GraphDBInterface.addKeywordToCategoryLink(keyword.getKeyword(), "categorized as", currentCategory);
                String previousCategory = "";
                while(iter.hasNext()){
                    previousCategory = currentCategory;
                    currentCategory = iter.next();
                    GraphDBInterface.addNodeWithTags("Category", currentCategory, currentDocTags, currentUserTags);
                    GraphDBInterface.addCategoryToHigherCategoryLink(currentCategory, "belongs to", previousCategory);
                }
        });


    }

    // TODO: add word to word triple persistors
    public void persistWordToWordTriple(Triple triple) {
        GraphDBInterface.addNodeWithTags("Definition", triple.getObject().getSentence(),
                currentDocTags, currentUserTags);
        GraphDBInterface.addKeywordToDefinitionLink(triple.getSubject().getSentence(), triple.getVerb(),
                triple.getObject().getSentence());
    }

    private void persistTags(Triple triple){
        // TODO: add tag persistors
    }


    public static void main(String[] args) {

    }


}
