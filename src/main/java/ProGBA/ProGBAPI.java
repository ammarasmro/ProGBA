package ProGBA;

import algorithms.Pipeline;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import conversation_datastructures.UserUtterance;
import data_structures.PipelineDocument;
import interfaces.CoreNLPInterface;
import interfaces.DataManagerInterface;
import interfaces.DrQAInterface;
import interfaces.GraphDBInterface;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class ProGBAPI {

    public static void main(String[] args) {

        Pipeline pipeline = new Pipeline();

        /**
         * Check the status of the systems
         */
        get("/status", (req, res) -> {
            // TODO: Add a more comprehensive response like a table
            if(!CoreNLPInterface.getStatusOfOpenIE()) { return "OpenIE has not started yet!\n"; }
            if(!CoreNLPInterface.getStatusOfCoref()) { return "Coref has not started yet!\n"; }
            if(!DrQAInterface.getStatusOfDrQA()) { return "DrQA has not started yet!\n"; }
            res.header("Access-Control-Allow-Origin", "*");
//            res.body("All systems are working!\n");
            return "All systems are working!\n";
        });

        /**
         * Start the servers for the CoreNLP and DrQA servers
         */
        get("/start-servers", (req, res) -> {
            // TODO: Add server starters for the CoreNLP servers
            // TODO: Add server starter for the DrQA server
            res.header("Access-Control-Allow-Origin", "*");
            return "Hello from ProGBA!";
        });

        /**
         * Stop the servers for the CoreNLP and DrQA servers
         */
        get("/stop-servers", (req, res) -> {
            // TODO: Add server stoppers for the CoreNLP servers
            // TODO: Add server stopper for the DrQA server
            res.header("Access-Control-Allow-Origin", "*");
            return "Goodbye!";
        });

        // TODO: add project starter routes that start the whole graph with project node and aspect nodes

        /**
         * Start a project with an aspect
         */
        get("/start-project/:project/:aspect", (req, res) -> {
            GraphDBInterface.addProjectAspectTriple(req.params("project"), "hasAspect",
                    req.params("aspect"));
            DataManagerInterface.setProjectTitle(req.params("project"));
            DataManagerInterface.setCurrentAspect(req.params("aspect"));
            res.header("Access-Control-Allow-Origin", "*");
            return String.format("Started Project %s with Aspect %s\n", req.params("project"),
                    req.params("aspect"));
        });

        /**
         * Issue a query to DrQA and store the resulting documents in the DataManager
         */
        get("/query/:query", (req, res) -> {
            pipeline.queryDrQA(req.params("query"));
            System.out.println("done!");
//            System.out.println(DataManagerInterface.getPipelineDocuments());
            res.header("Access-Control-Allow-Origin", "*");
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(DataManagerInterface.getPipelineDocuments());
            JsonElement jElement = gson.toJsonTree(new JsonObject());
            jElement.getAsJsonObject().addProperty("conversation-id", DataManagerInterface.getConversationTag());
            jElement.getAsJsonObject().add("results", jsonElement);
            return gson.toJson(jElement);
//            return DataManagerInterface.getPipelineDocumentByResultNumber(1).getContext().substring(0, 1000);
//            return DataManagerInterface.getPipelineDocuments();
        });

        /**
         * Choose the document to further process through the document and return the triples
         */
        get("/choose-doc/:doc-number", (req, res) -> {
            // TODO: this is taking too long, I need an AJAX request instead of http
            PipelineDocument document = pipeline.getPipelineDocument(Integer.valueOf(req.params("doc-number")));
            pipeline.putDocumentThroughPipeline(document);
            System.out.println("done!");
            res.header("Access-Control-Allow-Origin", "*");
            return document.getTriples();
        });

        /**
         * Retrieve a list of conversations
         */
        get("/conversation/list", (req, res) -> {
            Gson gson = new Gson();
            String json = gson.toJson(DataManagerInterface.getConversation());
//            Type uttersType = new TypeToken<Collection<UserUtterance>>() {}.getType();
//            Collection<UserUtterance> utters = gson.fromJson(json, uttersType);
            res.header("Access-Control-Allow-Origin", "*");
            return json;
        });


        /**
         * Choose document by title
         * TODO: Implement
         */
        get("/choose-doc-title/:doc-title", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            return req.params("doc-title");
        });

        /**
         * Search document by title
         * TODO: Implement
         */
        get("/search-doc/:query", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            return req.params("query");
        });

        /**
         * Get user utterance to support conversation
         */
        get("/user-utterance/:utter", (req, res) -> {
            // TODO: add converation handling
            res.header("Access-Control-Allow-Origin", "*");
            return req.params("utter");
        });

        /**
         * Answer simple questions by the user
         */
        get("/question/:question", (req, res) -> {
            // TODO: Use DrQA Reader to get short answers
//            pipeline.askDrQA(req.params("question"));
            res.header("Access-Control-Allow-Origin", "*");
            return req.params("question");
        });
    }

}
