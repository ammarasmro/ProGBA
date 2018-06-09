package interfaces;

import clients.DrQAClient;
import data_structures.DrQAResponseDocument;
import data_structures.WikipediaDocument;
import utils.DataStructureConverter;

public class DrQAInterface {

    public static DrQAResponseDocument[] queryDocuments(String query){

        return DataStructureConverter.jsonStringToDrQADocsArray(DrQAClient.queryTextToStringJsonResponse(query));
    }

    public static WikipediaDocument[] retrieveWikiDocs(String query){
        String jsonResponse = DrQAClient.queryTextToDocumentStringJsonResponse(query, 5);
        return DataStructureConverter.jsonStringToWikipediaDocumentsArray(jsonResponse);
    }

    public static void main(String[] args) {
//        for(DrQAResponseDocument doc: queryDocuments("Automotive%20Design")){
//            System.out.println(doc);
//        }

        for(WikipediaDocument doc: retrieveWikiDocs("Automotive%20Design")){
            System.out.println(doc);
        }
    }
}
