package clients;


import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import data_structures.Category;
import data_structures.Keyword;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NaturalLanguageUnderstandingClient {

    private NaturalLanguageUnderstanding service;

    private static String NLU_USERNAME;
    private static String NLU_PASSWORD;

    private Features features;
    private AnalyzeOptions parameters;
    private AnalysisResults response;


    public NaturalLanguageUnderstandingClient() {
        getInfoFromConfigFile();
        service = new NaturalLanguageUnderstanding(
                "2017-02-27",
                NLU_USERNAME,
                NLU_PASSWORD
        );
    }

    private static void getInfoFromConfigFile(){
        Properties prop = new Properties();
        try{
            prop.load(new FileInputStream("src/main/resources/ProGBA.config"));
            NLU_USERNAME = prop.getProperty("NLU_USERNAME");
            NLU_PASSWORD = prop.getProperty("NLU_PASSWORD");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void initializeForKeywordsExtraction() {
        KeywordsOptions keywords;
        keywords= new KeywordsOptions.Builder()
                .limit(3)
                .build();
        features = new Features.Builder()
                .keywords(keywords)
                .build();
    }

    private void initializeForCategoriesExtraction() {
        initializeForKeywordsExtraction();
        CategoriesOptions categories;
        categories = new CategoriesOptions();
        features = new Features.Builder()
                .categories(categories)
                .build();
    }

    private List<KeywordsResult> getKeywordsResults(String sentence) {
        parameters = new AnalyzeOptions.Builder()
                .text(sentence)
                .features(features)
                .language("en")
                .build();
        response = service
                .analyze(parameters)
                .execute();
        return response.getKeywords();
    }

    private List<CategoriesResult> getCategoriesResults(String sentence) {
        initializeForCategoriesExtraction();
        parameters = new AnalyzeOptions.Builder()
                .text(sentence)
                .features(features)
                .language("en")
                .build();
        response = service
                .analyze(parameters)
                .execute();
        return response.getCategories();
    }

    public List<Keyword> getKeywordsOfSentence(String sentence){
        List<Keyword> keywords = new ArrayList<Keyword>();
        for(KeywordsResult keywordsResult: getKeywordsResults(sentence)) {
            keywords.add(new Keyword(keywordsResult.getText()));
        }
        return keywords;
    }

    public List<Category> getCategoryOfSentence(String sentence){
        List<Category> categories = new ArrayList<Category>();
        for(CategoriesResult category: getCategoriesResults(sentence) ) {
            categories.add(new Category(category.getLabel()));
        }
        return categories;
    }

    public static void main(String[] args) {

        NaturalLanguageUnderstandingClient testClient = new NaturalLanguageUnderstandingClient();
        System.out.println(testClient.getKeywordsOfSentence("Ammar is back"));
        System.out.println(testClient.getCategoryOfSentence("functional design"));
    }
}
