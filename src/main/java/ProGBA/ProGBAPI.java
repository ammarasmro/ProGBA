package ProGBA;

import static spark.Spark.*;

public class ProGBAPI {

    public static void main(String[] args) {
        get("/hello", (req, res) -> {
            return "Hello from ProGBA!";
        });

        get("/query/:query", (req, res) -> {
            return req.params("query");
        });

        get("/user-utterance/:utter", (req, res) -> {
            return req.params("utter");
        });
    }

}
