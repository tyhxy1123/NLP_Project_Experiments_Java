package App;


import java.util.*;
import java.util.stream.Collectors;

public class Node {

    private Node parent;
    private List<Node> children;
    private String value;
    private int floor;
    private boolean terminal;
    private boolean addedRule = false;

    public List<String> getChildrenString(){
        return children.stream().map(n -> n.getValue()).collect(Collectors.toList());
    }

    private Node(Node parent, String value, boolean isTerminal){
        if(parent == null || value == null) throw new NullPointerException();
        if(value == "") throw new IllegalArgumentException();
        this.parent = parent;
        this.value = value;
        this.children = new ArrayList<>();
        this.floor = parent.getFloor() + 1;
        this.terminal = isTerminal;
    }

    public Node(String value) {
        if(value == null) throw new NullPointerException();
        if(value == "") throw new IllegalArgumentException();
        this.parent = null;
        this.value = value;
        this.children = new ArrayList<>();
        this.floor = 1;
        this.terminal = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return value.equals(node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public Node addChild(String value, boolean isTerminal){
        Node tmp = new Node(this, value, isTerminal);
        this.children.add(tmp);
        return tmp;
    }

    public Node addChild(String value){
        Node tmp = new Node(this, value, false);
        this.children.add(tmp);
        return tmp;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFloor() {return floor;}

    public List<Node> getChildren() {
        return this.children;
    }

    public Node getParent() {
        return this.parent;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isTerminal(){
        return this.terminal;
    }

    public void addedRule(){
        this.addedRule = true;
    }

    public boolean isAddedRule(){
        return this.addedRule;
    }
}
