package sjsu.ho.cs146.project2;

import java.awt.Point;

/**
 * Represents a cell of a maze.
 * Contains variables useful for BFS and DFS searching
 * @author Steven Luu and Evan Ho
 *
 */
public class Cell {
	private Cell parent;

	private Point position;

	private String color;
	private int distance;

	private int discoveryTime;
	private int finishTime;

	public Cell(int row, int column) {
		position = new Point(row, column);
		this.parent = null;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setDiscoveryTime(int time) {
		this.discoveryTime = time;
	}

	public void setFinishTime(int time) {
		this.finishTime = time;
	}

	public String getColor() {
		return color;
	}

	public int getDistance() {
		return distance;
	}

	public Cell getParent() {
		return parent;
	}

	public int getDiscoveryTime() {
		return discoveryTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public int getX() {
		return (int) position.getX();
	}
	
	public int getY() {
		return (int) position.getY();
	}
}
