package de.tu_dresden.models;

import java.util.*;

public class MatrixCell {
    private Set<String> tokens;
    private Set<List<String>> cartResult;
    private List<Map<String, Map<String, Double>>> cartResultNew;
    private Map<String, List<String>> tagToTags = new HashMap<>();
    //    private Map<String, String> tagToSingle = new HashMap<>();
    private Map<String, Double> tagAppearanceProb = new HashMap<>();
    private SortedSet<Tag> allTags = new TreeSet<>();

    public SortedSet<Tag> getAllTags(){
        return this.allTags;
    }

    public Tag getTag(long id){
        for(Tag t : this.allTags){
            if(t.getId() == id) return t;
        }
        return null;
    }

    public void cellClear(){
        this.tokens.clear();
        this.tagToTags.clear();
        this.tagAppearanceProb.clear();
        this.cartReset();
    }

    public void cellToZero(){
        for (String s : this.tagAppearanceProb.keySet()) {
            this.tagAppearanceProb.put(s, (double) 0);
        }
    }

    public void setTagToTags(String parent, List<String> children){
        this.tagToTags.put(parent, children);
    }
    public void setTagAppearanceProb(String tag, double prob){
        tagAppearanceProb.put(tag, prob);
    }
    public MatrixCell() {
        tokens = new HashSet();
        this.cartResult = new HashSet<>();
    }

//    public MatrixCell(String cellContent, double prob) {
//        tokens = new HashSet<>();
//        tokens.add(cellContent);
//        this.prob = prob;
//        cartReset();
//    }
//
//    public MatrixCell(Set<String> cellContent, double prob) {
//        this.prob = prob;
//        tokens = cellContent;
//        cartReset();
//    }


    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    public void cartReset() {
        if (!this.tokens.isEmpty()) {
            Set<List<String>> tmp = new HashSet<>();
            for (String token : tokens) {
                List<String> tmpL = new ArrayList<>();
                tmpL.add(token);
                tmp.add(tmpL);
            }
            this.cartResult = tmp;
        }
    }

    //calculate all tag compositions from this cell and given cell
    public void cart(MatrixCell cell) {
        Set<String> objectiveSet = cell.getTokens();
        List<List<String>> heads = new ArrayList<>();

        if (!this.tokens.isEmpty() && !objectiveSet.isEmpty()) {
            for (String s : objectiveSet) {
                for (List<String> strings : cartResult) {
                    for (String string : strings) {
                    }
                    List<String> tmp = new ArrayList<>(strings);
                    tmp.add(s);
                    for (String s1 : tmp) {

                    }
                    heads.add(tmp);
                }
            }
            cartResult = new HashSet<>(heads);
            for (List<String> strings : cartResult) {
            }
        } else if (this.tokens.isEmpty() && objectiveSet.isEmpty()) {
            this.cartResult = new HashSet<>();
        } else {
            if (this.tokens.isEmpty()) {
                for (String s : objectiveSet) {
                    this.cartResult.add(Arrays.asList(s));
                }
            } else {
                for (String token : this.tokens) {
                    this.cartResult.add(Arrays.asList(token));
                }
            }
        }
    }

    public void add(String content) {
        this.tokens.add(content);
        cartReset();
    }

    public void addAll(Set<String> content) {
        this.tokens.addAll(content);
    }

    public void clear() {
        tokens.clear();
        cartReset();
    }

//    public void add(String head, String tail, double prob){
//
//        if(this.heads.contains(head) && (prob > this.tagAppearanceProb.get(head))){
//           if(tag)
//        }
//        this.tagToTags.put(head, Arrays.asList(tail));
//        this.tagAppearanceProb.put(head, prob);
//        this.heads.add(head);
//    }


    //add method accepts only the element that not exist in tokens
    public void add(String head, List<String> tail, double prob) {
//        if (this.tokens.contains(head) && (prob > this.tagAppearanceProb.get(head))) {
//            tagAppearanceProb.put(head, prob);
//            tagToTags.put(head, tail);
//        }
//
//        else if(this.tokens.contains(head) && (prob < this.tagAppearanceProb.get(head))){
//
//        }
//
//        else {
//            this.tagToTags.put(head, tail);
//            this.tagAppearanceProb.put(head, prob);
//            this.tokens.add(head);
//        }
        if(tokens.contains(head)){
            System.out.println("Element to be added exist already!");
            throw new IllegalArgumentException("Element to be added exist already!");
        }
        tokens.add(head);
        tagToTags.put(head, tail);
        tagAppearanceProb.put(head, prob);
        cartReset();

    }

    public void add(Tag tag){
        allTags.add(tag);
    }

    public void put(String head, List<String> tail, double prob){
        this.tokens.add(head);
        this.tagToTags.put(head, tail);
        this.tagAppearanceProb.put(head, prob);
        cartReset();
    }

    public Set<String> getTokens() {
        return this.tokens;
    }

    public Set<List<String>> getCartResult() {
        return cartResult;
    }

    public Map<String, List<String>> getRelations() {
        return tagToTags;
    }

    public Map<String, Double> getTagAppearanceProbs() {
        return tagAppearanceProb;
    }
}
