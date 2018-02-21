import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

public class AI {

	private int flag;
	private char[][] Maze;
	private int row;
	private int col;
	private Stack<int[]> visited;
	
	/**
	 * constructor for AI
	 * @param map the copy of the map
	 * @param row row number
	 * @param col column number
	 */
	public AI(char[][] map,int row,int col) {
		this.Maze = new char[map.length][map[0].length];//map;
		copyMap(map);
		this.row = row;
		this.col = col;
		this.visited = new Stack<int[]>();
	}
	
	/**
	 * Using backtracking algorithm to generate the next position for ai player
	 * @param p2 ai player
	 * @return the next position of the ai player
	 */
	public Player robotMove(Player p2) {
		int[] current = new int[2];
		char[] neighbors;
		char move;
		
		current[0] = p2.yCoord();
		current[1] = p2.xCoord();

		Maze[current[0]][current[1]] = '*';
		neighbors = getNeighbor(current);
				
		if(flag != 0) {
			move = randomMove(neighbors);
			if(move == 'u') {
				current[0]--;
			}
			else if(move == 'd') {			
				current[0]++;
			}
			else if(move == 'l') {
				current[1]--;
			}
			else if(move == 'r') {
				current[1]++;
			}
			visited.add(current);
		}
		else {			
			current = visited.pop();			
		}
				
		int oldX = p2.xCoord();
		int oldY = p2.yCoord();
		
		p2.updateXCoord(current[1]);
		p2.updateYCoord(current[0]);
		
		//also temp
		if ((oldX - current[1]) == 1) {
			p2.changeOrientation(2);
		} else if ((oldX - current[1]) == -1) {
			p2.changeOrientation(3);
		} else if ((oldY - current[0]) == 1) {
			p2.changeOrientation(1);
		} else if ((oldY - current[0]) == -1) {
			p2.changeOrientation(0);
		}

		return p2;
	}
	
	
	/**
	 * reset the ai
	 * @param map copy map
	 */
	public void resetAI(char[][] map) {
		copyMap(map);
		visited.clear();
		//printMaze();
	}
	
	/**
	 * copy map from the original map
	 * @param map original map
	 */
	private void copyMap(char[][] map) {
		for (int y = 0; y < map.length ; y++) {
			for (int x = 0; x < map[y].length; x++) {
				Maze[y][x] = map[y][x];
			}
		}
	}
	

	private void printMaze() {
		for(int i = 0;i < row;i++) {
			for(int j = 0;j < col;j++) {
				System.out.print(Maze[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * generate a random move
	 * @param neighbors available neighbor positions
	 * @return a neighbor position
	 */
	private char randomMove(char[] neighbors) {
		Random rand = new Random();
		int randomNum = rand.nextInt(neighbors.length);
		return neighbors[randomNum];
	}
	
	/**
	 * get all the available neighbor
	 * @param current position
	 * @return an array of all available neighbor
	 */
	private char[] getNeighbor(int[] current) {
		int row = current[0];
		int col = current[1];
		char[] neighbors = new char[4];
		int size = 0;
		flag = 0;
		for (int i = 0; i < 4; i++) {
			neighbors[i] = ' ';
		}
		if (row - 1 > 0 && Maze[row - 1][col] == ' ') {
			neighbors[0] = 'u';
			size++;
		}
		if (row + 1 < this.row && Maze[row + 1][col] == ' ') {
			neighbors[1] = 'd';
			size++;
		}
		if (col - 1 > 0 && Maze[row][col - 1] == ' ') {
			neighbors[2] = 'l';
			size++;
		}
		if (col + 1 < this.col && Maze[row][col + 1] == ' ') {
			neighbors[3] = 'r';
			size++;
		}
		if (size != 0) {
			flag = 1;
		}
		char[] fixedNeighbors = new char[size];
		size--;
		for (int i = 0; i < 4; i++) {
			if (neighbors[i] != ' ') {
				fixedNeighbors[size--] = neighbors[i];
			}
		}

		return fixedNeighbors;
	}
	
	
	
	
	
		/**
		 * Find the shortest path for given start and goal
		 * @param start
		 * @param goal
		 * @return a HashMap of int[]s that connect start and goal (e.g. similar to a link list)
		 */
		public LinkedList<Position> doFindPath(Position start, Position goal) {
			HashMap<Position, Integer> gcosts = new HashMap<>();
			HashMap<Position, Integer> fcosts = new HashMap<>();
			HashMap<Position,Position> parents = new HashMap<>();
			CostComparator comparator = new CostComparator(fcosts, goal);
			PriorityQueue<Position> open = new PriorityQueue<>(100, comparator);
			ArrayList<Position> closed = new ArrayList<>();
			open.add(start);
			gcosts.put(start, 0);
			fcosts.put(start, heuristic(start,goal));
			
			
			while (open.size() != 0) {
				Position current = open.remove();
				Position newCurrent = new Position(current.row,current.col);
				
				closed.add(newCurrent);
				
//				for(Position temp : closed) {
//					System.out.println("closed set before " + temp[0] +" " + temp[1]);
//				}

				if (newCurrent.equals(goal)) {
					// found path to goal
					System.out.println("FOUND GOAL!!!!!!");
					return buildPath (parents,start,goal);
				}
				
				int[] temp = new int[2];
				temp[0] = newCurrent.row;
				temp[1] = newCurrent.col;
				char[] neighbors = getNeighbor(temp);

				for (char tempN : neighbors) {
					Position neighbor = new Position(0,0);
					
					if(tempN == 'u') {
						neighbor.row = newCurrent.row - 1;
						neighbor.col = newCurrent.col;
					}
					if(tempN == 'd') {
						neighbor.row = newCurrent.row + 1;
						neighbor.col = newCurrent.col;
					}
					if(tempN == 'r') {
						neighbor.row = newCurrent.row;
						neighbor.col = newCurrent.col + 1;
					}
					if(tempN == 'l') {
						neighbor.row = newCurrent.row;
						neighbor.col = newCurrent.col - 1;
					}
					
					int newGCost = gcosts.get(current) + 1;
					int newCost = newGCost + heuristic(neighbor, goal);
					
					if (fcosts.get(neighbor) == null) {
						fcosts.put(neighbor, newCost);
						gcosts.put(neighbor, newGCost);
					}
					

					int oldCost = fcosts.get(neighbor);
					// found a cheaper path, update neighbor's cost
					
					if (newCost < oldCost
						&& (closed.contains(neighbor) || open.contains(neighbor))) {
//						System.out.println("Reset cost: new = " + newCost + ", old = " + oldCost);
//						System.out.println("Reset parent: Parent of " + neighbor +  " = " + current);
						fcosts.put(neighbor, newCost);
						gcosts.put(neighbor, newGCost);
						parents.put(neighbor, current);
					}
					//System.out.println("test " + neighbor[0] + " " +  neighbor[1] + " " + current[0] + " " + current[1]);

//					for( Position temp1 : closed) {
//						System.out.println("closed set after " + temp1[0] +" " + temp1[1]);
//					}
					if (!open.contains(neighbor) && !closed.contains(neighbor)) {
						open.add(neighbor);
						parents.put(neighbor, current);
						//System.out.println("Add parent: Parent of " + neighbor[0] + " " +  neighbor[1] + " " + current[0] + " " + current[1]);
						//Position.printList("open ", open);
						//Position.printList("closed ", closed);	
					}
//					else {
//						System.out.println(open.size() + " " + closed.size()+" why " + neighbor[0] + " " +  neighbor[1] + " " + current[0] + " " + current[1]);
//
//					}
					
				}
			}
			return null;
		}
		
		/**
		 * Builds a path for the given start and goal function by looking for connected int[]s in the given HashMap
		 * @param parents contains int[] that connected to each other
		 * @param start
		 * @param goal
		 * @return path
		 */
		private LinkedList<Position> buildPath (HashMap<Position, Position> parents, Position start, Position goal) {
			//System.out.println("buildPath from " + start.toString() + " to " + goal.toString());
			
			LinkedList<Position> path = new LinkedList<Position>();
			Position target = goal;
			
			// backward, push goal first and back to start
			while (target != null && !target.equals(start)) {
				path.add(target);
				//System.out.println(" path" + target[0] +" " + target[1]);
				target = parents.get(target);
				//System.out.println(" path" + target[0] +" " + target[1]);

			}
			//System.out.println("buildPath from " + start + " to " + goal + " =" + path);
			return path;
		}
		
		/**
		 * Compares 2 paths according to their costs. Heuristic is used to estimate cost to goal
		 *
		 */
		private class CostComparator implements Comparator<Position> {
			private HashMap<Position, Integer> costs;
			private Position goal;
			
			public CostComparator(HashMap<Position, Integer> costs, Position goal) {
				this.costs = costs;
				this.goal = goal;
			}
			
			@Override
			public int compare(Position pos1, Position pos2) {
				int costPos1 = costs.get(pos1);
				int costPos2 = costs.get(pos2);
				int fPos1 = costPos1 + heuristic(pos1, goal);
				int fPos2 = costPos2 + heuristic(pos2, goal);
				return fPos1 - fPos2;
			}	
		}
		
		private int heuristic(Position pos,Position goal) {
			int xdis = Math.abs(goal.col - pos.col);
			int ydis = Math.abs(goal.row - pos.row);
			
			return Math.max(xdis, ydis);
		}
		
		
		
	
}

