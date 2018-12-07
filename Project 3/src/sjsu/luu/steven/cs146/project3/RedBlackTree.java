package sjsu.luu.steven.cs146.project3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Professor Potika and Steven Luu
 *
 * @param <Key> the type of data that will be stored in the red black tree.
 * 
 * This class will build a RedBlackTree
 */
public class RedBlackTree<Key extends Comparable<Key>> {
	private final static String INPUT_FILE_LOCATION = "src/sjsu/luu/steven/cs146/project3/dictionary.txt";
	private static RedBlackTree.Node<String> root;

	/**
	 * @author Professor Potika and Steven Luu
	 *
	 * @param <Key> is the type of data that will be stored in the node.
	 */
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

	
	/**
	 * Checks to see if a node is a leaf node.
	 * @param n is the node to be checked if its a leaf node.
	 * @return if n is leaf node, return true. If n is not a leaf node return false.
	 */
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
		 * @param n the visited node
		 */
		void visit(Node<Key> n);
	}
	

	public void visit(Node<Key> n) {
		System.out.println(n.key);
	}

	
	/**
	 * Uses the preorder traverse to print the tree out.
	 */
	public void printTree() { // preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;
		printTree(currentNode);
	}

	
	/**
	 * Prints out node passed out and recursively goes through to tree and prints out the data until it reaches a leaf.
	 * 
	 * @param node to be printed
	 */
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
	/**
	 * Adds a node to the red black tree and calls upon fixTree() to ensure it keeps all properties of a red black tree.
	 * 
	 * @param data is added to the red black tree as a node.
	 */
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

	
	/**
	 * Converts the string to a node and is added in the correct place in the red black tree.
	 * 
	 * @param data is a string type to be added.
	 */
	public void insert(String data) {
		addNode(data);
	}

	
	/**
	 * Uses compareTo() to check the key of each node and returns the node that has the same data.
	 * 
	 * @param k is the data to be searched up
	 * @return the node that has the same key as the string passed in
	 */
	public RedBlackTree.Node<String> lookup(String k) {
		Node<String> compareToMe = root;
		boolean found = false;


		while (found != true) {
			if(compareToMe == null)
				return null;
			else if (k.compareTo(compareToMe.key) > 0)
				compareToMe = compareToMe.rightChild;
			else if (k.compareTo(compareToMe.key) < 0)
				compareToMe = compareToMe.leftChild;
			else if (k.compareTo(compareToMe.key) == 0)
				return compareToMe;
		}
		return null;
	}

	
	/**
	 * Finds the sibling node of the current node and returns it.
	 * 
	 * @param n is the current node and is looking for the sibling of said node.
	 * @return the sibling node.
	 */
	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) { // needs to return the sibling of the
																				// node
		RedBlackTree.Node<String> parentNode = n.parent;
		if (n.leftChild == n)
			return parentNode.rightChild;
		else if (parentNode.rightChild == n)
			return parentNode.leftChild;
		else
			return null;
		
	}

	
	/**
	 * Is based of the getSibling method and calls it with the parent of the current node.
	 * 
	 * @param n is the current node.
	 * @return the aunt node of the current node.
	 */
	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		return getSibling(n.parent);
	}
	

	/**
	 * Simply uses .parent.parent of the current node to get the grandparent node.
	 * 
	 * @param n is the current node.
	 * @return the grandparent node of the current node.
	 */
	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		return n.parent.parent;
	}
	

	/**
	 * Rotate left around current node by using psudeocode provided by Professor Potika.
	 * 
	 * @param n is the node to be rotated around.
	 */
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
	

	/**
	 * Rotate right around current node by using psudeocode provided by Professor Potika.
	 * 
	 * @param n is the node to be rotated around.
	 */
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

	
	/**
	 * Uses instructions in the provided .pdf to solve all scenarios to fix a red black tree.
	 * 
	 * @param current is the node to be checked to see if it keeps the tree's red black properties.
	 */
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


	/**
	 * Checks to see if the node has null data.
	 * 
	 * @param n node to be checked
	 * @return true if node's data is null, otherwise return false;
	 */
	public boolean isEmpty(RedBlackTree.Node<String> n) {
		if (n.key == null) {
			return true;
		}
		return false;
	}

	
	/**
	 * Uses .compareTo() to check whether the child node is < parent node.
	 * 
	 * @param parent node
	 * @param child  node
	 * @return true if child node is less than the parent node.
	 */
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

	
	/**
	 * Returns the data stored in the node. Used for debugging purposes.
	 * 
	 * @param returnMe node to be returned
	 */
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
	
