package clients;

import utils.HttpUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Properties;

public class DrQAClient {

    private boolean status = false;
    private String CYBERA_IP;

    public DrQAClient(){

        // TODO: Change to make a real check or add instantiation first
        status = true;

        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream("src/main/resources/ProGBA.config"));
            CYBERA_IP = prop.getProperty("CYBERA_IP");
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    // TODO: Refactor to use the http utility for REST queries

    public String queryTextToStringJsonResponse(String query) {
        String result = "";
        try {
            // TODO: Change this IP to external IP of cloud instance
            URL hostUrl = new URL(CYBERA_IP + ":5000/question/");
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
            // TODO: Change this IP to external IP of cloud instance
            URL hostUrl = new URL(CYBERA_IP + ":5000/docs/");
            URL queryUrl = new URL(hostUrl, String.format("%s/%d", query.replaceAll(" ", "%20"), numberOfDocuments));
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
