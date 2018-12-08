package Project1;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Steven Luu Created a JUnit class to test circularLinkedListGame.
 */
public class circularLinkedListGameTest {
	private circularLinkedListGame circularLinkedListGame; // Created a circularLinkedListGame object to test.
	int amountOfPrisoners, step, expectedResult; // Integers used to test cases.
													// amountOfPrisoners is the starting length of a circularLinkedList
													// step is the amount of steps until a person is eliminated
													// expectedResult is the expected result given the scenario

	@Before
	public void setUp() {
		circularLinkedListGame = new circularLinkedListGame();
	}

	@Test
	public void testCircularLinkedListGame() {
		// Given Test Cases
		amountOfPrisoners = 6;
		step = 2;
		expectedResult = 1;
		circularLinkedListGame.createList(amountOfPrisoners);
		assertEquals(expectedResult, circularLinkedListGame.returnSurvivor(step));
		circularLinkedListGame.resetGame();

		amountOfPrisoners = 1;
		step = 9;
		expectedResult = 1;
		circularLinkedListGame.createList(amountOfPrisoners);
		assertEquals(expectedResult, circularLinkedListGame.returnSurvivor(step));
		circularLinkedListGame.resetGame();

		amountOfPrisoners = 7;
		step = 7;
		expectedResult = 4;
		circularLinkedListGame.createList(amountOfPrisoners);
		assertEquals(expectedResult, circularLinkedListGame.returnSurvivor(step));
		circularLinkedListGame.resetGame();

		amountOfPrisoners = 2;
		step = 8;
		expectedResult = 2;
		circularLinkedListGame.createList(amountOfPrisoners);
		assertEquals(expectedResult, circularLinkedListGame.returnSurvivor(step));
		circularLinkedListGame.resetGame();

		amountOfPrisoners = 5;
		step = 1;
		expectedResult = 3;
		circularLinkedListGame.createList(amountOfPrisoners);
		assertEquals(expectedResult, circularLinkedListGame.returnSurvivor(step));
		circularLinkedListGame.resetGame();

		// Personal Test Cases
		amountOfPrisoners = 1;
		step = 1;
		expectedResult = 1;
		circularLinkedListGame.createList(amountOfPrisoners);
		assertEquals(expectedResult, circularLinkedListGame.returnSurvivor(step));
		circularLinkedListGame.resetGame();

	}

	@Test
	public void test() {
		testCircularLinkedListGame();
	}
}
