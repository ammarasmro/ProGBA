package data_structures;

public class Concept {

    private String concept;
    private String linkToDbPedia;

    public Concept(String concept, String linkToDbPedia) {
        this.concept = concept;
        this.linkToDbPedia = linkToDbPedia;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getLinkToDbPedia() {
        return linkToDbPedia;
    }

    public void setLinkToDbPedia(String linkToDbPedia) {
        this.linkToDbPedia = linkToDbPedia;
    }

    @Override
    public String toString() {
        return String.format("[%s] -> %s", concept, linkToDbPedia);
    }

    public static void main(String[] args) {
        Concept testConcept = new Concept("Design", "dbpedia.org/resource/Q12");
        System.out.println(testConcept);
    }
}
