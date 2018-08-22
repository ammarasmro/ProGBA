package data_structures;

public class Triple {

    private String verb;

    private RichTextNode subject;
    private RichTextNode object;

    public Triple() {

    }

    public Triple(String subject, String verb, String object) {
        this.subject = new RichTextNode(subject);
        this.verb = verb;
        this.object = new RichTextNode(object);
    }

    public String getVerb() {
        return verb;
    }

    public RichTextNode getSubject() {
        return subject;
    }

    public RichTextNode getObject() {
        return object;
    }

    public void setObject(RichTextNode object) {
        this.object = object;
    }

    public void setSubject(RichTextNode subject) {
        this.subject = subject;
    }

    public String getTripleAsString(){
        return String.format("%s %s %s", subject.getSentence(), verb, object.getSentence());
    }

    @Override
    public String toString(){
        String subjectKeywords = "";
        String objectKeywords = "";
        try {
            subjectKeywords = subject.getKeywords().toString();
            objectKeywords = object.getKeywords().toString();
        } catch(Exception e) {
            System.out.println("Error: No keywords");
        }
        return String.format("%s ---- %s ----> %s \n"
                        + "Keywords within subject: %s\n"
                        + "Keywords within object: %s\n", subject, verb, object,
                subjectKeywords, objectKeywords);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((object == null) ? 0 : object.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((verb == null) ? 0 : verb.hashCode());
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
        Triple other = (Triple) obj;
        if (object == null) {
            if (other.object != null)
                return false;
        } else if (!object.equals(other.object))
            return false;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (verb == null) {
            if (other.verb != null)
                return false;
        } else if (!verb.equals(other.verb))
            return false;
        return true;
    }

    public static void main(String[] args) {
        Triple testTriple = new Triple("I", "am", "awesome");
        testTriple.getSubject().addKeyword(new Keyword("I"));
        testTriple.getObject().addKeyword(new Keyword("awesome"));
        System.out.println(testTriple);
    }

}
