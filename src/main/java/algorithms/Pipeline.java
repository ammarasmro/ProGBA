package algorithms;

import conversation_datastructures.UserUtterance;
import data_structures.*;
import interfaces.*;
import utils.DataStructureConverter;

import java.util.*;


public class Pipeline {

    private GraphBuilder graphBuilder;

    public Pipeline(){
        graphBuilder = new GraphBuilder();
    }

    public void askDrQA(String query){
        // TODO: change functionality to answer questions then process if answer was good
        for(DrQAResponseDocument drQADoc: DrQAInterface.queryDocuments(query)){
            DataManagerInterface.addPipelineDocument(initializePipelineDocumentFromDrQAResponse(drQADoc));
        }
    }

    public void queryDrQA(String query){
        DataManagerInterface.startNewConversation();
        query = query.replaceAll("%20", " ");
        DataManagerInterface.addUserUtterance(new UserUtterance(DataManagerInterface.getConversationTag(), query));
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

    private void updateTags(Collection<String> tags, Collection<Concept> concepts){
        tags.clear();
        for(Concept concept: concepts){
            tags.add(concept.getConcept());
        }
        System.out.println(tags);
        System.out.println(DataManagerInterface.getDocTags());
        System.out.println(DataManagerInterface.getUserTags());
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
        List<String> sentences = getCleanSentences(pipelineDocument);
        SentenceProcessingPipeline sentencePipeline;
        Cleanser cleanser = new Cleanser(pipelineDocument);
        // TODO: change this test sentences back
        List<String> testSentences = sentences.subList(0, 2);
        for(String sentence: testSentences){
            sentencePipeline = new SentenceProcessingPipeline(sentence);
            sentencePipeline.progressThroughPipeline();
            Set<Triple> sentenceTriples = sentencePipeline.getTriples();
            cleanser.cleanseCollection(pipelineDocument, sentenceTriples);
            persistTriplesToGraph(cleanser.getNormalTriples(), cleanser.getWordToWordTriples());
            updateTagsWithTriples(pipelineDocument.getConcepts(), sentenceTriples);
//            pipelineDocument.getTriples().addAll(sentencePipeline.getTriples());
        }
    }

    private void persistTriplesToGraph(Collection<Triple> normalTriples, Collection<Triple> wordToWordTriples){
        graphBuilder.updateTags();
        for(Triple triple: normalTriples){
            graphBuilder.persistTriple(triple);
        }
        for(Triple triple: wordToWordTriples){
            graphBuilder.persistWordToWordTriple(triple);
        }
    }


//    private void persistTriplesToGraph(Collection<Triple> triples){
//        for(Triple triple: triples){
//            GraphDBInterface.addSubjectObjectTriple(triple.getSubject().getSentence(), triple.getVerb(),
//                    triple.getObject().getSentence());
//            for(Keyword keyword: triple.getSubject().getKeywords()){
//                GraphDBInterface.addKeywordToSubjectLink(keyword.getKeyword(), "linksTo",
//                        triple.getSubject().getSentence());
////                for(Category category: keyword.getCategories()){
////                    GraphDBInterface.addKeywordToCategoryLink();
////                }
//            }
//            for(Keyword keyword: triple.getObject().getKeywords()){
//                GraphDBInterface.addObjectToKeywordLink(triple.getObject().getSentence(), "hasDefinition",
//                        keyword.getKeyword());
//            }
//            for(String tag: DataManagerInterface.getDocTags()) {
//                GraphDBInterface.addNodeToTagLink(triple.getSubject().getSentence(), "subject",
//                        "taggedAs", tag, "DocTag");
//                GraphDBInterface.addNodeToTagLink(triple.getObject().getSentence(), "object",
//                        "taggedAs", tag, "DocTag");
//            }
//            for(String tag: DataManagerInterface.getUserTags()) {
//                GraphDBInterface.addNodeToTagLink(triple.getSubject().getSentence(), "subject",
//                        "taggedAs", tag, "UserTag");
//                GraphDBInterface.addNodeToTagLink(triple.getObject().getSentence(), "object",
//                        "taggedAs", tag, "UserTag");
//            }
//        }
//
//    }


    /**
     * Deprecated
     * @param concepts
     * @param sentenceTriples
     */
    private void updateTagsWithTriples(Collection<Concept> concepts, Set<Triple> sentenceTriples) {
        Set<Integer> ids = new HashSet<>();
        for(Triple triple: sentenceTriples){
            ids.add(triple.getSubject().getId());
            ids.add(triple.getObject().getId());
        }
        DataManagerInterface.updateDocumentTags(concepts, ids);
        // TODO: GET AN ID GENERATING SERVICE TO GENERATE UNIQUE IDS EVERY TIME
    }

    public List<String> getCleanSentences(PipelineDocument document){
        String content = document.getContext();
        List<String> sentences = new ArrayList<>();
        for(String section: document.getContext().split("\n+"))
            sentences.addAll(Arrays.asList(OpenNLPInterface.getSentencesOfString(section)));
        return sentences;
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
//        System.out.println(Arrays.toString(pipeline.getCleanSentences(null)));
//        String a = "abcd\u2013bcds\"";
//        String b = a;
//        System.out.println(a);


    }


}
