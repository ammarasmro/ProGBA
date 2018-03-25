package clients;

import utils.HttpUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class DrQAClient {


    public static String queryTextToStringJsonResponse(String query) {
        String result = "";
        try {
            URL hostUrl = new URL("http://localhost:5000/question/");
            URL queryUrl = new URL(hostUrl, query);
            URLConnection connection = queryUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            result = HttpUtils.bufferedReaderToString(in);
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) throws Exception {

        queryTextToStringJsonResponse("Automotive%design");
    }
}
