package data;

import algorithms.Pipeline;
import conversation_datastructures.UserUtterance;
import data_structures.*;

import java.util.*;

public class DataManager {

    List<PipelineDocument> pipelineDocuments;

    Map<String, ArrayList<PipelineDocument>> pipelineDocumentsMap;

    Map<String, List<Triple>> docIdToTriplesMap;

    Map<String, List<Triple>> sentenceToTriplesMap;

    Map<Integer, PipelineDocument> documentDirectory;

    Set<Triple> tripleSet;

    List<UserUtterance> userUtterances;

    private String mainTag;

    private Set<String> currentTags;

    private Set<String> docTags;

    public DataManager(){

        pipelineDocuments = new ArrayList<>();
        pipelineDocumentsMap = new HashMap<>();
        documentDirectory = new HashMap<>();
        tripleSet = new HashSet<>();

        userUtterances = new ArrayList<>();
        currentTags = new HashSet<>();
        docTags = new HashSet<>();
    }

    public boolean storeTriple(Triple triple){
        return true;
    }

    public List<PipelineDocument> getPipelineDocuments(){
        return pipelineDocuments;
    }

    public void addPipelineDocument(PipelineDocument doc){
        pipelineDocumentsMap.putIfAbsent(doc.getDocId(), new ArrayList<PipelineDocument>());
        pipelineDocumentsMap.get(doc.getDocId()).add(doc);
        documentDirectory.put(doc.getResultNumber(), doc);
    }

    public PipelineDocument getPipelineDocumentByResultNumber(int resultNumber){
        return documentDirectory.get(resultNumber);
    }

    public List<UserUtterance> getUserUtterances() {
        return userUtterances;
    }

    public void setUserUtterances(List<UserUtterance> userUtterances) {
        this.userUtterances = userUtterances;
    }

    public void addUserUtterance(String utteranceText) {
        userUtterances.add(new UserUtterance(utteranceText));
        updateTags();
    }

    public UserUtterance getUtteranceByIndex(int index){
        return userUtterances.get(index);
    }

    private void updateTags(){
        currentTags.clear();
        for(Concept concept: getUtteranceByIndex(userUtterances.size() - 1).getConcepts()){
            currentTags.add(concept.getConcept());
        }
    }

    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public Set<String> getDocTags() {
        return docTags;
    }

    public void setDocTags(Set<String> docTags) {
        this.docTags = docTags;
    }


}
