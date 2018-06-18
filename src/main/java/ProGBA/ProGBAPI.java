package ProGBA;

import algorithms.Pipeline;
import data_structures.PipelineDocument;
import interfaces.CoreNLPInterface;
import interfaces.DataManagerInterface;
import interfaces.DrQAInterface;

import static spark.Spark.*;

public class ProGBAPI {

    public static void main(String[] args) {

        Pipeline pipeline = new Pipeline();

        /**
         * Check the status of the systems
         */
        get("/status", (req, res) -> {
            if(!CoreNLPInterface.getStatusOfOpenIE()) { return "OpenIE has not started yet!"; }
            if(!CoreNLPInterface.getStatusOfCoref()) { return "Coref has not started yet!"; }
            if(!DrQAInterface.getStatusOfDrQA()) { return "DrQA has not started yet!"; }
            return "All systems are working!";
        });

        /**
         * Start the servers for the CoreNLP and DrQA servers
         */
        get("/start-servers", (req, res) -> {
            // TODO: Add server starters for the CoreNLP servers
            // TODO: Add server starter for the DrQA server
            return "Hello from ProGBA!";
        });

        /**
         * Stop the servers for the CoreNLP and DrQA servers
         */
        get("/stop-servers", (req, res) -> {
            // TODO: Add server stoppers for the CoreNLP servers
            // TODO: Add server stopper for the DrQA server
            return "Goodbye!";
        });

        /**
         * Issue a query to DrQA and store the resulting documents in the DataManager
         */
        get("/query/:query", (req, res) -> {
            pipeline.queryDrQA(req.params("query"));
            return DataManagerInterface.getPipelineDocuments();
        });

        /**
         * Choose the document to further process through the document and return the triples
         */
        get("/choose-doc/:doc-number", (req, res) -> {
            PipelineDocument document = pipeline.getPipelineDocument(Integer.valueOf(req.params("doc-number")));
            pipeline.putDocumentThroughPipeline(document);
            return document.getTriples();
        });

        /**
         * Get user utterance to support conversation
         */
        get("/user-utterance/:utter", (req, res) -> {
            // TODO: add converation handling
            return req.params("utter");
        });

        /**
         * Answer simple questions by the user
         */
        get("/question/:question", (req, res) -> {
            // TODO: Use DrQA Reader to get short answers
//            pipeline.askDrQA(req.params("question"));
            return req.params("question");
        });
    }

}
