package interfaces;

import clients.DrQAClient;
import data_structures.DrQAResponseDocument;
import utils.DataStructureConverter;

public class DrQAInterface {

    public static DrQAResponseDocument[] queryDocuments(String query){

        return DataStructureConverter.jsonStringToDrQADocsArray(DrQAClient.queryTextToStringJsonResponse(query));
    }

    public static void main(String[] args) {
        for(DrQAResponseDocument doc: queryDocuments("Automotive%20Design")){
            System.out.println(doc);
        }
    }
}
