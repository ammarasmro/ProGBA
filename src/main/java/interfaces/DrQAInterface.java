package interfaces;

import clients.DrQAClient;
import data_structures.DrQAResponseDocument;
import data_structures.WikipediaDocument;
import utils.DataStructureConverter;

public class DrQAInterface {

    private static DrQAClient client = new DrQAClient();

    public static DrQAResponseDocument[] queryDocuments(String query){

        return DataStructureConverter.jsonStringToDrQADocsArray(client.queryTextToStringJsonResponse(query));
    }

    public static WikipediaDocument[] retrieveWikiDocs(String query){
        String jsonResponse = client.queryTextToDocumentStringJsonResponse(query, 5);
        return DataStructureConverter.jsonStringToWikipediaDocumentsArray(jsonResponse);
    }

    public static boolean getStatusOfDrQA(){
        return client.getStatus();
    }

    public static void main(String[] args) {
//        for(DrQAResponseDocument doc: queryDocuments("Automotive%20Design")){
//            System.out.println(doc);
//        }

        for(WikipediaDocument doc: retrieveWikiDocs("Automotive%20Design")){
            System.out.println(doc.getContent());
            for (String section: doc.getContent().split("\n+"))
                System.out.println(section);
        }
    }
}
