package interfaces;


import clients.Neo4jClient;

public class GraphDBInterface {

    private  static Neo4jClient client = new Neo4jClient();

    public static void addSubjectObjectTriple(String subject, String verb, String object){
        client.addTriple(subject, verb, object,
                "subject", "verb", "object",
                "subject Text", "object Text");
    }

    public static void addProjectAspectTriple(String project, String link, String aspect){
        client.addTriple(project, link, aspect,
                "project", "has aspect", "aspect",
                "project name", "aspect Text");
    }

    public static void addKeywordToCategoryLink(String keyword, String relation, String category){
        client.addTriple(keyword, relation, category,
                "keyword", "categorized as", "category",
                "keyword text", "category text");
    }

    public static void addAspectToKeywordLink(String aspect, String relation, String keyword){
        client.addTriple(aspect, relation, keyword,
                "aspect", "has keyword", "keyword",
                "aspect text", "keyword text");
    }

    public static void addKeywordToDefinitionLink(String keyword, String relation, String definition){
        client.addTriple(keyword, relation, definition,
                "keyword", "defined by", "definition",
                "keyword text", "definition text");
    }

    public static void addKeywordToSubjectLink(String keyword, String relation, String subject){
        client.addTriple(keyword, relation, subject,
                "keyword", "links to", "subject",
                "keyword text", "subject text");
    }

    public static void addObjectToKeywordLink(String object, String relation, String keyword){
        client.addTriple(object, relation, keyword,
                "object", "has definition", "keyword",
                "object text", "keyword text");
    }

    public static void addCategoryToHigherCategoryLink(String categoryCurrent, String relation, String categoryPrevious){
        client.addTriple(categoryCurrent, relation, categoryPrevious,
                "category", "belongs to", "category",
                "category text", "category text");
    }

    public static void main(String[] args) {
        client.deleteEverything();
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
