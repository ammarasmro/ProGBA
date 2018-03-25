package data_structures;

import java.util.ArrayList;
import java.util.List;

public class Keyword {

    String keyword;
    List<Triple> childrenTriples;
    List<Category> categories;

    public Keyword(String keyword) {
        this.keyword = keyword;
        childrenTriples = new ArrayList<Triple>();
        categories = new ArrayList<Category>();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Triple> getChildrenTriples() {
        return childrenTriples;
    }

    public void setChildrenTriples(List<Triple> childrenTriples) {
        this.childrenTriples = childrenTriples;
    }

    public void addChildTriplet(Triple triple) {
        childrenTriples.add(triple);
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return keyword;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((childrenTriples == null) ? 0 : childrenTriples.hashCode());
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
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
        Keyword other = (Keyword) obj;
        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;
        return true;
    }

    public static void main(String[] args) {
        Keyword testKeyword = new Keyword("key");
        System.out.println(testKeyword);
    }
}
