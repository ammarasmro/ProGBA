package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data_structures.DrQAResponseDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class HttpUtils {

    public static String bufferedReaderToString(BufferedReader in) throws IOException{
        String inputLine;
        StringBuilder sb = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);

        return sb.toString();
    }

    public static void main(String[] args) {
    }
}
