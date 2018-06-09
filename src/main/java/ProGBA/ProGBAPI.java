package ProGBA;

import algorithms.Pipeline;
import interfaces.DataManagerInterface;

import static spark.Spark.*;

public class ProGBAPI {

    public static void main(String[] args) {

        Pipeline pipeline = new Pipeline();

        get("/status", (req, res) -> {
            return "Working!";
        });

        get("/start-servers", (req, res) -> {
            return "Hello from ProGBA!";
        });

        get("/stop-servers", (req, res) -> {
            return "Goodbye!";
        });

        get("/query/:query", (req, res) -> {
            pipeline.queryDrQA(req.params("query"));
            return DataManagerInterface.getPipelineDocuments();
        });

        get("/choose-doc/:doc-id", (req, res) -> {
            pipeline.queryDrQA(req.params("doc-id"));
            return DataManagerInterface.getPipelineDocuments();
        });

        get("/user-utterance/:utter", (req, res) -> {
            return req.params("utter");
        });

        get("/question/:question", (req, res) -> {
            // TODO: Use DrQA Reader to get short answer
            return req.params("question");
        });
    }

}
