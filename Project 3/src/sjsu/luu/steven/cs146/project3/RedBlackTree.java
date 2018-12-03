package sjsu.luu.steven.cs146.project3;

public class RedBlackTree<Key extends Comparable<Key>> {
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
		if (node.isLeaf()) {
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}

	// place a new node in the RB tree with data the parameter and color it red.
	public void addNode(String data) { // this < that <0. this > that >0
		RedBlackTree.Node<String> currentNode = root;
		RedBlackTree.Node<String> addMe = new RedBlackTree.Node<String>(data);

		if (root == null) {
			root = addMe;
		} else {
			while (currentNode.isLeaf() == false) { // While loop to get to lead node.
				if (addMe.key.compareTo(currentNode.key) > 0)
					currentNode = currentNode.rightChild;
				else
					currentNode = currentNode.leftChild;
			}
			// Once leaf node is found compare it to addMe so it knows which side to add it
			// on.
			// Update whether leftChild or rightChild. Set the leaf as the parent of addMe.
			// Set the color to 0 and isRed to true.
			if (addMe.key.compareTo(currentNode.key) > 0) {
				addMe = currentNode.rightChild;
				currentNode = addMe.parent;
			} else
				addMe = currentNode.leftChild;
			currentNode = addMe.parent;
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
			if (compareToMe.key.compareTo(k) > 0)
				compareToMe = compareToMe.rightChild;
			else if (compareToMe.key.compareTo(k) < 0)
				compareToMe = compareToMe.leftChild;
			else if (compareToMe.key.compareTo(k) == 0)
				return compareToMe;
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
			return parentNode.rightChild;
		if (grandParentNode.rightChild.key.compareTo(parentNode.key) == 0)
			return parentNode.leftChild;
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
		RedBlackTree.Node<String> tempNode = n.rightChild; // y
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
		//need to check if shits busted or not.
		if (current == root || current == current.parent) {
			RedBlackTree.Node<String> auntNode = getAunt(current);
			RedBlackTree.Node<String> parentNode = current.parent;
			RedBlackTree.Node<String> grandParentNode = parentNode.parent;
		} else {
			if (current.key.equals(root.key)) {
				current.isRed = false;
				current.color = 1;
			}

			else if (parentNode.isRed == false && parentNode.color == 1) {
				return;
			}

			else if (current.isRed && parentNode.isRed) { // Need to deal with all cases here
				if ((auntNode.isRed == false && auntNode.color == 1) || auntNode == null) {
					// CASE 1
					if (isLeftChild(grandParentNode, parentNode) && !isLeftChild(grandParentNode, current)) {
						rotateLeft(parentNode);
						fixTree(parentNode);
					}
					// CASE 2
					else if (!isLeftChild(grandParentNode, parentNode) && isLeftChild(grandParentNode, current)) {
						rotateRight(parentNode);
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
				} else if (auntNode.isRed) {
					current.parent.isRed = false; // Parent is changed to black.
					current.parent.color = 1;
					auntNode.isRed = false; // auntNode is changed to black.
					auntNode.parent.color = 1;
					grandParentNode.isRed = true; // GrandParent is changed to red.
					grandParentNode.color = 0;
					fixTree(grandParentNode); // Recursively fix tree
				}
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

	public static void main(String[] args) {
		RedBlackTree rbt = new RedBlackTree();
		rbt.insert("D");
		rbt.insert("B");
		System.out.println(rbt.lookup("B"));
	}
}
