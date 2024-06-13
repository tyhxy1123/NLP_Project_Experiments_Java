package App;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Rule {
    protected String parent;
    protected List<String> children;

    public Rule(String parent, List<String> children){
        if(parent == null || children == null) throw new NullPointerException();
        if(parent.isEmpty() || children.isEmpty()) throw new IllegalArgumentException();
        this.parent = parent;
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return parent.equals(rule.parent) &&
                children.equals(rule.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, children);
    }

    public String getParent() {
        return parent;
    }

    public List<String> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        String str = parent + " -> ";
        for (String child : children) {
            str += child + " ";
        }
        return str;
    }
}
