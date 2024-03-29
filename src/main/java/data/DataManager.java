package data;

import algorithms.Pipeline;
import conversation_datastructures.UserUtterance;
import data_structures.*;

import java.util.*;

public class DataManager {

    private String projectTitle;
    private String aspect;

    List<PipelineDocument> pipelineDocuments;

    Map<String, ArrayList<PipelineDocument>> pipelineDocumentsMap;

    Map<String, List<Triple>> docIdToTriplesMap;

    Map<String, List<Triple>> sentenceToTriplesMap;

    Map<Integer, PipelineDocument> documentDirectory;

    Set<Triple> tripleSet;

    private String mainTag;

    private Set<String> currentTags;

    private Set<String> docTags;

    private Set<String> userTags;

    private Map<String, Set<Integer>> queryTagsToNodesMap;
    private Map<String, Set<Integer>> documentTagsToNodesMap;

    // Conversation variables
    private int conversationCounter;

    private Deque<UserUtterance> conversationStack;

    public DataManager(){

        pipelineDocuments = new ArrayList<>();
        pipelineDocumentsMap = new HashMap<>();
        documentDirectory = new HashMap<>();
        tripleSet = new HashSet<>();

        currentTags = new HashSet<>();
        docTags = new HashSet<>();
        userTags = new HashSet<>();

        queryTagsToNodesMap = new HashMap<>();
        documentTagsToNodesMap = new HashMap<>();

        conversationCounter = 0;
        conversationStack = new ArrayDeque<UserUtterance>();
    }

    public boolean storeTriple(Triple triple){
        return true;
    }

    public Collection<PipelineDocument> getPipelineDocuments(){
        return documentDirectory.values();
    }

    public void addPipelineDocument(PipelineDocument doc){
        pipelineDocumentsMap.putIfAbsent(doc.getDocId(), new ArrayList<PipelineDocument>());
        pipelineDocumentsMap.get(doc.getDocId()).add(doc);
        documentDirectory.put(doc.getResultNumber(), doc);
    }

    public PipelineDocument getPipelineDocumentByResultNumber(int resultNumber){
        return documentDirectory.get(resultNumber);
    }


    public String getMainTag() {
        return mainTag;
    }

    public void setMainTag(String mainTag) {
        this.mainTag = mainTag;
    }

    public Set<String> getUserTags() {
        return userTags;
    }

    public void setUserTags(Set<String> userTags) {
        this.userTags = userTags;
    }

    public Set<String> getDocTags() {
        return docTags;
    }

    public void setDocTags(Set<String> docTags) {
        this.docTags = docTags;
    }

    public Map<String, Set<Integer>> getQueryTagsToNodesMap() {
        return queryTagsToNodesMap;
    }

    public void setQueryTagsToNodesMap(Map<String, Set<Integer>> queryTagsToNodesMap) {
        this.queryTagsToNodesMap = queryTagsToNodesMap;
    }

    public Map<String, Set<Integer>> getDocumentTagsToNodesMap() {
        return documentTagsToNodesMap;
    }

    public void setDocumentTagsToNodesMap(Map<String, Set<Integer>> documentTagsToNodesMap) {
        this.documentTagsToNodesMap = documentTagsToNodesMap;
    }

    public void updateQueryTags(Collection<Concept> concepts, Collection<Integer> ids){
        for(Concept concept: concepts){
            String conceptText = concept.getConcept();
            queryTagsToNodesMap.getOrDefault(conceptText, new HashSet<Integer>()).addAll(ids);
        }
    }

    public void updateDocumentTags(Collection<Concept> concepts, Collection<Integer> ids){
        for(Concept concept: concepts){
            String conceptText = concept.getConcept();
            documentTagsToNodesMap.getOrDefault(conceptText, new HashSet<Integer>()).addAll(ids);
        }
    }


    public void setCurrentAspect(String aspect) {
        this.aspect = aspect;
    }

    public void setProjectTitle(String project) {
        this.projectTitle = project;
    }

    public String getAspect() {
        return this.aspect;
    }

    public int getConversationCounter() {
        return conversationCounter;
    }

    public void incConversationCounter() {
        this.conversationCounter++;
    }

    public Deque<UserUtterance> getConversationStack() { return conversationStack; }

    public void setConversationStack(Deque<UserUtterance> conversationStack) { this.conversationStack = conversationStack; }
}
