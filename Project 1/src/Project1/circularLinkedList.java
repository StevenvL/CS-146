package Project1;

/**
 * @author Steven Luu circularLinkedList builds and has all the attributes of a
 *         doubly linked list with nodes. Attributes include : addNode,
 *         deleteNode, deleteNthNode, resetList, getHead, getSize, and isEmpty.
 */
public class circularLinkedList {
	private Node head; // The first node in a linked list.
	private Node tail; // The last node in a linked list.
	private int size; // The size of the linked list.

	/**
	 * Adds the passed in node to the list.
	 * 
	 * @param node
	 *            is passed in and this node will be added to the linked list.
	 */
	public void addNode(Node node) {
		if (head == null) {
			node.setNext(node);
			node.setPrev(node);
			head = node;
			tail = node;
			size++;
		} else {
			node.setPrev(tail);
			node.setNext(head);
			tail.setNext(node);
			tail = node;
			head.setPrev(tail);
			size++;
		}
	}

	/**
	 * Deletes the node that is passed in.
	 * 
	 * @param node
	 *            that will be deleted.
	 */
	public void deleteNode(Node node) {
		node.getPrev().setNext(node.getNext());
		node.getNext().setPrev(node.getPrev());
		if (head == node)
			head = node.getNext();
		if (tail == node)
			tail = node.getPrev();
		size--;
	}

	/**
	 * Iteratively goes through a linked list deleting a node every "step" or nth
	 * term.
	 * 
	 * @param step
	 *            is how often a node will be deleted.
	 */
	public void deleteNthNode(int step) {
		Node stepNode = head;
		int counter = 0;

		while (size > 1) {
			if (counter == step) {
				Node deleteMe = stepNode;
				deleteNode(deleteMe);
				stepNode = stepNode.getNext();
				counter = 0;
			} else {
				stepNode = stepNode.getNext();
				counter++;
			}
		}
	}

	/**
	 * Resets the linked list to an empty linked list.
	 */
	public void resetList() {
		head = null;
		tail = null;
		size = 0;
	}

	public Node getHead() {
		return head;
	}

	public Node getTail() {
		return tail;
	}

	public int getSize() {
		return size;
	}

	/**
	 * Checks if head is null, yes; assumes list is empty.
	 * 
	 * @return isEmpty is true if list is empty.
	 */
	public boolean isEmpty() {
		if (head == null)
			return true;
		return false;
	}

}
