/*
 * comp2911 team * maze project
 * 
 * 
 */

import java.util.*;

public class BackTracker {
	private char[][] Maze;
	private int row;
	private int col;
	private int flag;
	
	public char[][] generateMaze(int row, int col) {
		
		initialMaze(row,col);
		LinkedList<int[]> unvisited = new LinkedList<int[]>();
		int[] start = new int[2];
		int[] current;
		char[] neighbors;
		char move;
		start[0] = 1;
		start[1] = 1;
		
		unvisited.add(start);
		current = unvisited.peek();

		while(!unvisited.isEmpty()) {
			
			neighbors = getNeighbor(current);
			
			if(flag != 0) {
				move = randomMove(neighbors);
				if(move == 'u') {
					
					Maze[current[0] - 1][current[1]] = ' ';
					Maze[current[0] - 2][current[1]] = ' ';
					current[0] = current[0] - 2;
				}
				else if(move == 'd') {
					Maze[current[0] + 1][current[1]] = ' ';
					Maze[current[0] + 2][current[1]] = ' ';
					current[0] = current[0] + 2;

				}
				else if(move == 'l') {
					Maze[current[0]][current[1] - 1] = ' ';
					Maze[current[0]][current[1] - 2] = ' ';
					current[1] = current[1] - 2;

				}
				else if(move == 'r') {
					Maze[current[0]][current[1] + 1] = ' ';
					Maze[current[0]][current[1] + 2] = ' ';
					current[1] = current[1] + 2;
				}
				int[] currCopy = new int[2];
				currCopy[0] = current[0];
				currCopy[1] = current[1];
				unvisited.add(currCopy);
			}
			else {
				current = unvisited.poll();
			}
			
		}
		Maze[0][1] = ' ';
		Maze[this.row - 1][this.col - 2] = ' ';
		//printMaze();
		
		
		
		return Maze;
	}
	
	
	private char randomMove(char[] neighbors) {
		Random rand = new Random();
		int randomNum = rand.nextInt(neighbors.length);
		return neighbors[randomNum];
	}
	
	private char[] getNeighbor(int[] current) {
		int row = current[0];
		int col = current[1];
		char[] neighbors = new char[4];
		int size = 0;
		flag = 0;
		for(int i = 0;i < 4;i++) {
			neighbors[i] = ' ';
		}
		if(row - 2 > 0 && Maze[row - 2][col] != ' ') {
			neighbors[0] ='u';
			size++;
		}
		if(row + 2 < this.row && Maze[row + 2][col] != ' ') {
			neighbors[1] = 'd';
			size++;
		}
		if(col - 2 > 0 && Maze[row][col - 2] != ' ') {
			neighbors[2] = 'l';
			size++;
		}
		if(col + 2 < this.col && Maze[row][col + 2] != ' ') {
			neighbors[3] = 'r';
			size++;
		}
		if(size != 0) {
			flag = 1;
		}
			char[] fixedNeighbors = new char[size];
			size--;
			for(int i = 0;i < 4;i++) {
				if(neighbors[i] != ' ') {
					fixedNeighbors[size--] = neighbors[i];
				}
			}
		
		
		return fixedNeighbors;
	}
		
	private void initialMaze(int row, int col) {
		Maze = new char[row + 1][col + 1];
		this.row = row;
		this.col = col;
		for(int i = 0;i < this.row;i++) {
			for(int j = 0;j < this.col;j++) {
//				Random rand = new Random();
//				int randomNum = rand.nextInt(2);
//				if(randomNum == 0) {
					Maze[i][j] = '*';
//				}
//				else {
//					Maze[i][j] = 't';
//				}
				
			}
		}
	}
	
	public void printMaze() {
		for(int i = 0;i < row;i++) {
			for(int j = 0;j < col;j++) {
				System.out.print(Maze[i][j]);
			}
			System.out.println();
		}
	}
	
}