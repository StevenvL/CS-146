package sjsu.ho.cs146.project2;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a maze that is implemented as a graph data structure.
 * Will initialize a 2-D array of cells to represent a maze and a 2-D array of chars to represent it visually. 
 * 
 * @author Steven Luu & Evan Ho
 *
 */
public class Maze {
	private int rows;
	private int columns;

	private Cell[][] mazeMatrix;
	private LinkedHashMap<Cell, LinkedList<Cell>> adjList;
	private LinkedHashMap<Cell, LinkedList<Cell>> neighbors;

	private char[][] perfectMaze;
	private char[][] BFSSolution;
	private char[][] BFSSolutionPath;
	private char[][] DFSSolution;
	private char[][] DFSSolutionPath;

	/**
	 * The sole constructor will initialize the maze with the correct row and columnds.
	 * Will create various printable mazes to represent a perfect maze, bfs solution and path, and dfs solution and dfs path.
	 * 
	 * @param rows is the size of the maze represented by the number of rows
	 * @param columns is the size of the maze represented by the number of columns
	 */
	public Maze(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		initMaze(rows, columns);
		perfectMaze = initPrintableMazeMatrix(rows, columns);
		BFSSolution = initPrintableMazeMatrix(rows, columns);
		BFSSolutionPath = initPrintableMazeMatrix(rows, columns);
		DFSSolution = initPrintableMazeMatrix(rows, columns);
		DFSSolutionPath = initPrintableMazeMatrix(rows, columns);
	}

	/**
	 * Initializes a 2-D array of cells along with the reflective neighborslist and adjlist.
	 * 
	 * @param rows is the size of the maze represented by the number rows
	 * @param columns is the size of the maze represented by the number of columns
	 */
	public void initMaze(int rows, int columns) {
		mazeMatrix = new Cell[rows][columns];
		neighbors = new LinkedHashMap<Cell, LinkedList<Cell>>();
		adjList = new LinkedHashMap<Cell, LinkedList<Cell>>();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				mazeMatrix[i][j] = new Cell(i, j);
				neighbors.put(mazeMatrix[i][j], new LinkedList<Cell>());
				adjList.put(mazeMatrix[i][j], new LinkedList<Cell>());
			}
		}
		fillOutNeighborList();
	}

	public void resetAdjListAndNeighbors() {
		neighbors.clear();
		adjList.clear();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				mazeMatrix[i][j].setParent(null);
				mazeMatrix[i][j].setDistance(0);
				mazeMatrix[i][j].setDiscoveryTime(0);
				mazeMatrix[i][j].setFinishTime(0);
				mazeMatrix[i][j].setColor(null);
				neighbors.put(mazeMatrix[i][j], new LinkedList<Cell>());
				adjList.put(mazeMatrix[i][j], new LinkedList<Cell>());
			}
		}
		fillOutNeighborList();
	}

	/**
	 * Initializes a visual representation of the maze created via ASCII values such as +, - , |, and ' '.
	 * 
	 * @param rows is the amount of rows the maze has.
	 * @param columns is the amount of columns the maze has.
	 * @return a printable representation of the maze created.
	 */
	public char[][] initPrintableMazeMatrix(int rows, int columns) {
		rows = (rows * 2) + 1;			//Rows is updated to a size that is accurate for a 2-D character array.
		columns = (columns * 2) + 1;	//Columns is updated to a size that is accurate for a 2-D character array.
		char[][] printable = new char[rows][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (i % 2 == 0 && j % 2 == 0)
					printable[i][j] = '+';
				else if (i % 2 != 0 && j % 2 == 0)
					printable[i][j] = '|';
				else if (i % 2 == 0 && j % 2 != 0) {
					if (!(i == 0 && j == 1) && !(i == rows - 1 && j == columns - 2))
						printable[i][j] = '-';
					else
						printable[i][j] = ' ';
				} else
					printable[i][j] = ' ';
			}
		}
		return printable;
	}

	/**
	 * Deletes the current cell from every linkedlist in the neighbors list. Used so that it will not break walls to cells that 
	 * have already been broken down.
	 * 
	 * @param cell is the current cell
	 */
	public void deleteEveryCellFromNeighborList(Cell cell) {
		for (Map.Entry<Cell, LinkedList<Cell>> entry : neighbors.entrySet()) {
			LinkedList<Cell> key = entry.getValue();
			key.remove(cell);
		}
	}

	/**
	 * Breaks down the wall between two cells in software. Software meaning that cell1 is the parent of cell2 now.
	 * Calls deleteEveryCellFromNeighborList so that this cell will never be broken into again to create a "perfect" maze.
	 * 
	 * @param cell1 is the current cell
	 * @param cell2 is a neighboring cell to the current cell
	 * @param printable is a 2-D character array that needs to be updated.
	 */
	public void breakWallBetween(Cell cell1, Cell cell2, char[][] printable) {
		if ((cell1.getX() == cell2.getX() && cell1.getY() == cell2.getY()) || Math.abs(cell1.getX() - cell2.getX()) > 1
				|| Math.abs(cell1.getY() - cell2.getY()) > 1) {
			System.out.println("NOT POSSIBLE");
			return;
		} else {
			adjList.get(cell1).add(cell2);

			deleteEveryCellFromNeighborList(cell1);
			deleteEveryCellFromNeighborList(cell2);

			cell2.setParent(cell1);

			int x1 = (2 * cell1.getX()) + 1;
			int y1 = (2 * cell1.getY()) + 1;

			int x2 = (2 * cell2.getX()) + 1;
			int y2 = (2 * cell2.getY()) + 1;

			if (y1 == y2) { // same row
				if (x1 < x2)
					printable[x1 + 1][y1] = ' ';
				else
					printable[x1 - 1][y1] = ' ';
			}
			if (x1 == x2) { // same column
				if (y1 < y2)
					printable[x1][y1 + 1] = ' ';
				else
					printable[x1][y1 - 1] = ' ';
			}
		}
	}
	
	/**
	 * Breaks down the wall between two cells visually. The "wall" i.e. '|' or '-' is removed between the two cells.
	 * 
	 * 
	 * @param cell1 is the current cell
	 * @param cell2 is a neighboring cell to the current cell
	 * @param printable is a 2-D character array that needs to be updated.
	 */
	public void breakWallBetweenPrintableOnly(Cell cell1, Cell cell2, char[][] printable) {
		if ((cell1.getX() == cell2.getX() && cell1.getY() == cell2.getY()) || Math.abs(cell1.getX() - cell2.getX()) > 1
				|| Math.abs(cell1.getY() - cell2.getY()) > 1) {
			System.out.println("NOT POSSIBLE");
			return;
		} else {
			int x1 = (2 * cell1.getX()) + 1;
			int y1 = (2 * cell1.getY()) + 1;

			int x2 = (2 * cell2.getX()) + 1;
			int y2 = (2 * cell2.getY()) + 1;

			if (y1 == y2) { // same row
				if (x1 < x2)
					printable[x1 + 1][y1] = ' ';
				else
					printable[x1 - 1][y1] = ' ';
			}
			if (x1 == x2) { // same column
				if (y1 < y2)
					printable[x1][y1 + 1] = ' ';
				else
					printable[x1][y1 - 1] = ' ';
			}
		}
	}

	public boolean isWallBroken(Cell c1, Cell c2) {
		return adjList.get(c1).contains(c2);
	}

	public Cell getCell(int row, int column) {
		if (row < 0 || column < 0 || row > getRows() - 1 || column > getColumns() - 1)
			return null;
		return mazeMatrix[row][column];
	}

	public LinkedList<Cell> getBrokenWalls(Cell cell) {
		return adjList.get(cell);
	}

	public Cell[] getIntactWalls(Cell cell) {
		Cell[] tempStorage = new Cell[neighbors.get(cell).size()];
		for (int i = 0; i < tempStorage.length; i++) {
			tempStorage[i] = neighbors.get(cell).get(i);
		}
		return tempStorage;
	}

	public void printNeighborsList() {
		for (Map.Entry<Cell, LinkedList<Cell>> entry : neighbors.entrySet()) {
			Cell cell = entry.getKey();
			LinkedList<Cell> list = entry.getValue();
			System.out.print(cell.getX() + ", " + cell.getY() + " -> ");
			for (Cell neighbors : list) {
				System.out.print(neighbors.getX() + ", " + neighbors.getY() + " -> ");
			}
			System.out.println();
		}
	}

	/**
	 * Initializes the neighbors list containing a mapping from cell to its neighbors
	 */
	public void fillOutNeighborList() {
		// if currentCell +- 1 is a cell then add it to the neighbors list.
		// neighboringCell is a neighboring cell to currentCell that has walls still intact.
		Cell currentCell;
		Cell neighboringCell;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				currentCell = getCell(i, j);

				neighboringCell = getCell(i + 1, j); // cell below
				if (neighboringCell != null) {
					if (adjList.containsKey(neighboringCell) && !neighbors.get(currentCell).contains(neighboringCell)) {
						neighbors.get(currentCell).add(neighboringCell);
					}
				}

				neighboringCell = getCell(i, j + 1); // cell to the right
				if (neighboringCell != null) {
					if (adjList.containsKey(neighboringCell) && !neighbors.get(currentCell).contains(neighboringCell)) {
						neighbors.get(currentCell).add(neighboringCell);
					}
				}

				neighboringCell = getCell(i - 1, j); // cell above
				if (neighboringCell != null) {
					if (adjList.containsKey(neighboringCell) && !neighbors.get(currentCell).contains(neighboringCell)) {
						neighbors.get(currentCell).add(neighboringCell);
					}
				}

				neighboringCell = getCell(i, j - 1); // cell to the left
				if (neighboringCell != null) {
					if (adjList.containsKey(neighboringCell) && !neighbors.get(currentCell).contains(neighboringCell)) {
						neighbors.get(currentCell).add(neighboringCell);
					}
				}
			}
		}
	}

	public void getNeighborLinkedListAtIndex(int row, int column) {
		Cell[] tempHolder = neighbors.get(getCell(row, column))
				.toArray(new Cell[neighbors.get(getCell(row, column)).size()]);
		for (int i = 0; i < tempHolder.length; i++) {
			System.out.print("Cells in linkedList of " + row + ", " + column);
		}
	}

	/**
	 * Inserts only specific characters as row and column will be automatically converted to insert in the center of a cell.
	 * 
	 * @param row is the x position to be inserted at
	 * @param column is the y position to be inserted at
	 * @param insertThis is a character that is either a  '#', ' ', or 'a number'
	 * @param printable is the printable matrix that the character is inserted in.
	 */
	public void insertInPrintableMatrix(int row, int column, char insertThis, char[][] printable) {
		row = (row * 2) + 1;
		column = (column * 2) + 1;
		printable[row][column] = insertThis;
	}

	/**
	 * Allows the insertion of any character into a specific spot as rows and columns are not converted.
	 * 
	 * @param row is the x position to be inserted at
	 * @param column is the y position to be inserted at
	 * @param insertThis is a character that is either a  '+', '-', '|', '#', ' ', or 'a number'
	 * @param printable is the printable matrix that the character is inserted in.
	 */
	public void insertInPrintableMatrixDirect(int row, int column, char insertThis, char[][] printable) {
		printable[row][column] = insertThis;
	}

	public void printMazeToConsole(char[][] printable) {
		for (int i = 0; i < printable.length; i++) {
			for (int j = 0; j < printable[0].length; j++) {
				System.out.print(printable[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printAdjList() {
		for (Map.Entry<Cell, LinkedList<Cell>> entry : adjList.entrySet()) {
			Cell cell = entry.getKey();
			LinkedList<Cell> list = entry.getValue();
			System.out.print(cell.getX() + ", " + cell.getY() + " -> ");
			for (Cell adjCell : list) {
				System.out.print(adjCell.getX() + ", " + adjCell.getY() + " -> ");
			}
			System.out.println();
		}
	}

	public LinkedList<Cell> getAdjListOf(Cell cell) {
		return adjList.get(cell);
	}

	public Cell[][] getMazeMatrix() {
		return mazeMatrix;
	}

	public char[][] getPerfectMazePrintable() {
		return perfectMaze;
	}

	public char[][] getBFSSolutionPrintable() {
		return BFSSolution;
	}

	public char[][] getBFSSolutionPathPrintable() {
		return BFSSolutionPath;
	}

	public char[][] getDFSSolutionPrintable() {
		return DFSSolution;
	}

	public char[][] getDFSSolutionPathPrintable() {
		return DFSSolutionPath;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	/**
	 * Clears text but leaves broken down walls visually.
	 * 
	 * @param printable is the matrix to be edited.
	 */
	public void clearText(char[][] printable) { 
		int rowsPrintable = printable[0].length;
		int columnsPrintable = printable.length;

		for (int i = 0; i < rowsPrintable; i++) {
			for (int j = 0; j < columnsPrintable; j++) {

				if (i % 2 == 0 && j % 2 == 0) // Insert +
					printable[i][j] = '+';

				else if (i % 2 != 0 && j % 2 == 0) // Insert |
					if (printable[i][j] != '|')
						printable[i][j] = ' '; // IF SOMETHING THERE REPLACE IT WITH EMPTY
					else
						printable[i][j] = '|';

				else if (i % 2 == 0 && j % 2 != 0) { // Insert - or ' 'depending on row
					if (!(i == 0 && j == 1) && !(i == rowsPrintable - 1 && j == columnsPrintable - 2)) {
						if (printable[i][j] != '-')
							printable[i][j] = ' '; // IF SOMETHING THERE REPLACE IT WITH EMPTY
						else
							printable[i][j] = '-';
					} else if (printable[i][j] != ' ')
						printable[i][j] = ' '; // IF SOMETHING THERE REPLACE IT WITH EMPTY
					else
						printable[i][j] = ' ';
				}

				else {
					if (printable[i][j] != ' ')
						printable[i][j] = ' '; // IF SOMETHING THERE REPLACE IT WITH EMPTY
					else
						printable[i][j] = ' ';
				}
			}
		}
	}
}
