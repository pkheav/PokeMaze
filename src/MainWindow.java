import javax.swing.*;
  
public class MainWindow {
    private JFrame gui;
    private boolean multiplayer;
    private String gameMode;
    private boolean limitedVision;
    private int gridSize;
    private JPanel menuPanel;
    private JPanel mazePanel;
    private MazeGUI mazeGUI;
    
    /**
     * Constructs a new window for the maze game.
     */
    public MainWindow() {
        this.multiplayer = false;
        this.gameMode = "normal";
        this.gridSize = 30;
        this.limitedVision = false;
          
        // Initialise JFrame
        this.gui = new JFrame("PokeMaze");
        
        MenuGUI menu = new MenuGUI(this);
        menuPanel = menu.getMenuPanel();
        gui.add(menuPanel);
        gui.pack();
        gui.setSize(1146,968/*1280, 720*/);
        gui.setLocationRelativeTo(null);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
      
    
    public static void main(String[] args) {
        //Create a new menu GUI
    	//Display window in a different thread. Read about swing concurrency to understand why 
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
        	MainWindow window = new MainWindow();
         	window.display();
         }
    	});
    }
  
    /**
     * Sets whether the current game mode is single player or multiplayer.
     * @param value true for multiplayer; false otherwise.
     */
    public void setMultiplayer(boolean value) {
        this.multiplayer = value;
    }
    
    /**
     * Returns whether the current game mode requested from the user is multiplayer or not.
     * @return true if multiplayer; false otherwise.
     */
    public boolean isMultiplayer() {
    	return this.multiplayer;
    }
    
    /**
     * Sets the grid size of the game that the user requests.
     * @param gridSize Size of the grid to be set.
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }
     
    /**
     * Gets the grid size for the maze that the user specified.
     * @return Size of the grid that has been set.
     */
    public int getGridSize() {
        return this.gridSize;
    }
    
    /**
     * Sets the current game mode that the user would like to play. 
     * @param mode Mode to play {"normal", or "AI"}
     */
    public void setGameMode(String mode) {
        this.gameMode = mode;
    }
    
    /**
     * Sets whether limited vision is enabled or not.
     * @param mode Enabled or disabled
     */
    public void setLimitedVision(boolean mode) {
    	//System.out.println("VIsion mode set: " + mode);
    	this.limitedVision = mode;
    }
    
    /**
     * Gets whether limited vision is enabled or not.
     * @return	Limited vision enabled or disabled
     */
    public boolean getLimitedVision() {
    	return this.limitedVision;
    }
    
    /**
     * Gets the mode the user has last requested.
     * @return Game mode that was selected.
     */
    public int getMode() {
    	if (this.gameMode.equals("normal")) {
    		return 0;
    	} else if (this.gameMode.equals("hm05")) {
    		return 1;
    	} else if (this.gameMode.equals("ai")) {
    		return 2;
    	} else if (this.gameMode.equals("pokeball")) {
    		return 3;
    	}
    	return -1;
    }    
    
    /**
     * Displays the maze GUI on the JFrame
     */
    public void startMazeGUI() {
    	gui.remove(menuPanel);
    	mazeGUI = new MazeGUI(this, gridSize, getMode(), multiplayer);
    	this.mazePanel = mazeGUI.getMazePanel();
    	gui.add(mazeGUI.getWindowPanel());
    	mazePanel.setFocusable(true);
    	mazePanel.requestFocusInWindow();
    	gui.validate();
    	gui.repaint();
    }

    /**
     * Displays the menu GUI on the JFrame
     */
    public void displayMenuGUI() {
    	gui.remove(mazeGUI.getWindowPanel());
        MenuGUI menu = new MenuGUI(this);
        menuPanel = menu.getMenuPanel();
        gui.add(menuPanel);
        gui.pack();
        gui.setSize(mazeGUI.getWindowPanel().getWidth(), mazeGUI.getWindowPanel().getHeight());
    }
    
    /**
     * Sets JFrame parameters to display the GUI
     */
    private void display() {
        gui.setVisible(true);
        gui.setResizable(true);
    }

    /**
     * Repaints GUI
     */
	public void repaint() {
		gui.repaint();
	}
    
	/**
	 * Closes the JFrame
	 */
	public void closeWindow() {
    	gui.dispose();
    }
	
}
