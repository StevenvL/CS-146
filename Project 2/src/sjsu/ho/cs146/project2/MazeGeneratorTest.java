package sjsu.ho.cs146.project2;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing for MazeGenerator. Will compare .txt files one line at a time.
 * 
 * @author Steven Luu and Evan Ho
 *
 */
public class MazeGeneratorTest {

	private final String OUTPUT_FILE_NAME = "src/sjsu/ho/cs146/project2/Output/MyMaze";
	private final String TEST_FILE_NAME = "src/sjsu/ho/cs146/project2/JUnit Test Files/JUnitTest";

	private MazeGenerator mazeGenerator;

	@Test
	public void test() throws IOException {
		mazeGenerator = new MazeGenerator(0);
		int rows;
		int columns;

		rows = 4;
		columns = 4;
		mazeGenerator = new MazeGenerator(0);
		mazeGenerator.generateMazesAndWriteToFile(rows, columns);
		checkIfMazesAreEqual(rows, columns);

		rows = 6;
		columns = 6;
		mazeGenerator = new MazeGenerator(0);
		mazeGenerator.generateMazesAndWriteToFile(rows, columns);
		checkIfMazesAreEqual(rows, columns);

		rows = 8;
		columns = 8;
		mazeGenerator = new MazeGenerator(0);
		mazeGenerator.generateMazesAndWriteToFile(rows, columns);
		checkIfMazesAreEqual(rows, columns);

	}

	/**
	 * Inserts both output and test file into reader and checks every line.
	 * 
	 * @param rows number of rows of the maze being tested
	 * @param columns number of columns of the maze being tested
	 * @throws IOException throws exception if file cannot be made or read from.
	 */
	public void checkIfMazesAreEqual(int rows, int columns) throws IOException {
		String rowsString = Integer.toString(rows);
		String columnsString = Integer.toString(columns);

		BufferedReader outputReader = new BufferedReader(
				new FileReader(OUTPUT_FILE_NAME + rowsString + "x" + columnsString + ".txt"));
		BufferedReader testFileReader = new BufferedReader(
				new FileReader(TEST_FILE_NAME + rowsString + "x" + columnsString + ".txt"));

		String expectedLine = testFileReader.readLine();
		while (expectedLine != null) {
			String outputLine = outputReader.readLine();

			assertEquals(outputLine, expectedLine);

			expectedLine = testFileReader.readLine();
		}
	}
}
