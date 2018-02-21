import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class MazeGUI {
	private MainWindow window;
	private Maze maze;
	private JPanel mazePanel;
	private JPanel windowPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel pauseMenu;
    private JLabel visualTimer;
    private boolean menuDisplayed;
    private Font pokemonFont;
    private Timer timer;
    private int timeLeft;
	private SimpleDateFormat time;
	private KeyListener kl;
	private boolean aiEnabled;
	private ImagePanel[][] storePanel;

	
	/**
	 * Creates a new Maze GUI for the game.
	 * @param mw MainWindow the gui will be added to.
	 * @param gridSize Grid size of the map.
	 * @param mode Game mode of the current game.
	 * @param mp Whether multiplayer is enabled.
	 */
	public MazeGUI (MainWindow mw, int gridSize, int mode, boolean mp) {
		this.window = mw;
		this.maze = new Maze(gridSize, mode, mp, window.getLimitedVision());
		this.storePanel = new ImagePanel[gridSize+1][gridSize+1];
		this.menuDisplayed = false;
		if (window.getMode() == 2) {
			this.aiEnabled = true;
		} else {
			this.aiEnabled = false;
		}
		
		this.windowPanel = new JPanel();
		windowPanel.setLayout(new BorderLayout());
		
		this.rightPanel = new JPanel();
		rightPanel.setLayout(new OverlayLayout(rightPanel));
		
		this.leftPanel = new JPanel();
		leftPanel.setLayout(new OverlayLayout(leftPanel));
		
		kl = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				maze.handleKeyPressed(e.getKeyCode());
				updateMaze();
				
				if (maze.mazeFinished()) {
					gameWon();
				}
			}
		};
		
		createPokeFont(pokemonFont);
		createMazePanel();
		createTimer();
		createPauseMenu();
		createRightPanel();
		
		leftPanel.add(mazePanel);

		windowPanel.add(leftPanel,BorderLayout.CENTER);
		windowPanel.add(rightPanel,BorderLayout.EAST);
		timer.start();
		if (aiEnabled) maze.startAi();
	}
	
	/**
	 * Creates a timer for the game.
	 */
	private void createTimer() {
		int mapSize = window.getGridSize();
		if (mapSize == 10) {
			timeLeft = 15 * 1000;
		} else if (mapSize == 20) {
			timeLeft = 35 * 1000;
		} else if (mapSize == 30) {
			timeLeft = 60 * 1000;
		} else if (mapSize == 40) {
			timeLeft = 120 * 1000;
		} else  {
			timeLeft = 180 * 1000;
		}
		if (window.getLimitedVision()) {
			timeLeft *= 1.5;
		}
		
		this.timer = new Timer(200, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				timeLeft -= 200;
				visualTimer.setText(time.format(timeLeft));
				visualTimer.repaint();
				updateMaze();
				if (maze.mazeFinished()) {
					gameWon();
				}
				if (timeLeft <= 0) {
					timer.stop();
					if (aiEnabled) maze.stopAi();
					mazePanel.removeKeyListener(kl);
					gameLost();
				}
			}
		});
		
		this.visualTimer = new JLabel();
		time = new SimpleDateFormat("mm:ss");

        visualTimer.setPreferredSize(new Dimension (70, 30));
		visualTimer.setForeground(Color.WHITE);
        visualTimer.setBackground(Color.BLACK);
        visualTimer.setOpaque(true);
        visualTimer.setFont(pokemonFont);
        visualTimer.setText(time.format(timeLeft));
        visualTimer.setHorizontalAlignment(SwingConstants.CENTER);
        visualTimer.setVerticalAlignment(SwingConstants.CENTER);

	}
	
	/**
	 * Create the in game panel/menu for the game.
	 */
	private void createRightPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel rightBg = new JPanel(new GridLayout(1,1));
		rightBg.add(new ImagePanel("Images/menu.png"));
		rightBg.setPreferredSize(new Dimension(200, rightBg.getHeight()));
		
		JPanel rightFg = new JPanel(new GridBagLayout());
		rightFg.setOpaque(false);
		
        gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		rightFg.add(visualTimer, gbc);

		gbc.insets = new Insets(50,0,5,0);
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		
		if ((window.getMode() == 0 || window.getMode() == 2) && window.isMultiplayer()){
			rightFg.add(new JLabel(new ImageIcon("Images/mode0mp.png")), gbc);				
		} else if (window.getMode() == 0){
			rightFg.add(new JLabel(new ImageIcon("Images/mode0solo.png")), gbc);
		} else if (window.getMode() == 2) {
			rightFg.add(new JLabel(new ImageIcon("Images/modeAI.png")), gbc);
		}
        
		JButton menu = new JButton("Menu");
		menu.setFocusPainted(false);
        menu.setOpaque(true);
        menu.setFont(pokemonFont);
        menu.setBackground(Color.WHITE);
        menu.setBorder(new LineBorder(Color.BLACK, 3));
        menu.setPreferredSize(new Dimension(120,60));
		
        gbc.insets = new Insets(200,0,10,0);
        gbc.gridy = 3;
		gbc.gridwidth = 2;
		rightFg.add(menu, gbc);
        
        menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!menuDisplayed) {
					pause();
				} else {
					resume();
				}
			}
        });
        rightFg.setOpaque(false);
        
        rightPanel.add(rightFg);
        rightPanel.add(rightBg);
	}
	
	/**
	 * Pauses the current game.
	 * @precondition the current game is running.
	 */
	private void pause() {
		timer.stop();
		if (aiEnabled) maze.stopAi();
		createPauseMenu();
		leftPanel.add(pauseMenu);
		leftPanel.validate();
		menuDisplayed = true;
	}
	
	/**
	 * Resumes the current game.
	 * @precondition the current game has been paused.
	 */
	private void resume() {
		leftPanel.remove(pauseMenu);
		leftPanel.validate();
		leftPanel.repaint();
		menuDisplayed = false;
		mazePanel.requestFocusInWindow();
		timer.start();
		if (aiEnabled) maze.startAi();
		visualTimer.setText(time.format(timeLeft));
	}
	
	/**
	 * Creates a pause menu to be displayed.
	 */
	private void createPauseMenu() {
		pauseMenu = new JPanel(new GridBagLayout());
		pauseMenu.setBackground(new Color(0,0,0,240));
    	
    	JButton resume = new JButton("Resume", new ImageIcon("Images/noIcon.png")); 
	    JButton restart = new JButton("Restart", new ImageIcon("Images/noIcon.png")); 
	    JButton mainMenu = new JButton("Main Menu", new ImageIcon("Images/noIcon.png")); 
	    JButton quit = new JButton("Quit", new ImageIcon("Images/noIcon.png")); 
	    
	    themeButton(resume);
	    themeButton(restart);
	    themeButton(mainMenu);
	    themeButton(quit);
	    
		JPanel menuOptions = new JPanel(new GridBagLayout());
	    
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(20, 20, 20, 20);
    	menuOptions.add(resume, gbc);
      	 
    	gbc.gridy = 1;
    	menuOptions.add(restart, gbc);
      	 
    	gbc.gridy = 2;
    	menuOptions.add(mainMenu, gbc);
      	 
    	gbc.gridy = 3;
    	menuOptions.add(quit, gbc);
    	
	    resume.addActionListener(new ActionListener() {
	      
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		resume();		
	    	}
	    });

	    restart.addActionListener(new ActionListener() {
	      
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		resetTimer();
	    		maze.restartMaze();
	    		paintMaze();
	    		resume();
	      	}
	    });

	    mainMenu.addActionListener(new ActionListener() {
	      
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		window.displayMenuGUI();
	    	}
	    });

	    quit.addActionListener(new ActionListener() {
	      
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		window.closeWindow();
	    	}
	    });

		
		pauseMenu.add(menuOptions);
	}
	
	/**
	 * Resets the timer back to it's initial value.
	 */
	private void resetTimer() {
		int mapSize = window.getGridSize();
		if (mapSize == 10) {
			timeLeft = 15 * 1000;
		} else if (mapSize == 20) {
			timeLeft = 35 * 1000;
		} else if (mapSize == 30) {
			timeLeft = 60 * 1000;
		} else if (mapSize == 40) {
			timeLeft = 120 * 1000;
		} else  {
			timeLeft = 180 * 1000;
		}
		if (window.getLimitedVision()) {
			timeLeft *= 1.5;
		}
		
		visualTimer.setText(time.format(timeLeft));
		timer.start();
	}
	
	/**
	 * Displays the lost game pop-up
	 */
	private void gameLost() {
		JFrame parent = new JFrame();
		String[] options = {"Try Again", "New Game", "Main Menu" };
		int choice = JOptionPane.showOptionDialog(parent, "You did not complete the maze in time!", "Gameover",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
		
		if (choice == -1) {
			gameLost();
		} else if (choice == 0 ) {
			maze.restartMaze();
			mazePanel.addKeyListener(kl);
			resetTimer();
			if (aiEnabled) maze.resetAi();
			paintMaze();
		} else if (choice == 1) {
			maze.newMaze();
			mazePanel.addKeyListener(kl);
			resetTimer();
			if (aiEnabled) maze.resetAi();
			paintMaze();
		} else if (choice == 2) {
			window.displayMenuGUI();
		}
	}
	
	/**
	 * Displays the game won pop-up
	 */
	private void gameWon() {
		JFrame parent = new JFrame();
		timer.stop();
		if (aiEnabled) maze.stopAi();
		String[] options = { "New Maze", "Main Menu" };
		int choice;
		
		if (maze.isMultiplayer() || aiEnabled) {	
			if (maze.getWinner().xCoord() == 1 && maze.getWinner().yCoord() == 1) {
				if (aiEnabled) {
					choice = JOptionPane.showOptionDialog(parent, "Computer wins! Challenge again?", "Message",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, options, options[0]);
				} else {
					choice = JOptionPane.showOptionDialog(parent, "Player 2 wins! Challenge again?", "Message",
							JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
							null, options, options[0]);
				}
			} else {
				choice = JOptionPane.showOptionDialog(parent, "Player 1 wins! Challenge again?", "Message",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
			}
		} else {
			choice = JOptionPane.showOptionDialog(parent, "Maze complete! New maze?", "Message",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[0]);
		}

		if (choice == -1) {
			gameWon();
		} else if (choice == 0) {
			maze.newMaze();
			resetTimer();
			if (aiEnabled) maze.resetAi();
			paintMaze();
		} else if (choice == 1) {
			window.displayMenuGUI();
		}
	}
	
	/**
	 * Creates a pokemon font.
	 * @param font Font to be added.
	 */
	private void createPokeFont(Font font) {
		try {
            pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")));
       } catch (IOException|FontFormatException e) {
            //System.out.println("FONT NOT FOUND");
       }
	}
	
	/**
	 * Gets the JPanel for the mazeGUI window.
	 * @return JPanel of the MazeGUI
	 */
	public JPanel getWindowPanel () {
		return windowPanel;
	}
	
	
    /**
     * Gets the menu panel for the maze GUI.
     * @return Panel to be returned.
     */
    public JPanel getMazePanel() {
        mazePanel.setOpaque(true);
        return mazePanel;
    }
	
	/**
	 * Creates a maze Panel
	 * @return JPanel for the maze part of the gui.
	 */
	private void createMazePanel() {
		this.mazePanel = new JPanel();
		mazePanel.setLayout(new GridLayout(maze.getGridSize(), maze.getGridSize()));
		paintMaze();
		mazePanel.addKeyListener(kl);			
	}
	
	/**
	 * Paints every element of the maze. (slow)
	 */
	private void paintMaze() {
		mazePanel.removeAll();
		
		ImageIcon catch1 = new ImageIcon("Images/geodude.png");  
		ImageIcon catch2 = new ImageIcon("Images/zubat.png");
		Image pokemon1 = catch1.getImage();
		Image pokemon2 = catch2.getImage();
		
		for (int i = 0; i < maze.getGridSize(); i++) {
			for (int j = 0; j < maze.getGridSize(); j++) {
				
				Image tileImage = maze.getTileImage(i,j);
				Image playerImage = maze.getPlayerImage(i, j);
				ImagePanel tilePanel = new ImagePanel(tileImage, playerImage, null);
				
				if(i == maze.getGridSize() - 2  && j == maze.getGridSize() - 2) {
					tilePanel = new ImagePanel(tileImage, pokemon1, playerImage);
				}
				if ((maze.isMultiplayer() || aiEnabled) && i == 1 && j == 1) {
					tilePanel = new ImagePanel(tileImage, pokemon2, playerImage);
				}
				
				storePanel[i][j] = tilePanel;
				mazePanel.add(tilePanel);
			}			
		}
		mazePanel.validate();
	}
	
	/**
	 * Paints only the elements that need repainting.
	 */
	private void updateMaze() {
		for (int i = 0; i < maze.getGridSize(); i++) {
			for (int j = 0; j < maze.getGridSize(); j++) {
				
				Image tileImage = maze.getTileImage(i,j);
				Image playerImage = maze.getPlayerImage(i, j);
				
				if (maze.newPlayerPosition(i, j) || maze.oldPlayerPosition(i, j)) {
					storePanel[i][j].updateTile(tileImage, playerImage, null);
					storePanel[i][j].repaint();
				
					updateFog(i,j);
				} 
			}			
		}
		mazePanel.validate();
	}
	
	/**
	 * Uncovers fog that is displayed in the maze.
	 * @param x
	 * @param y
	 */
	private void updateFog(int x, int y) {
		int fogSize = 1;
		for (int i = -fogSize; i <= fogSize; i++) {
			for (int j = -fogSize; j <= fogSize; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				
				if (i + x < 0 || j + y < 0 || i + x > window.getGridSize() || j + y > window.getGridSize()) {
					continue;
				}
				Image tileImage = maze.getTileImage(x + i, y + j);
				Image playerImage = maze.getPlayerImage(x + i, y + j);
				
				if ((i + x) == maze.getGridSize() - 2 && (j + y) == maze.getGridSize() - 2) {
					ImageIcon catch1 = new ImageIcon("Images/geodude.png");  
					Image pokemon1 = catch1.getImage();
					storePanel[i+x][j+y].updateTile(tileImage, pokemon1, playerImage);
					storePanel[i + x][j + y].repaint();
				} else if ((maze.isMultiplayer() || aiEnabled) && x + i == 1 && j + y == 1) {
					ImageIcon catch2 = new ImageIcon("Images/zubat.png");
					Image pokemon2 = catch2.getImage();
					storePanel[x + i][j + y].updateTile(tileImage, pokemon2, playerImage);
					storePanel[i + x][j + y].repaint();
				} else {
					storePanel[i + x][j + y].updateTile(tileImage, playerImage, null);
					storePanel[i + x][j + y].repaint();
				}
			}
		}
	}
    
	/**
	 * Themes a button in the pokemon style.
	 * @param b Button to be themed.
	 */
	private void themeButton (JButton b) {      	 
    	b.setFocusPainted(false);
    	b.setContentAreaFilled(false);
    	b.setOpaque(true);
    	b.setFont(pokemonFont);
    	b.setBackground(Color.WHITE);
    	b.setBorder(new LineBorder(Color.BLACK, 3));
    	b.setPreferredSize(new Dimension(220,60));
    	b.setHorizontalAlignment(SwingConstants.LEFT);
    	b.setIconTextGap(35);
    	b.setRolloverIcon(new ImageIcon("Images/pokeballIcon.png"));
	}
}

