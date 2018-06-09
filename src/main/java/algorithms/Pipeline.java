package algorithms;

import clients.NaturalLanguageUnderstandingClient;
import data.DataManager;
import data_structures.DrQAResponseDocument;
import data_structures.PipelineDocument;
import data_structures.SemanticRole;
import data_structures.WikipediaDocument;
import interfaces.DataManagerInterface;
import interfaces.DrQAInterface;
import interfaces.NLUInterface;
import utils.DataStructureConverter;

public class Pipeline {

    public void askDrQA(String query){
        // TODO: change functionality to answer questions
        for(DrQAResponseDocument drQADoc: DrQAInterface.queryDocuments(query)){
            DataManagerInterface.addPipelineDocument(initializePipelineDocumentFromDrQAResponse(drQADoc));
        }
    }

    public void queryDrQA(String query){
        DataManagerInterface.getPipelineDocuments().clear();
        int counter = 1;
        for(WikipediaDocument wikiDoc: DrQAInterface.retrieveWikiDocs(query)){
            PipelineDocument document = DataStructureConverter.wikiDocumentToPipelineDocument(wikiDoc, counter);
            String cleanContent = document.getContext();
            cleanContent.replaceAll("\n", " ");
            document.setConcepts(NLUInterface.getConceptsOf(cleanContent));
            DataManagerInterface.addPipelineDocument(document);
            counter++;
        }
    }

    public PipelineDocument getPipelineDocument(int docNumber){
        return DataManagerInterface.getPipelineDocumentByResultNumber(docNumber);
    }

    public void processDocumentsWithNLU(PipelineDocument pipelineDocument){
        pipelineDocument.setSemanticRoles(NLUInterface.getSemanticRolesOf(pipelineDocument.getContext()));
        pipelineDocument.setCategories(NLUInterface.getCategoryOf(pipelineDocument.getContext()));
        pipelineDocument.setKeywords(NLUInterface.getKeywordsOf(pipelineDocument.getContext()));
//        pipelineDocument.setConcepts(NLUInterface.getConceptsOf(pipelineDocument.getContext()));
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
        System.out.flush();
    }


}
