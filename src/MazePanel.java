import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MazePanel extends JPanel {
/*	
	private Maze maze;
	private KeyListener kl;
	private MazeGUI mgui;
	
	private Image floor;
	private boolean aiOn = false;

	public MazePanel(int gridSize, MazeGUI mgui) {
		this.mgui = mgui;
		this.maze = new Maze(this, gridSize);
		ImageIcon img = new ImageIcon("Images/floor.png");
		this.floor = img.getImage();
		kl = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				maze.handleKeyPressed(e.getKeyCode());
				repaint();
				
				if (maze.mazeFinished()) {
					gameWon();
				}
			}
		};
		addKeyListener(kl);
	}
	
	//might move this to MazeGUI
	public void timeUp() {
		removeKeyListener(kl);
		if (aiOn) maze.stopAi();
		gameLost();
	}
	
	//might move this to MazeGUI
	public void gameLost() {
		JFrame parent = new JFrame();
		String[] options = {"Try Again", "New Game", "Main Menu" };
		int choice = JOptionPane.showOptionDialog(parent, "You did not complete the maze in time!", "Gameover",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
		
		if (choice == -1) {
			gameLost();
		} else if (choice == 0 ) {
			maze.restartMaze();
			addKeyListener(kl);
			mgui.resetTimer();
			if (aiOn) maze.startAi();
			repaint();
		} else if (choice == 1) {
			maze.newMaze();
			addKeyListener(kl);
			mgui.resetTimer();
			if (aiOn) maze.startAi();
			repaint();
		} else if (choice == 2) {
			mgui.getMw().displayMenuGUI(); //temporary
		}
	}
	
	//might move this to MazeGUI
	public void gameWon() {
		JFrame parent = new JFrame();
		mgui.stopTimer();
		if (aiOn) maze.stopAi();
		String[] options = { "New Maze", "Main Menu" };
		int choice = JOptionPane.showOptionDialog(parent, "Maze complete! New maze?", "Message",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);

		if (choice == -1) {
			gameWon();
		} else if (choice == 0) {
			maze.newMaze();
			mgui.resetTimer();
			if (aiOn) maze.startAi();
			repaint();
		} else if (choice == 1) {
			mgui.getMw().displayMenuGUI();
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.getParent().getWidth() - 180, this.getParent().getHeight());
    }
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int xScale = this.getWidth()/31;
		int yScale = this.getHeight()/31;
		
		for(int y = 0; y < maze.getMap().length; y++) {
			for(int x = 0; x < maze.getMap()[y].length; x++) {
				g.drawImage(floor, x * xScale, y * yScale, xScale, yScale, null);
				g.drawImage(maze.getMap()[y][x].getOnMap(), x * xScale, y * yScale, xScale, yScale, null);
				
				if (maze.getP().xCoord() == x && maze.getP().yCoord() == y) {
					g.drawImage(maze.getP().getPlayer(), x * xScale, y * yScale, xScale, yScale, null);
				}
				if (maze.getP2().xCoord() == x && maze.getP2().yCoord() == y) {
					g.drawImage(maze.getP2().getPlayer(), x * xScale, y * yScale, xScale, yScale, null);
				}
				
				//^^^^somehow stop referencing player? (encapsulation)
				
				//unresizeable
				g.drawImage(floor, x * 30, y * 30, null);
				g.drawImage(maze.getMap()[y][x].getTile(), x * 30, y * 30, null);
				
				if (maze.getP().xCoord() == x && maze.getP().yCoord() == y) {
					g.drawImage(maze.getP().getPlayer(), x * 30, y * 30, null);
				}
				if (maze.getP2().xCoord() == x && maze.getP2().yCoord() == y) {
					g.drawImage(maze.getP2().getPlayer(), x * 30, y * 30, null);
				}
				
			}
		}
	}
	
	
	public JPanel createMazePanel() {
		JPanel mazePanel = new JPanel();
		mazePanel.setLayout(new GridLayout(maze.getGridSize(), maze.getGridSize()));
		
		for (int i = 0; i < maze.getGridSize(); i++) {
			for (int j = 0; j < maze.getGridSize(); j++) {
				MazeTile t = maze.getTile(i,j);		
				mazePanel.add(t.getImagePanel());
			}			
		}
		
		return mazePanel;
	}*/
}

