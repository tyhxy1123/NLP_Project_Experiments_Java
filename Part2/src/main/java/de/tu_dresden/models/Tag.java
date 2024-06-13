package de.tu_dresden.models;

import java.util.Objects;

/**
 * encapsulate the non-terminal string with it's backtrace
 */
public class Tag implements Comparable<Tag>{


    // store terminal word
    private String word;

    // store tag
    private String tag;

    // unique id of this tag
    private int id;

    // back trace of left tag and right tag
    Tag left;
    Tag right;

    // store the actual probability of this trace represented by this tag
    private Double actualProb;

    public Tag(int id, String tag, Tag left, Tag right, Double prob){
        this.id = id;
        this.tag = tag;
        this.left = left;
        this.right = right;
        actualProb = prob;
    }

    public Tag(int id, String tag, Tag left, Double prob){
        this.id = id;
        this.tag = tag;
        this.left = left;
        actualProb = prob;
    }

    public Tag(int id, String tag, String word, Double prob){
        this.id = id;
        this.tag = tag;
        this.word = word;
        actualProb = prob;
    }

    public boolean isSingle(){
        return this.right == null;
    }

    public boolean isPaar(){
        return this.right != null;
    }

    public boolean isLastNonterminal(){
        return this.word != null;
    }

    public String getWord() {
        return word;
    }

    public String getTag() {
        return tag;
    }

    public long getId() {
        return id;
    }

    public Tag getLeft() {
        return left;
    }

    public Tag getRight() {
        return right;
    }

    public double getActualProb() {
        return actualProb;
    }

    public void setActualProb(double actualProb) {
        this.actualProb = actualProb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id == tag.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Tag o) {
        if(getId() == o.getId()) return 0;
        if(getActualProb() <= o.getActualProb()) return 1;
        if(getActualProb() > o.getActualProb()) return -1;
        return 1;
    }

    @Override
    public Tag clone(){
        if(word != null){
            return new Tag(this.id, this.tag, this.word, this.actualProb);
        }

        else if(right == null){
            return new Tag(this.id, this.tag, this.left.clone(), this.actualProb);
        }

        else return new Tag(this.id, this.tag, this.left.clone(), this.right.clone(), this.actualProb);
    }

//    @Override
//    public Tag clone(){
//        if(word != null){
//            return new Tag(this.id, this.tag, this.word, this.actualProb);
//        }
//
//        else if(right == null){
//            return new Tag(this.id, this.tag, this.left, this.actualProb);
//        }
//
//        else return new Tag(this.id, this.tag, this.left, this.right, this.actualProb);
//    }

    @Override
    public String toString() {

        if(word!=null){
            return "("+tag+" "+word+")";
        }

        else if(right==null){
            return "("+tag+" "+left+")";
        }

        else{
            return "("+tag+" "+left+" "+right+")";
        }
//        return "{" +
//                "word='" + word + '\'' +
//                ", tag='" + tag + '\'' +
//                ", id=" + id +
//                ", left=" + left +
//                ", right=" + right +
//                ", actualProb=" + actualProb +
//                '}';
    }
}
