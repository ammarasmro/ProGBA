package algorithms;

import data_structures.Concept;
import data_structures.DrQAResponseDocument;
import data_structures.PipelineDocument;
import data_structures.WikipediaDocument;
import interfaces.*;
import utils.DataStructureConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Pipeline {

    public void askDrQA(String query){
        // TODO: change functionality to answer questions then process if answer was good
        for(DrQAResponseDocument drQADoc: DrQAInterface.queryDocuments(query)){
            DataManagerInterface.addPipelineDocument(initializePipelineDocumentFromDrQAResponse(drQADoc));
        }
    }

    public void queryDrQA(String query){
        updateTags(DataManagerInterface.getUserTags(), NLUInterface.getConceptsOf(query));
        DataManagerInterface.getPipelineDocuments().clear();
        int counter = 1;
        for(WikipediaDocument wikiDoc: DrQAInterface.retrieveWikiDocs(query)){
            PipelineDocument document = DataStructureConverter.wikiDocumentToPipelineDocument(wikiDoc, counter);
            String cleanContent = document.getContext();
            document.setConcepts(NLUInterface.getConceptsOf(cleanContent));
            DataManagerInterface.addPipelineDocument(document);
            counter++;
        }
    }

    private void updateTags(Set<String> tags, List<Concept> concepts){
        tags.clear();
        for(Concept concept: concepts){
            tags.add(concept.getConcept());
        }
    }

    public PipelineDocument getPipelineDocument(int docNumber){
        return DataManagerInterface.getPipelineDocumentByResultNumber(docNumber);
    }

    public void processDocumentsWithNLU(PipelineDocument pipelineDocument){
//        pipelineDocument.setSemanticRoles(NLUInterface.getSemanticRolesOf(pipelineDocument.getContext()));
        pipelineDocument.setCategories(NLUInterface.getCategoryOf(pipelineDocument.getContext()));
        pipelineDocument.setKeywords(NLUInterface.getKeywordsOf(pipelineDocument.getContext()));
//        pipelineDocument.setConcepts(NLUInterface.getConceptsOf(pipelineDocument.getContext()));
    }

//    public void putDocumentThroughPipeline(PipelineDocument pipelineDocument){
//        SentenceProcessingPipeline sentencePipeline;
//        for(SemanticRole semanticRole: pipelineDocument.getSemanticRoles()){
//            sentencePipeline = new SentenceProcessingPipeline(semanticRole);
//            sentencePipeline.progressThroughPipeline();
//            pipelineDocument.getTriples().addAll(sentencePipeline.getTriples());
//        }
//    }

    public void putDocumentThroughPipeline(PipelineDocument pipelineDocument){
        processDocumentsWithNLU(pipelineDocument);
        updateTags(DataManagerInterface.getDocTags(), pipelineDocument.getConcepts());
        String[] sentences = getCleanSentences(pipelineDocument);
        SentenceProcessingPipeline sentencePipeline;
        for(String sentence: sentences){
            sentencePipeline = new SentenceProcessingPipeline(sentence);
            sentencePipeline.progressThroughPipeline();
            pipelineDocument.getTriples().addAll(sentencePipeline.getTriples());
        }
    }

    public String[] getCleanSentences(PipelineDocument document){
        String content = document.getContext();
        return OpenNLPInterface.getSentencesOfString(content);
    }

    public static PipelineDocument initializePipelineDocumentFromDrQAResponse(DrQAResponseDocument drQAResponseDocument){
        return new PipelineDocument(drQAResponseDocument);
    }

    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline();
//        pipeline.queryDrQA("Automotive%20design");
//        PipelineDocument pipelineDocument = pipeline.getPipelineDocument(1);
//        pipeline.processDocumentsWithNLU(pipelineDocument);
//        pipeline.putDocumentThroughPipeline(pipelineDocument);
//        System.out.flush();
//        System.out.println(Character.toString((char)0x00a0));
        System.out.println(Arrays.toString(pipeline.getCleanSentences(null)));
//        String a = "abcd\u2013bcds\"";
//        String b = a;
//        System.out.println(a);


    }


}
