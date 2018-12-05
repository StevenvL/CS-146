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

	public void printTree(RedBlackTree.Node<String> node){
		System.out.print(node.key);
		System.out.println();
		if (isLeaf(node)){
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
				if(addMe.key.compareTo(parentOfCurrent.key) > 0)
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
		if (current == root) {
			current.isRed = false;
			current.color = 1;
			return;
		} else if (current.parent.isRed == false) {
			return;
		} 
		else if (current.isRed == true && current.parent.isRed == true) {
			if (getAunt(current) == null || getAunt(current).isRed == false) {
				if (getGrandparent(current).leftChild == current.parent &&
						current.parent.rightChild == current) {
					Node<String> parent = current.parent;
					rotateLeft(current);
					fixTree(parent);
				} else if (getGrandparent(current).rightChild == current.parent &&
						current.parent.leftChild == current) {
					Node<String> parent = current.parent;
					rotateRight(current);
					fixTree(parent);			
				} else if (getGrandparent(current).leftChild == current.parent &&
						current.parent.leftChild == current) {
					current.parent.isRed = false;
					current.parent.color = 1;
					getGrandparent(current).isRed = true;
					rotateRight(getGrandparent(current));
				} else if (getGrandparent(current).rightChild == current.parent &&
						current.parent.rightChild == current) {
					current.parent.isRed = false;
					current.parent.color = 1;
					getGrandparent(current).isRed = true;
					rotateLeft(getGrandparent(current));
				}
			} else if (getAunt(current).isRed == true) {
				current.parent.isRed = false;
				current.parent.color = 1;
				getAunt(current).isRed = false;
				getAunt(current).color = 1;
				getGrandparent(current).isRed = true;
				getGrandparent(current).color = 0;
				fixTree(getGrandparent(current));
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
	
	public void returnData(RedBlackTree.Node<String> returnMe) { //temp code delete later.
		System.out.println(returnMe.key);
	}

	public static void main(String[] args) {
		RedBlackTree<String> rbt = new RedBlackTree<>();
		rbt.insert("D");
		
		
		rbt.insert("B");
		rbt.insert("A");
		
		//rbt.insert("C");
		
		//rbt.insert("F");
		
		//rbt.insert("E");


		rbt.printTree();

		
		
	}
}
