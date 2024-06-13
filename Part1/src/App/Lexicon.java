package App;

import java.util.List;
import java.util.Objects;

public class Lexicon{
    private String child;
    private String parent;
    public Lexicon(String parent, String child) {
        this.parent = parent;
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexicon lexicon = (Lexicon) o;
        return child.equals(lexicon.child) &&
                parent.equals(lexicon.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(child, parent);
    }

    public String getChild() {
        return child;
    }

    public String getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return parent + " " + child;
    }
}
