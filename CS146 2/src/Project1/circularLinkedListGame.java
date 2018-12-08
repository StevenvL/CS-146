package Project1;

/**
 * @author Steven Luu CircularLinkedListGame is a game where there is 'x' amount
 *         of prisoners and every 'nth' one is elimiated until the last one is
 *         crowned victor. Victor gets freedom while the others are left to rot.
 */
public class circularLinkedListGame {
	public circularLinkedList circularLinkedList = new circularLinkedList(); // Creates a circularlinkedlist which are
																				// prisoners.

	/**
	 * Creates a linked list of amountOfNodes length.
	 * 
	 * @param amountOfNodes
	 *            is the length of the circular linked list.
	 */
	public void createList(int amountOfNodes) {
		for (int i = 1; i <= amountOfNodes; i++) {
			Node person = new Node(i);
			circularLinkedList.addNode(person);
		}
	}

	/**
	 * This method counts step amount of times until a prisoner is eliminated.
	 * 
	 * @param step
	 *            amount of steps until a person is eliminated.
	 * @return the prisoner that is given freedom.
	 */
	public int returnSurvivor(int step) {
		circularLinkedList.deleteNthNode(step);
		return circularLinkedList.getHead().getData();
	}

	/**
	 * Resets the circular linked list to an empty linked list.
	 */
	public void resetGame() {
		circularLinkedList.resetList();
	}
}
