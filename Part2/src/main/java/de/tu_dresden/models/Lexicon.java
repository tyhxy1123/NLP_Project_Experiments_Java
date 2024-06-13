package de.tu_dresden.models;

import java.util.Objects;

public class Lexicon {
    private String child;
    private String parent;
    private double prob;

    public Lexicon(String parent, String child, double prob) {
        this.parent = parent;
        this.child = child;
        this.prob = prob;
    }

    public Lexicon(String parent, String child) {
        this.parent = parent;
        this.child = child;
    }

    public double getProb() {
        return this.prob;
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
