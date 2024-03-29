package data_structures;

import java.util.HashSet;
import java.util.List;

public class RichTextNode {
    int id;
    String sentence;
    HashSet<Keyword> keywords;

    public RichTextNode(String sentence) {
        this.id = -1;
        this.sentence = sentence;
        this.keywords = new HashSet<Keyword>();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public HashSet<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(HashSet<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
    }

    public void addAllKeywords(List<Keyword> keywordsList) {
        keywords.addAll(keywordsList);
    }

//    @Override
//    public String toString() {
//        return String.format("String: %s\nAssociated Keywords: %s", sentence, getKeywords());
//    }

    @Override
    public String toString(){
        return sentence;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((sentence == null) ? 0 : sentence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RichTextNode other = (RichTextNode) obj;
        if (sentence == null) {
            if (other.sentence != null)
                return false;
        } else if (!sentence.equals(other.sentence))
            return false;
        return true;
    }

    public static void main(String[] args) {
        RichTextNode testRichTextNode = new RichTextNode("bla is good");
        testRichTextNode.addKeyword(new Keyword("bla"));
        System.out.println(testRichTextNode);
    }
}
