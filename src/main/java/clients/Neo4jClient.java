package clients;

import static org.neo4j.driver.v1.Values.parameters;

import java.util.Arrays;
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

//    private static Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "ammar" ) );

    private static Driver driver;

    public Neo4jClient(){
        this( "bolt://localhost:7687", "neo4j", "ammar" );
    }

    public Neo4jClient( String uri, String user, String password ) {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
//        init();
    }

    public boolean init() {
        deleteEverything();
        return true;
    }

    public void addTriple(String source, String relation, String destination,
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
                            parameters( "source", source.toLowerCase(), "relation",
                                    relation.toLowerCase(), "destination", destination.toLowerCase() ) );
                    result.consume();
                    return "Triple added!";
                }
            } );
        } catch (Exception e){
            System.out.println(source + " " + relation + " " + destination);
        }
    }

    public void addNodeWithTags(String nodeType, String nodeText, String[] docTags, String[] userTags, int convTag){
        try ( Session session = driver.session() )
        {
            String transactionResponse = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( String.format("MERGE (node: %s {%s: $nodeText})\n" +
                                    "ON CREATE SET node.docTags = $docTags, node.userTags = $userTags, node.conversations = [$convTag]\n" +
                                    "ON MATCH SET node.docTags = node.docTags + [x IN $docTags WHERE NOT x IN node.docTags]," +
                                    "node.userTags = node.userTags + [x IN $userTags WHERE NOT x IN node.userTags]," +
                                    "node.conversations = node.conversations + $convTag",
                            TextUtils.toUpperCamelCase(nodeType),
                            TextUtils.toLowerCamelCase(nodeType + " text")),
                            parameters( "nodeText", nodeText.toLowerCase(), "docTags",
                                    docTags, "userTags", userTags, "convTag", convTag ) );
                    result.consume();
                    return "Triple added!";
                }
            } );
        }
    }

    public void addRelation(String source, String relation, String destination,
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
                            parameters( "source", source.toLowerCase(), "relation",
                                    relation.toLowerCase(), "destination", destination.toLowerCase() ) );
                    result.consume();
                    return "Triple added!";
                }
            } );
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

    public void deleteEverything()
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

        Neo4jClient neo4jClient = new Neo4jClient( "bolt://localhost:7687", "neo4j", "ammar" );
        String[] arr = {"\"ammar\"", "\"neo4j\""};
        System.out.println(Arrays.toString(arr));

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
