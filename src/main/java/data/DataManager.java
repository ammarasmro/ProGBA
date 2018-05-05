package data;

import algorithms.Pipeline;
import data_structures.*;

import java.util.*;

public class DataManager {

    List<PipelineDocument> pipelineDocuments;

    Map<String, ArrayList<PipelineDocument>> pipelineDocumentsMap;

    Map<String, List<Triple>> docIdToTriplesMap;

    Map<String, List<Triple>> sentenceToTriplesMap;

    Map<Integer, PipelineDocument> documentDirectory;

    Set<Triple> tripleSet;




    public DataManager(){

        pipelineDocuments = new ArrayList<>();
        pipelineDocumentsMap = new HashMap<>();
        documentDirectory = new HashMap<>();
        tripleSet = new HashSet<>();
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


}
