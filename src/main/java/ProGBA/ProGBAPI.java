package ProGBA;

import static spark.Spark.*;

public class ProGBAPI {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }

}
