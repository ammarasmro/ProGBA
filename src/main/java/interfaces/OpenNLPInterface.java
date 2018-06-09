package interfaces;

import clients.OpenNLPClient;

public class OpenNLPInterface {

    private static OpenNLPClient client = new OpenNLPClient();

    public static String[] getSentencesOfString(String text){
        return client.StringToSentences(text);
    }
}
