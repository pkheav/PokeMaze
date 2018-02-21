import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.Timer;

public class Maze {
	private int height;
	private int width;
	private MazeTile[][] map;
	private char[][] charMap;
	private Player p;
	private Player p2;
	private boolean multiplayer;
	private int gameMode;
	private AI ai;
	private Timer robotTimer;
	private boolean aiEnabled;
	private boolean vision;
	private Player winner;
	private int mazeType;
	
	/**
	 * Constructs the back end of the maze program
	 * 
	 * @param gridSize	Size of the maze
	 * @param mode		Current game mode
	 * @param mp		Multiplayer enabled or disabled
	 * @param vision	Limited visibility
	 */
	public Maze(int gridSize, int mode, boolean mp, boolean vision) {
		this.height = gridSize + 1;
		this.width = gridSize + 1;
		this.aiEnabled = false;
		this.gameMode = mode;
		this.multiplayer = mp;
		this.vision = vision;
		createMaze();
		setupGame();
	}
	
	/**
	 * 
	 * Sets up the game depending on the game mode
	 * 
	 */
	private void setupGame() {
		p = new Player();
		p2 = new Player(2, width, height);
		if (gameMode == 0) {
			//NORMAL
		} else if (gameMode == 1) {
			//FOG
			addFog();
		} else if (gameMode == 2) {
			//AI
			aiEnabled = true;
			setupAI();
		} else if (gameMode == 3) {
			//POKEBALL COLLECT
		}
		
		if (!aiEnabled && !multiplayer) {
			p2 = null;
		}
		
		if (vision) {
			addFog();
		}
	}
	
	/**
	 * 
	 * Sets up the AI
	 * 
	 */
	private void setupAI() {
		ai = new AI(charMap,height,width);
		robotTimer = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (gameMode == 1) {
					addFog();
				}
				p2 = ai.robotMove(p2);
			}
		});
		robotTimer.setRepeats(true);
	}
	
	/**
	 * Stops AI movement
	 */
	public void stopAi() {
		robotTimer.stop();
	}
	
	/**
	 * Resumes AI movement
	 */
	public void startAi() {
		robotTimer.start();
	}
	
	/**
	 * Restarts AI movement
	 */
	public void resetAi() {
		ai.resetAI(charMap);
		robotTimer.start();
	}
	
	/**
	 * Modifies map for limited visibility
	 */
	private void addFog () {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				
				if(Math.abs((y - p.yCoord())) <= 3 && Math.abs((x - p.xCoord())) <= 3) {
					map[y][x].onMap("tile");
					continue;
				} else {
					map[y][x].onMap("fog");
				}
				
				if (gameMode == 2 || multiplayer) {
					if(Math.abs((y - p2.yCoord())) <= 3 && Math.abs((x - p2.xCoord())) <= 3) {
						map[y][x].onMap("tile");
					} else {
						map[y][x].onMap("fog");
					}
				} 
			}
		}
	}
	
	/**
	 * Generates a new maze
	 */
	private void createMaze () {
		Random rand = new Random();
		this.mazeType = rand.nextInt(3);
		BackTracker newMap = new BackTracker();
		charMap = newMap.generateMaze(width, height);
		map = new MazeTile [width][height];
		
		// char[][] -> MazeTile[][]
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				map[y][x] = new MazeTile(charMap[y][x], mazeType);
			}
		}
	}
	
	/**
	 * Creates a new maze game
	 */
	public void newMaze() {
		setupGame();
		createMaze();
		
		if (gameMode == 1) {
			addFog();
		}
		
		if (vision) {
			addFog();
		}
	}
	
	/**
	 * Restarts current maze game
	 */
	public void restartMaze() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if (map[y][x].getType().equals("floor")) {
					map[y][x].onMap("floor");
				}
			}
		}
		setupGame();
	}
	
	/**
	 * Checks if the game is finished
	 * 
	 * @return	Whether game is finished
	 */
	public boolean mazeFinished () {

		if (p.xCoord() == (width - 2) && p.yCoord() == (height - 2)) {
			winner = p;
			return true;
		}

		if(multiplayer || aiEnabled) {
			if(p2.xCoord() == 1 && p2.yCoord() == 1) {
				winner = p2;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Handles keyboard input
	 * @param key	The key pressed
	 */
	public void handleKeyPressed (int key) {
		if (key == KeyEvent.VK_H && !multiplayer) {
			Position start = new Position(p.yCoord(),p.xCoord());
			Position goal = new Position(height -1,width - 2);
			
			AI pathHint = new AI(charMap,height,width);
			
			LinkedList<Position> path = pathHint.doFindPath(start,goal);
			int count = 0;
			if(path.size() > 0) {
				ListIterator<Position> li = path.listIterator(path.size());
				while(li.hasPrevious() && count < 5) {
				    Position prev = li.previous();
					map[prev.row][prev.col].onMap("hint");
					count++;
				}
			}
			
			return;
		}
		
		if (isLegal(key)) {
			if (key <= 40 && key >= 37) {
				p.processMove(key, true);
			} else if (!aiEnabled) {
				p2.processMove(key, true);
			}
		} else {
			if (key <= 40 && key >= 37) {
				p.processMove(key, false);
			} else if (!aiEnabled) {
				p2.processMove(key, false);
			}
		}
		if (vision) addFog();
	}
	
	/**
	 * Checks whether the move was legal or not
	 * @param key	The key indicating movement direction
	 * @return		Legal or not
	 */
	private boolean isLegal(int key) {
		boolean legal = false;
		switch (key) {
			case KeyEvent.VK_UP:
				if ((p.yCoord() > 0) && (map[p.yCoord() - 1][p.xCoord()].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_DOWN:
				if ((p.yCoord() < width - 1) && (map[p.yCoord() + 1][p.xCoord()].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_LEFT:
				if ((p.xCoord() > 0) && (map[p.yCoord()][p.xCoord() - 1].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if ((p.xCoord() < height - 1) && (map[p.yCoord()][p.xCoord() + 1].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_W:
				if ((p2.yCoord() > 0) && (map[p2.yCoord() - 1][p2.xCoord()].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_S:
				if ((p2.yCoord() < width - 1) && (map[p2.yCoord() + 1][p2.xCoord()].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_A:
				if ((p2.xCoord() > 0) && (map[p2.yCoord()][p2.xCoord() - 1].getType().equals("floor"))) {
					legal = true;
				}
				break;
			case KeyEvent.VK_D:
				if ((p2.xCoord() < height - 1) && (map[p2.yCoord()][p2.xCoord() + 1].getType().equals("floor"))) {
					legal = true;
				}
				break;	
				
			
		}
		
		return legal;
	}
	
	/**
	 * 
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}
	
	/**
	 * @return the p
	 */
	public Player getP() {
		return p;
	}

	/**
	 * @return the p2
	 */
	public Player getP2() {
		return p2;
	}
	
	/**
	 * 
	 * @return the grid size
	 */
	public int getGridSize() {
		return height;
	}

	/**
	 * Gets the player image
	 * @param x	X-coordinate of player
	 * @param y	Y-coordinate of player
	 * @return	The player image
	 */
	public Image getPlayerImage(int x, int y) {
		if (p.xCoord() == y && p.yCoord() == x) {
			return p.getPlayer();
		}
			
		if (multiplayer || aiEnabled) {
			if (p2.xCoord() == y && p2.yCoord() == x) {
				return p2.getPlayer();
			}
		}	
			
		return null;
	}

	/**
	 * Checks multiplayer status
	 * @return	Disabled or enabled
	 */
	public boolean isMultiplayer() {
		if(multiplayer)
			return true;
		else 
			return false;
	}

	/**
	 * Gets image of the tile
	 * @param x	X-coordinate of image
	 * @param y Y-coordinate of image
	 * @return
	 */
	public Image getTileImage(int x, int y) {
		return map[x][y].getOnMap();
	}

	/**
	 * Checks if player is here
	 * @param x	X-coordinate of player
	 * @param y	Y-coordinate of player
	 * @return	Whether player is here 
	 */
	public boolean newPlayerPosition(int x, int y) {
		if (p.xCoord() == y && p.yCoord() == x) {
			return true;
		}
		if (p2 != null) {
		    if (p2.xCoord() == y && p2.yCoord() == x) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether player was previously there
	 * @param x	X-coordinate of player
	 * @param y	Y-coordinate of player
	 * @return	Whether player was here
	 */
	public boolean oldPlayerPosition(int x, int y) {
		if (p.xCoordPrev() == y && p.yCoordPrev() == x) {
			return true;
		}
		if (p2 != null) {
		    if (p2.xCoordPrev() == y && p2.yCoordPrev() == x) {
				return true;
			}
		}
		return false;
	}
}

