package Project1;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Steven Luu
 * Created a JUnit class to test circularLinkedList.
 */
public class circularLinkedListTest {
	private circularLinkedList list; //Created a circularLinkedList object named 'list' for test cases.

	@Before
	public void setUp() {
		list = new circularLinkedList();
	}

	@Test
	public void testCircularLinkedList() {
		//Given Test Cases
		
		
		assertTrue(list.isEmpty());		//Checks to see if empty list is empty
		assertEquals(list.getSize(), 0);//Checks empty list to ensure size is 0

		Node testNode = new Node(1); //Create a new node. add to list, and check the size is 1
		list.addNode(testNode);
		int testSize = 1;
		assertFalse(list.isEmpty());
		assertEquals(testSize, list.getSize());

		Node testNode2 = new Node(2); //Create a new node, add to list, and check the size is 2
		list.addNode(testNode2);
		testSize++;
		assertFalse(list.isEmpty());
		assertEquals(testSize, list.getSize());
		
		//Personal Test Cases
		
		list.resetList();			//Reset list and ensure it is empty.
		assertTrue(list.isEmpty());
		
		
		list.addNode(testNode); 	//Insert 2 nodes into a list. Delete the first node.
		list.addNode(testNode2);
		assertEquals(list.getTail(),testNode2);	//Make sure addNode is correct and labels last added node as tail.
		list.deleteNode(testNode);
		testSize = 1;
		assertEquals(list.getHead(),testNode2);	//Make sure delete is function and that head is now testNode2.
		assertEquals(list.getTail(),testNode2);	//Make sure delete is fucntion and that tail is now testNode2.
		assertEquals(testSize, list.getSize());	//Check size is equal to 1. 
	}

	@Test
	public void test() {
		testCircularLinkedList();
	}
}
