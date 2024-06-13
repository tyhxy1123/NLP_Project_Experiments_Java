package de.tu_dresden.models;

import java.util.List;
import java.util.Objects;

public class Rule implements Comparable<Rule>{
    private String parent;
    private List<String> children;
    private double prob;

    public Rule(String parent, List<String> children, double prob) {
        this.parent = parent;
        this.children = children;
        this.prob = prob;
    }

    public Rule(String parent, List<String> children) {
        if (parent == null || children == null) throw new NullPointerException();
        if (parent.isEmpty() || children.isEmpty()) throw new IllegalArgumentException();
        this.parent = parent;
        this.children = children;
    }

    public double getProb() {
        return this.prob;
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

    @Override
    public int compareTo(Rule o) {
        if(this.getProb() < o.getProb()) return 1;
        else if (this.getProb() == o.getProb()) return 0;
        else return -1;
    }
}
