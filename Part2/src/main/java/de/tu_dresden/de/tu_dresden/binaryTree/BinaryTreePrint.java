package de.tu_dresden.de.tu_dresden.binaryTree;

public class BinaryTreePrint {
	
	public static void main(String []args) {
		Node f = new Node(null, null, "f");
		Node e = new Node(null, null, "e");
		Node d = new Node(null, null, "d");
		Node c = new Node(f, null, "c");
		Node b = new Node(d, e, "b");
		Node a = new Node(b, c, "a");
		
		System.out.println(parse(a).toString());
	}
	
	public static StringBuilder parse(Node tree) {
		StringBuilder result = new StringBuilder("( \"value\": \"");
		String value = tree.getValue();
		if (value != null) {
			result.append(value);
		} else {
			result.append("null");
		}
		
		result.append("\", \"left\": ");
		
		/**
		 * Get result of left sub tree
		 */
		if (tree.hasLeftChild()) {
			result.append(parse(tree.getLeft()));
		} else {
			result.append("\"null\"");
		}
		
		result.append(", \"right\": ");
		
		/**
		 * Get result of right sub tree
		 */
		if (tree.hasRightChild()) {
			result.append(parse(tree.getRight()));
		} else {
			result.append("\"null\"");
		}
		
		result.append(")");
		
		return result;
	}

}

class Node {
	private Node left;
	private Node right;
	private String value;
	
	Node(Node left, Node right, String value) {
		this.left = left;
		this.right = right;
		this.value = value;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	public boolean hasLeftChild() {
		return this.left != null;
	}
	
	public boolean hasRightChild() {
		return this.right != null;
	}
	
}