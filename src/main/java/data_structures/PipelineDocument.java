package data_structures;

import java.util.ArrayList;
import java.util.List;

public class PipelineDocument {


    private String context;
    private String docId;
    private double docScore;
    private int resultNumber;
    private String span;
    private double spanScore;

    private List<SemanticRole> semanticRoles;
    private List<Keyword> keywords;
    private List<Concept> concepts;
    private List<Category> categories;
    private List<Triple> triples;
    private List<Triple> openIETriples;
    private List<Triple> corefTriples;

    public PipelineDocument(DrQAResponseDocument drQAResponseDocument){
        this.context = drQAResponseDocument.getContext();
        this.docId = drQAResponseDocument.getDoc_id();
        this.docScore = drQAResponseDocument.getDoc_score();
        this.resultNumber = drQAResponseDocument.getResult_number();
        this.span = drQAResponseDocument.getSpan();
        this.spanScore = drQAResponseDocument.getSpan_score();
        this.triples = new ArrayList<Triple>();
    }

    public String getContext(){
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public String getDocId(){
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public double getDocScore(){
        return docScore;
    }

    public void setDocScore(double docScore) {
        this.docScore = docScore;
    }

    public int getResultNumber(){
        return resultNumber;
    }

    public void setResultNumber(int resultNumber) {
        this.resultNumber = resultNumber;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public double getSpanScore() {
        return spanScore;
    }

    public void setSpanScore(double spanScore) {
        this.spanScore = spanScore;
    }

    public List<SemanticRole> getSemanticRoles() {
        return semanticRoles;
    }

    public void setSemanticRoles(List<SemanticRole> semanticRoles) {
        this.semanticRoles = semanticRoles;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Triple> getTriples() {
        return triples;
    }

    public void setTriples(List<Triple> triples) {
        this.triples = triples;
    }




}
