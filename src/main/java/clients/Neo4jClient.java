package clients;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.List;
import java.util.Set;

import data_structures.Keyword;
import data_structures.Triple;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import utils.TextUtils;

public class Neo4jClient implements AutoCloseable {

    private static Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "ammar" ) );

    public Neo4jClient( String uri, String user, String password ) {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        init();
    }

    public boolean init() {
        deleteEverything();
        return true;
    }

    public static void addTriple(String source, String relation, String destination,
                                 String sourceType, String relationType, String destinationType,
                                 String sourceTextType, String destinationTextType){
        try ( Session session = driver.session() )
        {
            String transactionResponse = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( String.format("MERGE (source: %s {%s: $source})\n" +
                                    "MERGE (destination: %s {%s: $destination})\n" +
                                    "MERGE (source)-[: %s {relationText: $relation}]->(destination)",
                            TextUtils.toUpperCamelCase(sourceType),
                            TextUtils.toLowerCamelCase(sourceTextType),
                            TextUtils.toUpperCamelCase(destinationType),
                            TextUtils.toLowerCamelCase(destinationTextType),
                            TextUtils.toUpperUnderscored(relationType)),
                            parameters( "source", source, "relation", relation, "destination", destination,
                                    "sourceType", TextUtils.toUpperCamelCase(sourceType),
                                    "sourceTextType", TextUtils.toLowerCamelCase(sourceTextType),
                                    "destinationType", TextUtils.toUpperCamelCase(destinationType),
                                    "destinationTextType", TextUtils.toLowerCamelCase(destinationTextType),
                                    "relationType", TextUtils.toUpperUnderscored(relationType)) );
                    result.consume();
                    return "Triple added!";
                }
            } );
        }
    }



    public static void startNewProject( String project, String aspect )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "CREATE (car:Project {title:$project, started:timestamp()})\n" +
                                    "CREATE (design:Aspect {aspectName: $aspect})\n" +
                                    "CREATE (car)-[:HASASPECT]->(design)",
                            parameters( "project", project, "aspect", aspect ) );
                    result.consume();
                    return "Project " + project + " was created!";
                }
            } );
            System.out.println( greeting );
        }
    }

    public void addCategoryLevel(String categoryLevel, String previousCategoryLevel) {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "MERGE (categoryLevel:CategoryLevel {categoryText: $categoryLevel})\n" +
                                    "WITH categoryLevel\n" +
                                    "MATCH (categoryLevelPrevious:CategoryLevel {categoryText: $previousCategoryLevel})\n" +
                                    "WITH categoryLevelPrevious, categoryLevel\n" +
                                    "MERGE (categoryLevel)-[:BELONGSTO]->(categoryLevelPrevious)",
                            parameters( "categoryLevel", categoryLevel, "previousCategoryLevel", previousCategoryLevel ));
                    result.consume();
                    return "Category level added!\n" + categoryLevel;
                }
            } );
            System.out.println( greeting );

        }
    }

    public void getAllData()
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "MATCH (project:Project)-[:HASASPECT]->(aspect:Aspect)-[:CONTAINS]->(subject:Subject)-[]->(object:Object)\n" +
                                    "RETURN project, aspect, subject, object",
                            parameters() );
                    return result.single().get(0).get("message").asString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public static void deleteEverything()
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "MATCH (n) DETACH DELETE n",
                            parameters() );
                    return "Database emptied...";
                }
            } );
            System.out.println( greeting );
        }
    }

    public static void main( String... args ) throws Exception
    {
        deleteEverything();
        startNewProject("Car", "Design");


//        Neo4jClient neo4jClient = new Neo4jClient( "bolt://localhost:7687", "ammar", "ammar" );

//        neo4jClient.addKeyword(new Keyword("Keyword 1"), "Design");
//        neo4jClient.addKeyword(new Keyword("Keyword 2"), "Design");
//        neo4jClient.addKeyword(new Keyword("Keyword 3"), "Design");

//        neo4jClient.addRichTriplet(new Triple("Subject 1", "Verb 1", "Object 1"));
//        neo4jClient.linkKeywordToSubject("Subject 1", new Keyword("Keyword 1"));
//        neo4jClient.linkObjectToKeyword("Object 1", new Keyword("Keyword 3"));

//        neo4jClient.addRichTriplet(new Triple("Subject 2", "Verb 2", "Object 2"));
//        neo4jClient.linkKeywordToSubject("Subject 2", new Keyword("Keyword 1"));
//        neo4jClient.linkObjectToKeyword("Object 2", new Keyword("Keyword 3"));

//        try ( GraphDispatcher neo4jClient = new GraphDispatcher( "bolt://localhost:7687", "ammar", "ammar" ) )
//        {
//        		greeter.startNewProject("Car", "Design");
//        		greeter.addAspectsToProject("Car", "Performance");
//        		greeter.addTriplet("Automotive Design", "a set of vocations and occupations", "IS", "Car", "Design");
//        		greeter.addTriplet("Automotive Suspension Design", "a kind of design", "IS", "Car", "Design");
//            greeter.printGreeting( "hello, world" );
//        }
    }

    public static void closeThis(){
        driver.close();
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        driver.close();
    }



}
