package clients;

import utils.HttpUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class DrQAClient {

    private boolean status = false;

    public DrQAClient(){

        // TODO: Change to make a real check or add instantiation first
        status = true;
    }


    public String queryTextToStringJsonResponse(String query) {
        String result = "";
        try {
            URL hostUrl = new URL("http://localhost:5000/question/");
            URL queryUrl = new URL(hostUrl, query);
            URLConnection connection = queryUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            result = HttpUtils.bufferedReaderToString(in);
            in.close();
            // This is a temporary fix for not checking the status from DrQA
            status = true;
        } catch (Exception e){
            e.printStackTrace();
            // This is a temporary fix for not checking the status from DrQA
            status = false;
        }
        return result;
    }

    public String queryTextToDocumentStringJsonResponse(String query, int numberOfDocuments) {
        String result = "";
        try {
            URL hostUrl = new URL("http://localhost:5000/docs/");
            URL queryUrl = new URL(hostUrl, String.format("%s/%d", query, numberOfDocuments));
            URLConnection connection = queryUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            result = HttpUtils.bufferedReaderToString(in);
            in.close();
            status = true;
        } catch (Exception e){
            e.printStackTrace();
            status = false;
        }
        return result;
    }

    /**
     * TODO: add an HTTP request to check for the status
     * @return status of the DrQA server
     */
    public boolean getStatus() {
        return status;
    }

    public static void main(String[] args) throws Exception {

//        System.out.println(queryTextToStringJsonResponse("Automotive%design"));
//        System.out.println(queryTextToDocumentStringJsonResponse("Automotive%design", 5));
    }
}
