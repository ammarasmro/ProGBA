package data_structures;

public class SemanticRole {

    private String sentence;

    private Triple triple;

    public SemanticRole(String sentence, Triple triple){
        this.sentence = sentence;
        this.triple = triple;
    }

    public String getSentence() {
        return sentence;
    }

    public Triple getTriple() {
        return triple;
    }

    public void setTriplet(Triple triple) {
        this.triple = triple;
    }

    @Override
    public String toString() {
        return String.format("Sentence: %s\nTriple: %s", sentence, triple);
    }

    public static void main(String[] args) {
        SemanticRole testSemanticRole = new SemanticRole("I know Java", (new Triple("I", "know", "java")));
        System.out.println(testSemanticRole);
    }
}
