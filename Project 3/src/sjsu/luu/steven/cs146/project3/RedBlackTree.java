package sjsu.luu.steven.cs146.project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RedBlackTree<Key extends Comparable<Key>> {
	private final static String INPUT_FILE_LOCATION = "src/sjsu/luu/steven/cs146/project3/dictionary.txt";
	private static RedBlackTree.Node<String> root;

	public static class Node<Key extends Comparable<Key>> { // changed to static

		Key key;
		Node<String> parent;
		Node<String> leftChild;
		Node<String> rightChild;
		boolean isRed;
		int color;

		public Node(Key data) {
			this.key = data;
			leftChild = null;
			rightChild = null;
		}

		public int compareTo(Node<Key> n) { // this < that <0
			return key.compareTo(n.key); // this > that >0
		}

		public boolean isLeaf() {
			if (this.equals(root) && this.leftChild == null && this.rightChild == null)
				return true;
			if (this.equals(root))
				return false;
			if (this.leftChild == null && this.rightChild == null) {
				return true;
			}
			return false;
		}
	}

	public boolean isLeaf(RedBlackTree.Node<String> n) {
		if (n.equals(root) && n.leftChild == null && n.rightChild == null)
			return true;
		if (n.equals(root))
			return false;
		if (n.leftChild == null && n.rightChild == null) {
			return true;
		}
		return false;
	}

	public interface Visitor<Key extends Comparable<Key>> {
		/**
		 * This method is called at each node.
		 * 
		 * @param n
		 *            the visited node
		 */
		void visit(Node<Key> n);
	}

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);
	}

	public void printTree(RedBlackTree.Node<String> node) {
		System.out.print(node.key);
		System.out.println();
		if (isLeaf(node)) {
			return;
		}
		if (node.leftChild != null) {
			printTree(node.leftChild);
		}
		if (node.rightChild != null) {
			printTree(node.rightChild);
		}
	}

	// place a new node in the RB tree with data the parameter and color it red.
	public void addNode(String data) { // this < that <0. this > that >0
		RedBlackTree.Node<String> currentNode = root;
		RedBlackTree.Node<String> parentOfCurrent = null;
		RedBlackTree.Node<String> addMe = new RedBlackTree.Node<String>(data);

		if (root == null) {
			root = addMe;
		} else {
			while (currentNode != null) { // While loop to get to lead node.
				if (addMe.key.compareTo(currentNode.key) > 0) {
					parentOfCurrent = currentNode;
					currentNode = currentNode.rightChild;
				} else {
					parentOfCurrent = currentNode;
					currentNode = currentNode.leftChild;
				}
				// Once leaf node is found compare it to addMe so it knows which side to add it
				// on.
				// Update whether leftChild or rightChild. Set the leaf as the parent of addMe.
				// Set the color to 0 and isRed to true.
			}
			currentNode = addMe;
			addMe.parent = parentOfCurrent;
			if (addMe.key.compareTo(parentOfCurrent.key) > 0)
				parentOfCurrent.rightChild = addMe;
			else
				parentOfCurrent.leftChild = addMe;
		}

		addMe.color = 0;
		addMe.isRed = true;
		fixTree(addMe);
	}

	public void insert(String data) {
		addNode(data);
	}

	public RedBlackTree.Node<String> lookup(String k) {
		Node<String> compareToMe = root;
		boolean found = false;
		// use compareTo() and if its less it will return a negative number, positive if
		// greater, and 0 if its the same.

		while (found != true) {
			if (k.compareTo(compareToMe.key) > 0)
				compareToMe = compareToMe.rightChild;
			else if (k.compareTo(compareToMe.key) < 0)
				compareToMe = compareToMe.leftChild;
			else if (k.compareTo(compareToMe.key) == 0)
				return compareToMe;
			else
				return null;
		}
		return null;
	}

	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) { // needs to return the sibling of the
																				// node
		RedBlackTree.Node<String> parentNode = n.parent;

		if (parentNode.leftChild.key.compareTo(n.key) == 0)
			return parentNode.rightChild;
		if (parentNode.rightChild.key.compareTo(n.key) == 0)
			return parentNode.leftChild;
		else
			return null;
	}

	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> grandParentNode = getGrandparent(n);
		RedBlackTree.Node<String> parentNode = n.parent;

		if (grandParentNode.leftChild.key.compareTo(parentNode.key) == 0)
			return grandParentNode.rightChild;
		if (grandParentNode.rightChild.key.compareTo(parentNode.key) == 0)
			return grandParentNode.leftChild;
		else
			return null;

	}

	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		return n.parent.parent;
	}

	public void rotateLeft(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> tempNode = n.rightChild; // y
		n.rightChild = tempNode.leftChild; // x

		if (tempNode.leftChild != null)
			tempNode.leftChild.parent = n;

		tempNode.parent = n.parent;

		if (n.parent == null)
			root = tempNode;
		else if (n.equals(n.parent.leftChild))
			n.parent.leftChild = tempNode;
		else
			n.parent.rightChild = tempNode;

		tempNode.leftChild = n;
		n.parent = tempNode;
	}

	public void rotateRight(RedBlackTree.Node<String> n) {
		RedBlackTree.Node<String> tempNode = n.leftChild; // y
		n.leftChild = tempNode.rightChild; // x

		if (tempNode.rightChild != null)
			tempNode.rightChild.parent = n;

		tempNode.parent = n.parent;

		if (n.parent == null)
			root = tempNode;
		else if (n.equals(n.parent.rightChild))
			n.parent.rightChild = tempNode;
		else
			n.parent.leftChild = tempNode;

		tempNode.rightChild = n;
		n.parent = tempNode;
	}

	public void fixTree(RedBlackTree.Node<String> current) {
		if (current.key.equals(root.key)) {
			current.isRed = false;
			current.color = 1;
			return;
		} else if (current.parent.isRed == false && current.parent.color == 1) {
			return;
		} else if (current.isRed && current.parent.isRed) { // Need to deal with all cases here
			RedBlackTree.Node<String> auntNode = getAunt(current);
			RedBlackTree.Node<String> parentNode = current.parent;
			RedBlackTree.Node<String> grandParentNode = parentNode.parent;

			if (auntNode == null || (auntNode.isRed == false && auntNode.color == 1)) {
				// CASE 1
				if (isLeftChild(grandParentNode, parentNode) && !isLeftChild(grandParentNode, current)) {
					rotateLeft(current);
					fixTree(parentNode);
				}
				// CASE 2
				else if (!isLeftChild(grandParentNode, parentNode) && isLeftChild(grandParentNode, current)) {
					rotateRight(current);
					fixTree(parentNode);
				}
				// CASE 3
				else if (isLeftChild(grandParentNode, parentNode) && isLeftChild(grandParentNode, current)) {
					parentNode.isRed = false;
					parentNode.color = 1;
					grandParentNode.isRed = true;
					grandParentNode.color = 0;
					rotateRight(grandParentNode);
				}
				// CASE 4
				else if (!isLeftChild(grandParentNode, parentNode) && !isLeftChild(grandParentNode, current)) {
					parentNode.isRed = false; 
					parentNode.color = 1;
					grandParentNode.isRed = true;
					grandParentNode.color = 0;
					rotateLeft(grandParentNode);
				}
			}
			else if (auntNode.isRed) {
			parentNode.isRed = false; // Parent is changed to black.
			parentNode.color = 1;
			auntNode.isRed = false; // auntNode is changed to black.
			auntNode.color = 1;
			grandParentNode.isRed = true; // GrandParent is changed to red.
			grandParentNode.color = 0;
			fixTree(grandParentNode); // Recursively fix tree
			}
		}
	}


	public boolean isEmpty(RedBlackTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child) {
		if (child.compareTo(parent) < 0) {// child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<String> v) {
		preOrderVisit(root, v);
	}

	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	}

	public void returnData(RedBlackTree.Node<String> returnMe) { 
		System.out.println(returnMe.key);
	}

	public static void main(String[] args) throws IOException {
		RedBlackTree<String> rbt = new RedBlackTree<>();
		ArrayList<String> tempList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_LOCATION));
		
		String readLine = br.readLine();
		while(readLine != null) {
			tempList.add(readLine);
			readLine = br.readLine();
		}
		
		for(int i=0; i<tempList.size(); i++) {
			rbt.insert(tempList.get(i));
		}
		rbt.printTree();
		br.close();
	}
}
	
		
		/*
		rbt.addNode("D");
		rbt.addNode("B");
		rbt.addNode("A");
		rbt.addNode("C");
		rbt.addNode("F");
		rbt.addNode("E");
		rbt.addNode("H");
		rbt.addNode("G");
		rbt.addNode("I");
		rbt.addNode("J");
		rbt.printTree();
		*/
	
