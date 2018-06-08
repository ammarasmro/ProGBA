package conversation_datastructures;

import data_structures.Concept;
import utils.EnrichmentUtility;

import java.util.HashSet;
import java.util.Set;

public class UserUtterance {

    private String text;
    private Set<Concept> concepts;

    public UserUtterance(String text) {
        this.text = text;
        this.concepts = new HashSet<Concept>();
        this.concepts.addAll(EnrichmentUtility.getConceptsOfText(text));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }

    @Override
    public String toString(){
        return String.format("User: %s\nConcepts: %s", text, concepts.toString());
    }

}
