package interfaces;

import data.DataManager;
import data_structures.*;

import java.util.List;

public class DataManagerInterface {

    private static DataManager dataManager = new DataManager();

    public static void storeTriple(Triple triple){
        dataManager.storeTriple(triple);
    }

    public static void addPipelineDocument(PipelineDocument doc){
        dataManager.addPipelineDocument(doc);
    }

    public static List<PipelineDocument> getPipelineDocuments(){
        return dataManager.getPipelineDocuments();
    }

    public static PipelineDocument getPipelineDocumentByResultNumber(int resultNumber){
        return dataManager.getPipelineDocumentByResultNumber(resultNumber);
    }
}
