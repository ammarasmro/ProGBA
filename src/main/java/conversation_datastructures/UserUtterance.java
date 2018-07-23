package conversation_datastructures;

import data_structures.Concept;
import utils.EnrichmentUtility;

import java.util.HashSet;
import java.util.Set;

public class UserUtterance {

    private Integer id;
    private String text;
    private Set<Concept> concepts;

    public UserUtterance(Integer id, String text) {
        this.id = id;
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

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    @Override
    public String toString(){
        return String.format("User: %s\nConcepts: %s", text, concepts.toString());
    }

}
