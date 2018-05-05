package algorithms;

import clients.NaturalLanguageUnderstandingClient;
import data.DataManager;
import data_structures.DrQAResponseDocument;
import data_structures.PipelineDocument;
import data_structures.SemanticRole;
import interfaces.DataManagerInterface;
import interfaces.DrQAInterface;
import interfaces.NLUInterface;

public class Pipeline {

    public void queryDrQA(String query){
        for(DrQAResponseDocument drQADoc: DrQAInterface.queryDocuments(query)){
            DataManagerInterface.addPipelineDocument(initializePipelineDocumentFromDrQAResponse(drQADoc));
        }
    }

    public PipelineDocument getPipelineDocument(int docNumber){
        return DataManagerInterface.getPipelineDocumentByResultNumber(docNumber);
    }

    public void processDocumentsWithNLU(PipelineDocument pipelineDocument){
        pipelineDocument.setSemanticRoles(NLUInterface.getSemanticRolesOf(pipelineDocument.getContext()));
        pipelineDocument.setCategories(NLUInterface.getCategoryOf(pipelineDocument.getContext()));
        pipelineDocument.setKeywords(NLUInterface.getKeywordsOf(pipelineDocument.getContext()));
    }

    public void putDocumentThroughPipeline(PipelineDocument pipelineDocument){
        SentenceProcessingPipeline sentencePipeline;
        for(SemanticRole semanticRole: pipelineDocument.getSemanticRoles()){
            sentencePipeline = new SentenceProcessingPipeline(semanticRole);
            sentencePipeline.progressThroughPipeline();
            pipelineDocument.getTriples().addAll(sentencePipeline.getTriples());
        }
    }

    public static PipelineDocument initializePipelineDocumentFromDrQAResponse(DrQAResponseDocument drQAResponseDocument){
        return new PipelineDocument(drQAResponseDocument);
    }

    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline();
        pipeline.queryDrQA("Automotive%20design");
        PipelineDocument pipelineDocument = pipeline.getPipelineDocument(1);
        pipeline.processDocumentsWithNLU(pipelineDocument);
        pipeline.putDocumentThroughPipeline(pipelineDocument);
//        pipeline.processDocumentsWithNLU(null);
        System.out.flush();
    }


}
