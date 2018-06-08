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

    public static String queryTextToDocumentStringJsonResponse(String query, int numberOfDocuments) {
        String result = "";
        try {
            URL hostUrl = new URL("http://localhost:5000/docs/");
            URL queryUrl = new URL(hostUrl, String.format("%s/%d", query, numberOfDocuments));
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

//        System.out.println(queryTextToStringJsonResponse("Automotive%design"));
        System.out.println(queryTextToDocumentStringJsonResponse("Automotive%design", 5));
    }
}
