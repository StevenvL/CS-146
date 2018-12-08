package Project1;


import org.junit.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Steven Luu
 * Create a JUnit class to test songShuffler.
 */
public class songShufflerTest {
	private final String MY_OUTPUT = "src/Project1/LuuStevenPlaylist.txt"; //Output from songShuffler
	private final String TEST_FILE = "src/Project1/Target2.txt";//Teacher given target to compare to.
	
	private songShuffler songShuffler; // songShuffler is created so it can be tested with JUnit.
	
	/**
	 * Set up songShuffler so that it can be used.
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException {
		songShuffler = new songShuffler();
		
	}
	
	/**
	 * Reads from two given files and compares each line of text to each other.
	 * If it passes, JUnit will give a green bar and no errors.
	 * If it fails, JUnit will give a red bar and list where the first error has occured.
	 * @throws IOException
	 */
	@Test
	public void testSongShuffler() throws IOException {
		BufferedReader myInput = new BufferedReader(new FileReader(MY_OUTPUT));
		BufferedReader testInput = new BufferedReader(new FileReader(TEST_FILE));
		
		String myLine = myInput.readLine();
		String testLine = testInput.readLine();
		while(testLine != null) {
			assertEquals(testLine,myLine);
			testLine = testInput.readLine();
			myLine = myInput.readLine();
		}
		myInput.close();
		testInput.close();
	}
	
	@Test
	public void test() throws IOException {
		testSongShuffler();
	}
}
