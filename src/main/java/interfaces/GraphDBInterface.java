package interfaces;


import clients.Neo4jClient;

public class GraphDBInterface {

    public static void addSubjectObjectTriple(String subject, String verb, String object){
        Neo4jClient.addTriple(subject, verb, object,
                "subject", "verb", "object",
                "subject Text", "object Text");
    }

    public static void addProjectAspectTriple(String project, String link, String aspect){
        Neo4jClient.addTriple(project, link, aspect,
                "project", "has aspect", "aspect",
                "project name", "aspect Text");
    }

    public static void addKeywordToCategoryLink(String keyword, String relation, String category){
        Neo4jClient.addTriple(keyword, relation, category,
                "keyword", "categorized as", "category",
                "keyword text", "category text");
    }

    public static void addAspectToKeywordLink(String aspect, String relation, String keyword){
        Neo4jClient.addTriple(aspect, relation, keyword,
                "aspect", "has keyword", "keyword",
                "aspect text", "keyword text");
    }

    public static void addKeywordToDefinitionLink(String keyword, String relation, String definition){
        Neo4jClient.addTriple(keyword, relation, definition,
                "keyword", "defined by", "definition",
                "keyword text", "definition text");
    }

    public static void addKeywordToSubjectLink(String keyword, String relation, String subject){
        Neo4jClient.addTriple(keyword, relation, subject,
                "keyword", "links to", "subject",
                "keyword text", "subject text");
    }

    public static void addObjectToKeywordLink(String object, String relation, String keyword){
        Neo4jClient.addTriple(object, relation, keyword,
                "object", "has definition", "keyword",
                "object text", "keyword text");
    }

    public static void addCategoryToHigherCategoryLink(String categoryCurrent, String relation, String categoryPrevious){
        Neo4jClient.addTriple(categoryCurrent, relation, categoryPrevious,
                "category", "belongs to", "category",
                "category text", "category text");
    }

    public static void main(String[] args) {
        Neo4jClient.deleteEverything();
        addProjectAspectTriple("Car", "has aspect", "Design");
        addAspectToKeywordLink("Design", "has relation", "functional design");
        addSubjectObjectTriple("design", "is", "cool");
        addSubjectObjectTriple("functional design", "takes", "alot of time");
        addKeywordToSubjectLink("functional design", "links to", "functional design");
        addAspectToKeywordLink("Design", "has keyword", "time");
        addObjectToKeywordLink("alot of time", "has definition", "time");
        addKeywordToCategoryLink("functional design", "categorized as", "design");
        addCategoryToHigherCategoryLink("design", "belongs to", "art");
        addKeywordToDefinitionLink("time", "is", "important");
        Neo4jClient.closeThis();

    }
}
