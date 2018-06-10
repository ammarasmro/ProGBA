package interfaces;

import data.DataManager;
import data_structures.*;

import java.util.List;
import java.util.Set;

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

    public static void setMainTag(String tag){ dataManager.setMainTag(tag); }

    public static String getMainTag(){ return dataManager.getMainTag(); }

    public static void setDocTags(Set<String> tags){ dataManager.setDocTags(tags); }

    public static Set<String> getDocTags(){ return dataManager.getDocTags(); }

    public static void setUserTags(Set<String> tags){ dataManager.setUserTags(tags); }

    public static Set<String> getUserTags(){ return dataManager.getUserTags(); }
}
