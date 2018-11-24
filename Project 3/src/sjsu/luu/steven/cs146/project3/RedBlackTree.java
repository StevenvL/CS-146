package sjsu.luu.steven.cs146.project3;

public class RedBlackTree<Key extends Comparable<Key>> {
	private RedBlackTree.Node<String> root;

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
		// fill

	}

	public void insert(String data) {
		addNode(data);
	}

	public RedBlackTree.Node<String> lookup(String k) {
		// fill
	}

	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n) {
		//
	}

	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n) {
		//
	}

	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n) {
		return n.parent.parent;
	}

	public void rotateLeft(RedBlackTree.Node<String> n) {
		//
	}

	public void rotateRight(RedBlackTree.Node<String> n) {
		//
	}

	public void fixTree(RedBlackTree.Node<String> current) {
		//
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
}
