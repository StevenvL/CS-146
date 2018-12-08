package sjsu.ho.cs146.project2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * MazeGenerator deals with creating a maze and methods to solve it via BFS,DFS, and insertingHashTags.
 * 
 * @author Steven Luu & Evan Ho
 *
 */
public class MazeGenerator {
	private Maze maze;
	private Random randomGenerator;

	private final String OUTPUT_FILE_NAME = "src/sjsu/ho/cs146/project2/Output/MyMaze";

	/**
	 * @param seed Specifies the seed that is passed in.
	 */
	public MazeGenerator(int seed) {
		randomGenerator = new Random(seed);
	}

	/**
	 * Creates a "perfect" maze based off the seed passed in the constructor. For this project we will compare it to seed = 0.
	 * 
	 * @param rows The amount of rows a maze will have.
	 * @param columns The amount of columns a maze will have.
	 */
	public void generatePerfectMaze(int rows, int columns) {
		maze = new Maze(rows, columns);

		Stack<Cell> cellStack = new Stack<>();
		int totalCells = rows * columns;
		Cell currentCell = maze.getCell(0, 0);
		int visitedCells = 1;

		while (visitedCells < totalCells) {

			Cell[] intactWalls = maze.getIntactWalls(currentCell);
			if (intactWalls.length > 0) {
				int chosenCellIndex = (int) (getRandom() * intactWalls.length);
				Cell chosenCell = intactWalls[chosenCellIndex];

				maze.breakWallBetween(currentCell, chosenCell, maze.getPerfectMazePrintable());
				maze.breakWallBetweenPrintableOnly(currentCell, chosenCell, maze.getBFSSolutionPrintable());
				maze.breakWallBetweenPrintableOnly(currentCell, chosenCell, maze.getDFSSolutionPrintable());
				maze.breakWallBetweenPrintableOnly(currentCell, chosenCell, maze.getBFSSolutionPathPrintable());
				maze.breakWallBetweenPrintableOnly(currentCell, chosenCell, maze.getDFSSolutionPathPrintable());

				cellStack.push(currentCell);
				currentCell = chosenCell;
				visitedCells++;
			} else {
				currentCell = cellStack.pop();
			}
		}
	}

	/**
	 * Uses BFS algorithm to solve the "perfect" maze.
	 * Updates each cell with a parent, color, and distance.
	 * Printable maze will reflect these changes with a number to indicate when it was visited.
	 */
	public void BFSSolve() {
		for (Cell[] row : maze.getMazeMatrix()) {
			for (Cell cell : row) {
				cell.setParent(null);
				cell.setColor("WHITE");
				cell.setDistance(Integer.MAX_VALUE);
			}
		}

		Queue<Cell> queue = new LinkedList<>();
		maze.getCell(0, 0).setDistance(0);
		queue.add(maze.getCell(0, 0));

		int visitOrder = 0;
		maze.insertInPrintableMatrix(0, 0, '0', maze.getBFSSolutionPrintable());

		while (queue.size() > 0) {
			Cell u = queue.remove();

			for (int i = 0; i < maze.getAdjListOf(u).size(); i++) {
				Cell v = getPriorityPositionOf(u, maze.getAdjListOf(u));
				if (v.getColor().equals("WHITE")) {
					v.setColor("GREY");
					v.setDistance(u.getDistance() + 1);
					visitOrder++;
					String visitOrderString = Integer.toString(visitOrder);
					char visitOrderChar = visitOrderString.charAt(visitOrderString.length() - 1);
					maze.insertInPrintableMatrix(v.getX(), v.getY(), visitOrderChar, maze.getBFSSolutionPrintable());
					v.setParent(u);
					queue.add(v);
					if (v.getX() == maze.getRows() - 1 && v.getY() == maze.getColumns() - 1) {
						return;
					}
				}
			}
			u.setColor("BLACK");
		}
	}

	/**
	 * @param u Current cell passed in.
	 * @param adjList is the neighbors list of the current cell. Neighborlist is which cell walls can be broken down.
	 * @return The cell that is returned is the one that should be choosen next for BFS or DFS.
	 */
	private Cell getPriorityPositionOf(Cell u, LinkedList<Cell> adjList) {
		if (adjList.size() == 1) {
			return adjList.get(0);
		} else {
			if (getCellBelow(u, adjList) != null && getCellBelow(u, adjList).getColor() == "WHITE") {
				return getCellBelow(u, adjList); // return bottom cell
			}
			if (getCellToRight(u, adjList) != null && getCellToRight(u, adjList).getColor() == "WHITE") {
				return getCellToRight(u, adjList); // return right cell
			}
			if (getCellAbove(u, adjList) != null && getCellAbove(u, adjList).getColor() == "WHITE") {
				return getCellAbove(u, adjList); // return top cell
			}
			if (getCellToLeft(u, adjList) != null && getCellToLeft(u, adjList).getColor() == "WHITE") {
				return getCellToLeft(u, adjList); // return left cell
			}
		}
		return null;
	}

	/**
	 * Returns the cell to the left if it is in the neighborlist of the cell, otherwise null.
	 * 
	 * @param u the current cell.
	 * @param adjList is the neighborlist of the current cell
	 * @return a cell that is to the left of the current cell in the neighborlist if any.
	 */
	private Cell getCellToLeft(Cell u, LinkedList<Cell> adjList) {
		for (Cell c : adjList) {
			if (c.getY() < u.getY())
				return c;
		}
		return null;
	}
	
	/**
	 * Returns the cell above if it is in the neighborlist of the cell, otherwise null.
	 * 
	 * @param u the current cell.
	 * @param adjList is the neighborlist of the current cell
	 * @return a cell that is above the current cell in the neighborlist if any.
	 */
	private Cell getCellAbove(Cell u, LinkedList<Cell> adjList) {
		for (Cell c : adjList) {
			if (c.getX() < u.getX())
				return c;
		}
		return null;
	}
	
	/**
	 * Returns the cell to the right if it is in the neighborlist of the cell, otherwise null.
	 * 
	 * @param u the current cell.
	 * @param adjList is the neighborlist of the current cell
	 * @return a cell that is to the right of the current cell in the neighborlist if any.
	 */
	private Cell getCellToRight(Cell u, LinkedList<Cell> adjList) {
		for (Cell c : adjList) {
			if (c.getY() > u.getY())
				return c;
		}
		return null;
	}
	
	/**
	 * Returns the cell below if it is in the neighborlist of the cell, otherwise null.
	 * 
	 * @param u the current cell.
	 * @param adjList is the neighborlist of the current cell
	 * @return a cell that is below the current cell in the neighborlist if any.
	 */
	private Cell getCellBelow(Cell u, LinkedList<Cell> adjList) {
		for (Cell c : adjList) {
			if (c.getX() > u.getX())
				return c;
		}
		return null;
	}

	/**
	 * Uses DFS to solve a "perfect" maze
	 * Updates each cell with a parent, color, and distance.
	 * Printable maze will reflect these changes with a number to indicate when it was visited.
	 */
	public void DFSSolve() {
		for (Cell[] rows : maze.getMazeMatrix()) {
			for (Cell cell : rows) {
				cell.setParent(null);
				cell.setColor("WHITE");
				cell.setDistance(Integer.MAX_VALUE);
			}
		}

		Stack<Cell> cellStack = new Stack<>();
		cellStack.push(maze.getCell(0, 0));
		maze.insertInPrintableMatrix(0, 0, '0', maze.getDFSSolutionPrintable());

		int DFSVisitOrder = 0;
		while (cellStack.size() > 0) {
			Cell v = cellStack.pop();
			if (v.getColor().equals("WHITE")) {
				v.setColor("GREY");
				String timeString = Integer.toString(DFSVisitOrder);
				char timeChar = timeString.charAt(timeString.length() - 1);
				maze.insertInPrintableMatrix(v.getX(), v.getY(), timeChar, maze.getDFSSolutionPrintable());
				DFSVisitOrder++;
				if (v.getX() == maze.getRows() - 1 && v.getY() == maze.getColumns() - 1) {
					return;
				}
			}
			LinkedList<Cell> cellAdjList = maze.getAdjListOf(v);
			for (int i = 0; i < cellAdjList.size(); i++) {
				Cell adjCell = getPriorityPositionOf(v, cellAdjList);
				if (adjCell.getColor().equals("WHITE")) {
					adjCell.setParent(v);
					cellStack.push(adjCell);
				}
			}
		}
	}

	/**
	 * @return a double from the random generator with seed '0'.
	 */
	public double getRandom() {
		return randomGenerator.nextDouble();
	}

	/**
	 * Uses clearText(char[][] printable) to remove all text inside leaving just the perfect maze.
	 * After it is cleared it will insert '#' starting at the lastc cell and using .getParent() of each cell until it reaches (0,0)
	 * 
	 * @param printable is a 2-D array of characters
	 */
	public void insertHashtag(char[][] printable) {
		maze.clearText(printable);

		maze.insertInPrintableMatrix(0, 0, '#', printable); // Insert hashtag at 0,0
		Cell tempCell = maze.getCell(maze.getRows() - 1, maze.getColumns() - 1);
		maze.insertInPrintableMatrix(tempCell.getX(), tempCell.getY(), '#', printable); // Start at end of maze. use
																						// .getparent to get to 0,0

		while (!tempCell.equals(maze.getCell(0, 0))) {
			int printableXIndex = tempCell.getX() * 2 + 1;
			int printableYIndex = tempCell.getY() * 2 + 1;
			Cell parentCell = tempCell.getParent();
			maze.insertInPrintableMatrix(tempCell.getX(), tempCell.getY(), '#', printable); // Inserts # where the
																							// original number was.

			boolean insertedBetween = false;
			while (!insertedBetween) {
				if (tempCell.getX() < parentCell.getX()) { // CurrentCell is above ParentCell
					maze.insertInPrintableMatrixDirect(printableXIndex + 1, printableYIndex, '#', printable);
					insertedBetween = true;
				}
				if (tempCell.getX() > parentCell.getX()) { // CurrentCell is below ParentCell
					maze.insertInPrintableMatrixDirect(printableXIndex - 1, printableYIndex, '#', printable);
					insertedBetween = true;
				}
				if (tempCell.getY() < parentCell.getY()) { // CurrentCell is to the left of ParentCell
					maze.insertInPrintableMatrixDirect(printableXIndex, printableYIndex + 1, '#', printable);
					insertedBetween = true;
				}
				if (tempCell.getY() > parentCell.getY()) { // CurrentCell is to the right of ParentCell
					maze.insertInPrintableMatrixDirect(printableXIndex, printableYIndex - 1, '#', printable);
					insertedBetween = true;
				}
			}
			tempCell = parentCell; // Iterate
		}
	}

	/**
	 * Will delete any file with the same maze size so that the next time it prints to a file it will be brand new.
	 * 
	 * @param mazeSize size of printable maze
	 */
	public void deleteExistingMazeFile(int mazeSize) {
		String mazeSizeName = Integer.toString(mazeSize);
		File file = new File(OUTPUT_FILE_NAME + mazeSizeName + "x" + mazeSizeName + ".txt");
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * Prints out the maze to a file and the file name is connected to the size of the maze.
	 * I.E. 4x4 maze is named 'OUTPUT_FILE_NAME4x4.txt' which turns into 'MyMaze4x4.txt'.
	 * 
	 * @param printable 2-D array to be printed out
	 * @param mazeSize	size of maze to be included in the file name
	 * @throws IOException throws an exception if file cannot be created or found when written to
	 */
	public void writeMazeToFile(char[][] printable, int mazeSize) throws IOException {
		String mazeSizeName = Integer.toString(mazeSize);

		BufferedWriter writer = new BufferedWriter(
				new FileWriter(OUTPUT_FILE_NAME + mazeSizeName + "x" + mazeSizeName + ".txt", true));
		for (char[] row : printable) {
			for (char cell : row) {
				writer.write(cell);
			}
			writer.newLine();
		}
		writer.newLine();
		writer.close();
	}

	/**
	 * Clears the text of the existing maze that is passed in.
	 * 
	 * @param printable is a printable maze that will reset the text of the maze.
	 */
	public void resetMaze(char[][] printable) {
		maze.clearText(printable);
	}

	/**
	 * Used for junit testing, prints out all the needed info to compare to the given junit file.
	 * 
	 * @param rows is the row size of the maze
	 * @param columns is the column size of the maze
	 * @throws IOException will throw an exception if the file cannot be printed to or found.
	 */
	public void generateMazesAndWriteToFile(int rows, int columns) throws IOException {
		deleteExistingMazeFile(rows);
		System.out.println("MAZE:");
		generatePerfectMaze(rows, columns);
		System.out.println(maze.getRows() + " x " + maze.getColumns());
		maze.printMazeToConsole(maze.getPerfectMazePrintable());
		writeMazeToFile(maze.getPerfectMazePrintable(), maze.getRows());

		System.out.println("BFS:");
		BFSSolve();
		maze.printMazeToConsole(maze.getBFSSolutionPrintable());
		writeMazeToFile(maze.getBFSSolutionPrintable(), maze.getRows());

		insertHashtag(maze.getBFSSolutionPathPrintable());
		maze.printMazeToConsole(maze.getBFSSolutionPathPrintable());
		writeMazeToFile(maze.getBFSSolutionPathPrintable(), maze.getRows());

		System.out.println("DFS:");
		DFSSolve();
		maze.printMazeToConsole(maze.getDFSSolutionPrintable());
		writeMazeToFile(maze.getDFSSolutionPrintable(), maze.getRows());

		insertHashtag(maze.getDFSSolutionPathPrintable());
		maze.printMazeToConsole(maze.getDFSSolutionPathPrintable());
		writeMazeToFile(maze.getDFSSolutionPathPrintable(), maze.getRows());

		maze.resetAdjListAndNeighbors();
		System.out.println("======================");
		System.out.println("  Program Completed!  ");
		System.out.println("======================");
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		MazeGenerator mazeGenerator = new MazeGenerator(0);
		Scanner sc = new Scanner(System.in);
		
		System.out.println("How many rows in the maze?");
		int rows = sc.nextInt();
		
		System.out.println("How many columns in the maze?");
		int columns = sc.nextInt();
		
		mazeGenerator.generateMazesAndWriteToFile(rows, columns);
	}
}
