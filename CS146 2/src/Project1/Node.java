package Project1;

/**
 * @author Steven Luu Node class that will be used for circularLinkedListGame.
 *         Has two nodes for next and previous as well as the data which is an
 *         integer.
 */
public class Node {
	private Node next; // The next node to the current one.
	private Node prev; // The previous node to the current one.
	private int data; // Integer data is stored which details when it was created. Ex. 7 denotes it
						// was the 7th node created.

	/**
	 * Constructs a node and places an integer into the data field.
	 * 
	 * @param d
	 *            the data that the node will hold, an integer.
	 */
	public Node(int d) {
		data = d;
		next = null;
	}

	public Node getNext() {
		return next;
	}

	public Node getPrev() {
		return prev;
	}

	public int getData() {
		return data;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

}
