import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
   
   
public class MenuGUI {
	private MainWindow window;
  	 
	// Main GUI panel
	private JPanel mainMenu;
	private ImagePanel bgPanel;
  	 
	private ButtonGroup gameModeGroup;
	private ButtonGroup numPlayerGroup;
	private ButtonGroup gridSizeGroup;
	private ButtonGroup visionGroup;
	
	private JRadioButton aiRace;
	private JRadioButton normalMode;
	private JRadioButton hm05Mode;
	private JRadioButton normalVision;
   
  	 
	/**
 	* Creates a new main menu GUI Panel
 	*/
	public MenuGUI(MainWindow window) {
      	 
    	this.window = window;
    	//mainMenu stores both the background image with the foreground buttons overlayed.
    	this.mainMenu = new JPanel();
    	mainMenu.setLayout(new OverlayLayout(mainMenu));
      	 
    	//Create a background panel (will store background image)
    	this.bgPanel = new ImagePanel("Images/bg.png");
    	bgPanel.setLayout(new GridLayout(1,1));
      	 
    	//Create a main menu panel
    	JPanel menuList = createMainMenu();
    	menuList.setOpaque(false);
    	mainMenu.add(menuList);
    	mainMenu.add(bgPanel);  
	}
  	 
	/**
 	* Gets the menu panel for the menu GUI.
 	* @return Panel to be returned.
 	*/
	public JPanel getMenuPanel() {
    	return mainMenu;
	}
  	 
  	 
	/**
 	*  Creates a new main menu list of options in a panel.
 	*  @return Panel to be returned.
 	*/
	private JPanel createMainMenu() {
    	JPanel mainMenuList = new JPanel();  // Stores the list of buttons that appear on the mainMenu Panel
      	 
    	//Create list of options for the main menu
    	mainMenuList.setLayout(new GridBagLayout());
    	mainMenuList.setBorder(new EmptyBorder(200,0,0,0));
      	 
    	//Create buttons
    	JButton newGame = new JButton("New Game", new ImageIcon("Images/noIcon.png"));  
    	JButton help = new JButton   (" 	Help 	", new ImageIcon("Images/noIcon.png"));
    	JButton settings = new JButton("Board Size", new ImageIcon("Images/noIcon.png"));
    	JButton quit = new JButton(" 	Quit", new ImageIcon("Images/noIcon.png"));
      	 
    	//Theme the buttons
    	themeButton(newGame);
    	themeButton(help);
    	themeButton(settings);
    	themeButton(quit);
      	 
    	// Create a constraint to layout the buttons.
    	// 'gridx' = column, 'gridy' = row
    	// 'fill' means make buttons same width
    	// 'insets' = the distance between the buttons.
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(20, 20, 20, 20);
    	mainMenuList.add(newGame, gbc);
      	 
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	mainMenuList.add(help, gbc);
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	mainMenuList.add(settings, gbc);
      	 
    	gbc.gridx = 1;
    	gbc.gridy = 1;
    	mainMenuList.add(quit, gbc);
      	 
    	newGame.addActionListener(new ActionListener() { 	 
        	@Override
        	public void actionPerformed(ActionEvent e) {
            	mainMenu.removeAll();
            	JPanel setDifficulty = createNewGamePanel();
   
            	setDifficulty.setOpaque(false);
            	setDifficulty.validate();
   
            	mainMenu.add(setDifficulty);
            	mainMenu.add(bgPanel);
              	 
            	mainMenu.validate();
            	mainMenu.repaint();
        	}
    	});
    	 
   
    	help.addActionListener(new ActionListener() {
          	 
        	@Override
        	public void actionPerformed(ActionEvent e) {
            	mainMenu.removeAll();
            	JPanel helpPanel = createHelpPanel();
   
            	helpPanel.setOpaque(false);
            	helpPanel.validate();
   
            	mainMenu.add(helpPanel);
            	mainMenu.add(bgPanel);
              	 
            	mainMenu.validate();
            	mainMenu.repaint();
        	}
    	});
      	 
    	settings.addActionListener(new ActionListener() {
          	 
        	@Override
        	public void actionPerformed(ActionEvent e) {
            	mainMenu.removeAll();
            	JPanel settingsPanel = createSettingsPanel();
   
            	settingsPanel.setOpaque(false);
            	settingsPanel.validate();
   
            	mainMenu.add(settingsPanel);
            	mainMenu.add(bgPanel);
              	 
            	mainMenu.validate();
            	mainMenu.repaint();
        	}
    	});
 	 
      	 
    	quit.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
            	System.exit(0);
              	 
        	}
    	});
      	 
    	return mainMenuList;
	}
  	 
  	 
  	 
  	 
	/**
 	* Creates the panel that will be seen when clicking on the "New Game" button.
 	* @return Panel with the new game options.
 	*/
	private JPanel createNewGamePanel() {
    	JPanel gameModePanel = new JPanel(new GridBagLayout());
    	//gameModePanel.setLayout(new GridBagLayout());
    	gameModePanel.setBorder(new EmptyBorder(200,0,0,0));
      	 
    	JRadioButton singlePlayer = new JRadioButton("1 Player.", true);
    	JRadioButton multiplayer = new JRadioButton("2 Players");
      	 
    	this.numPlayerGroup = new ButtonGroup();
    	numPlayerGroup.add(singlePlayer);
    	numPlayerGroup.add(multiplayer);
     	 
    	singlePlayer.setActionCommand("single");
    	multiplayer.setActionCommand("multi");
     	 
    	this.normalMode = new JRadioButton("Normal.", true);
    	this.aiRace = new JRadioButton("AI Race.");
    	

    	this.normalVision = new JRadioButton("Normal Vision.", true);
    	this.hm05Mode = new JRadioButton("Limited Vision.");
   
    	
    	singlePlayer.addActionListener(new ActionListener() {	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Prevent AI race from being selected.
        		aiRace.setEnabled(true);
        	}
    	});
    	
    	multiplayer.addActionListener(new ActionListener() {	
        	@Override
        	public void actionPerformed(ActionEvent e) {

        		// Prevent AI race from being selected.
        		aiRace.setEnabled(false);
        		normalMode.setSelected(true);
        	}
    	});
    	
    	aiRace.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {

        		// Prevent AI race from being selected.
        		hm05Mode.setEnabled(false);
        		normalVision.setSelected(true);
        	}
    	});
    	
    	normalMode.addActionListener(new ActionListener() {	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Prevent AI race from being selected.
        		hm05Mode.setEnabled(true);
        	}
    	});
     	 
     	//Game modes
    	normalMode.setActionCommand("normal");
    	aiRace.setActionCommand("ai");
    	
    	//Vision modes
    	normalVision.setActionCommand("normal");
    	hm05Mode.setActionCommand("hm05");
     	 
    	this.gameModeGroup = new ButtonGroup();
    	gameModeGroup.add(normalMode);
    	gameModeGroup.add(aiRace);
    	
    	this.visionGroup = new ButtonGroup();
    	visionGroup.add(normalVision);
    	visionGroup.add(hm05Mode);
    	
      	 
    	themeRadioButton(singlePlayer);
    	themeRadioButton(multiplayer);
    	
    	themeRadioButton(normalMode);
    	themeRadioButton(aiRace);
    	
    	themeRadioButton(normalVision);
    	themeRadioButton(hm05Mode);
      	 
    	JButton play = new JButton("Play!", new ImageIcon("Images/noIcon.png"));
    	themeButton(play);
    	play.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
            	//Set the game mode selected
            	String gameMode = gameModeGroup.getSelection().getActionCommand();
            	window.setGameMode(gameMode);
             	 
            	//Set whether multiplayer or single player
            	String btnText = numPlayerGroup.getSelection().getActionCommand();
            	if (btnText.equals("single")) {
                	window.setMultiplayer(false);
            	} else {
                	window.setMultiplayer(true);
            	}
            	
            	String visionMode = visionGroup.getSelection().getActionCommand();
            	if (visionMode.equals("hm05")) {
            		window.setLimitedVision(true);
            	} else {
            		window.setLimitedVision(false);
            	}
         	 
            	if(btnText.equals("single")) {
                	window.startMazeGUI();
            	} else {
                	window.startMazeGUI();
            	}
            	 
         	 
        	}
    	});
    	 
    	JButton backButton = createBackButton();
    	themeButton(backButton);
      	 
    	JPanel settings = new JPanel(new GridBagLayout());
    	themeJPanel(settings);
    	
    	JPanel gameMode = new JPanel(new GridBagLayout());
    	JPanel gameType = new JPanel(new GridBagLayout());
    	JPanel visionMode = new JPanel(new GridBagLayout());
    	 
    	themeJPanel(gameMode);
    	themeJPanel(gameType);
    	themeJPanel(visionMode);
      	 
    	// Create a constraint
    	GridBagConstraints gbc = new GridBagConstraints();
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(10, 10, 10, 10);
      	 
    	JLabel numPlayers = new JLabel("Number of Players:");
    	themeLabelHeading(numPlayers);
    	gameMode.add(numPlayers, gbc);
      	 
    	gbc.gridy = 1;
    	gameMode.add(singlePlayer, gbc);
      	 
    	gbc.gridx = 1;
    	gameMode.add(multiplayer, gbc);
      	 
    	JLabel gameTypeHeading = new JLabel("Choose Game Mode:");
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	themeLabelHeading(gameTypeHeading);
    	gameType.add(gameTypeHeading, gbc);
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gameType.add(normalMode, gbc);
      	 
    	gbc.gridx = 1;
    	gameType.add(aiRace, gbc);

    	JLabel visionHeading = new JLabel("Choose Vision Mode:");
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	themeLabelHeading(visionHeading);
    	visionMode.add(visionHeading);
    	
    	gbc.gridy = 1;
    	visionMode.add(normalVision, gbc);
    	
    	gbc.gridx = 1;
    	visionMode.add(hm05Mode, gbc);
   
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.gridwidth = 2;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(10, 10, 10, 10);
    	gameModePanel.add(gameMode, gbc);
      	 
    	gbc.gridy = 1;
    	gameModePanel.add(gameType,gbc);
    	
    	gbc.gridy = 2;
    	gameModePanel.add(visionMode, gbc);
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 3;
    	gbc.gridwidth = 1;
    	gbc.fill = GridBagConstraints.REMAINDER;
    	gameModePanel.add(play, gbc);
    	 
    	gbc.gridx = 1;
    	gbc.gridy = 3;
    	gameModePanel.add(backButton, gbc);
   
    	return gameModePanel;
	}
  	 
  	 
  	 
	/**
 	* Creates a help panel to be added to the main menu when the 'Help' button is pressed.
 	* @return Help panel to be returned.
 	*/
	private JPanel createHelpPanel() {
      	 
    	JPanel helpPanel = new JPanel(new GridBagLayout());
    	helpPanel.setLayout(new GridBagLayout());
    	helpPanel.setBorder(new EmptyBorder(200,0,0,0));
   
    	JPanel controlsPanel = new JPanel(new GridBagLayout());
    	//controlsPanel.setBackground(Color.WHITE);
    	themeJPanel(controlsPanel);
      	 
    	// Player 1 controls
    	JLabel p1Controls = new JLabel("Player 1 Controls:");
    	JLabel p1up = new JLabel("Move up.", new ImageIcon("Images/up.png"), JLabel.CENTER);
    	p1up.setHorizontalAlignment(SwingConstants.LEFT);
    	p1up.setIconTextGap(25);
      	 
    	JLabel p1left = new JLabel("Move down.", new ImageIcon("Images/left.png"), JLabel.CENTER);
    	p1left.setHorizontalAlignment(SwingConstants.LEFT);
    	p1left.setIconTextGap(25);
      	 
    	JLabel p1down = new JLabel("Move left.", new ImageIcon("Images/down.png"), JLabel.CENTER);
    	p1down.setHorizontalAlignment(SwingConstants.LEFT);
    	p1down.setIconTextGap(25);
      	 
    	JLabel p1right = new JLabel("Move Right.", new ImageIcon("Images/right.png"), JLabel.CENTER);
    	p1right.setHorizontalAlignment(SwingConstants.LEFT);
    	p1right.setIconTextGap(25);
      	 
    	//Player two controls
    	JLabel p2Controls = new JLabel("Player 2 Controls:");
    	JLabel p2up = new JLabel("Move up.", new ImageIcon("Images/w.png"), JLabel.CENTER);
    	p2up.setHorizontalAlignment(SwingConstants.LEFT);
    	p2up.setIconTextGap(25);
      	 
    	JLabel p2left = new JLabel("Move down.", new ImageIcon("Images/a.png"), JLabel.CENTER);
    	p2left.setHorizontalAlignment(SwingConstants.LEFT);
    	p2left.setIconTextGap(25);
      	 
    	JLabel p2down = new JLabel("Move left.", new ImageIcon("Images/s.png"), JLabel.CENTER);
    	p2down.setHorizontalAlignment(SwingConstants.LEFT);
    	p2down.setIconTextGap(25);
      	 
    	JLabel p2right = new JLabel("Move Right.", new ImageIcon("Images/d.png"), JLabel.CENTER);
    	p2right.setHorizontalAlignment(SwingConstants.LEFT);
    	p2right.setIconTextGap(25);
      	 
    	p1Controls.setBackground(Color.WHITE);
    	p2Controls.setBackground(Color.WHITE);
    	p1Controls.setOpaque(true);
    	p2Controls.setOpaque(true);
      	 
    	themeLabelHeading(p1Controls);
    	themeLabelHeading(p2Controls);
    	themeLabelOption(p1up);
    	themeLabelOption(p1down);
    	themeLabelOption(p1left);
    	themeLabelOption(p1right);
    	themeLabelOption(p2up);
    	themeLabelOption(p2down);
    	themeLabelOption(p2left);
    	themeLabelOption(p2right);
      	 
    	JButton backToMenu = createBackButton();
    	themeButton(backToMenu);
      	 
    	controlsPanel.setBorder(new LineBorder(Color.BLACK, 3));
      	 
    	// Create a constraint
    	GridBagConstraints gbc = new GridBagConstraints();
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(10, 10, 10, 10);
    	controlsPanel.add(p1Controls, gbc);
      	 
    	gbc.gridy = 1;
    	controlsPanel.add(p1up, gbc);
      	 
    	gbc.gridy = 2;
    	controlsPanel.add(p1left, gbc);
      	 
    	gbc.gridy = 3;
    	controlsPanel.add(p1down, gbc);
      	 
    	gbc.gridy = 4;
    	controlsPanel.add(p1right, gbc);
      	 
    	//gbc.gridwidth = 1;
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(10, 100, 10, 10);
    	controlsPanel.add(p2Controls, gbc);
      	 
    	gbc.gridy = 1;
    	controlsPanel.add(p2up, gbc);
      	 
    	gbc.gridy = 2;
    	controlsPanel.add(p2left, gbc);
      	 
    	gbc.gridy = 3;
    	controlsPanel.add(p2down, gbc);
      	 
    	gbc.gridy = 4;
    	controlsPanel.add(p2right, gbc);
      	 
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.gridwidth = 3;
    	gbc.insets = new Insets(0, 0, 0, 0);
    	helpPanel.add(controlsPanel);
 
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	gbc.gridwidth = 1;
    	//gbc.weightx = 0;
    	gbc.fill = GridBagConstraints.REMAINDER;
    	gbc.insets = new Insets(10, 0, 0 ,0);
    	helpPanel.add(backToMenu, gbc);
   
    	return helpPanel;
	}
  	 
  	 
	/**
 	* Creates a setting panel for the game menu.
 	* @return Panel containing settings.
 	*/
	private JPanel createSettingsPanel() {
    	JPanel settingsPanel = new JPanel(new GridBagLayout());
   
    	settingsPanel.setLayout(new GridBagLayout());
    	settingsPanel.setBorder(new EmptyBorder(200,0,0,0));
   
    	JPanel boardSize = new JPanel();
    	themeJPanel(boardSize);
    	 
     	 
    	// Board Size options as radio buttons
    	JLabel boardSizeHeading = new JLabel("Choose board Size:");
    	JRadioButton square10 = new JRadioButton("10 x 10 (Easy).");
    	JRadioButton square20 = new JRadioButton("20 x 20 (Medium).");
    	JRadioButton square30 = new JRadioButton("30 x 30 (Hard).");
    	JRadioButton square40 = new JRadioButton("40 x 40 (Expert).");
    	JRadioButton square50 = new JRadioButton("50 x 50 (ULTRA).");
    	
    	//For fun
    	JRadioButton square100 = new JRadioButton("100 x 100 (ULTRA-EXTREME).");
     	 
    	int currSize = window.getGridSize();
    	if (currSize == 10) {
        	square10.setSelected(true);
    	} else if (currSize == 20) {
        	square20.setSelected(true);
    	} else if (currSize == 30) {
        	square30.setSelected(true);
    	} else if (currSize == 40) {
        	square40.setSelected(true);
    	} else if (currSize == 50) {
        	square50.setSelected(true);
    	} 
     	 
    	square10.setActionCommand("10");
    	square20.setActionCommand("20");
    	square30.setActionCommand("30");
    	square40.setActionCommand("40");
    	square50.setActionCommand("50");
    	square100.setActionCommand("100");
   
    	this.gridSizeGroup = new ButtonGroup();
    	gridSizeGroup.add(square10);
    	gridSizeGroup.add(square20);
    	gridSizeGroup.add(square30);
    	gridSizeGroup.add(square40);
    	gridSizeGroup.add(square50);  
    	gridSizeGroup.add(square100);
      	 
    	themeLabelHeading(boardSizeHeading);
    	themeRadioButton(square10);
    	themeRadioButton(square20);  
    	themeRadioButton(square30);  
    	themeRadioButton(square40);  
    	themeRadioButton(square50);
    	themeRadioButton(square100);
      	 
    	//boardSize.setOpaque(true);
      	 
    	JButton save = new JButton("Save", new ImageIcon("Images/noIcon.png"));
    	themeButton(save);
    	save.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
            	String gridSize = gridSizeGroup.getSelection().getActionCommand();
            	window.setGridSize(Integer.parseInt(gridSize));
            	mainMenu.removeAll();
            	JPanel mainMenuList = createMainMenu();
   
            	mainMenuList.setOpaque(false);
            	mainMenuList.validate();
   
            	mainMenu.add(mainMenuList);
            	mainMenu.add(bgPanel);
              	 
            	mainMenu.validate();
            	mainMenu.repaint();  
        	}
    	});
      	 
    	boardSize.setLayout(new GridBagLayout());
    	boardSize.setBorder(new LineBorder(Color.BLACK, 3));
      	 
    	// Create a constraint
    	GridBagConstraints gbc = new GridBagConstraints();
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(10, 10, 10, 10);
    	boardSize.add(boardSizeHeading, gbc);
      	 
    	gbc.gridy = 1;
    	boardSize.add(square10, gbc);
      	 
    	gbc.gridy = 2;
    	boardSize.add(square20, gbc);
      	 
    	gbc.gridy = 3;
    	boardSize.add(square30, gbc);
      	 
    	gbc.gridy = 4;
    	boardSize.add(square40, gbc);
      	 
    	gbc.gridy = 5;
    	boardSize.add(square50, gbc);
    	
    	gbc.gridy = 6;
    	boardSize.add(square100, gbc);
      	 
    	gbc.fill = GridBagConstraints.HORIZONTAL;
    	gbc.insets = new Insets(10, 10, 10, 10);
    	settingsPanel.add(boardSize);
      	 
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	settingsPanel.add(save, gbc);
      	 
    	return settingsPanel;
	}
   
   
	/**
 	* Creates a back button that can be used in any menu.
 	* @return Back JButton
 	*/
	private JButton createBackButton() {
    	JButton backButton = new JButton("Return.", new ImageIcon("Images/noIcon.png"));
      	 
    	backButton.addActionListener(new ActionListener() {
          	 
        	@Override
        	public void actionPerformed(ActionEvent e) {            	 
            	mainMenu.removeAll();
            	JPanel mainMenuList = createMainMenu();
   
            	mainMenuList.setOpaque(false);
            	mainMenuList.validate();
   
            	mainMenu.add(mainMenuList);
            	mainMenu.add(bgPanel);
              	 
            	mainMenu.validate();
            	mainMenu.repaint();	 
        	}
    	});
      	 
      	 
    	return backButton;
	}
  	 
  	 
	/**
 	* Themes the radio buttons to be consistent with the user interface.
 	* @param b Button to be themed.
 	*/
	private void themeRadioButton(JRadioButton b) {
    	Font pokemonFont = null;
      	 
    	try {
         	pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")).deriveFont(24f);
         	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")));
    	} catch (IOException|FontFormatException e) {}
      	 
    	b.setOpaque(true);
    	b.setFont(pokemonFont);
    	b.setBackground(Color.WHITE);
	}
  	 
  	 
	/**
 	* Themes a button in a particular style.
 	* @param b Button to be themed.
 	*/
	private void themeButton (JButton b) {
    	Font pokemonFont = null;
      	 
    	try {
         	pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")).deriveFont(30f);
         	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")));
    	} catch (IOException|FontFormatException e) {
         	//System.out.println("FONT NOT FOUND");
    	}
      	 
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
  	 
  	 
	/**
 	* Adds styling to a label to fit the Pokemon theme (for headings).
 	* @param l Label to be styled.
 	*/
	private void themeLabelHeading (JLabel l) {
    	Font pokemonFont = null;
      	 
    	try {
         	pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")).deriveFont(30f);
         	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")));
    	} catch (IOException|FontFormatException e) {
         	//System.out.println("FONT NOT FOUND");
    	}
      	 
    	l.setOpaque(true);
    	l.setFont(pokemonFont);
    	l.setBackground(Color.WHITE);
    	l.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
	}
  	 
  	 
	/**
 	* Adds styling to a label to fit the Pokemon theme (for smaller text).
 	* @param l Label to be styled.
 	*/
	private void themeLabelOption (JLabel l) {
    	Font pokemonFont = null;
      	 
    	try {
         	pokemonFont = Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")).deriveFont(24f);
         	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         	ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Fonts/pokemon_pixel_font.ttf")));
    	} catch (IOException|FontFormatException e) {
         	//System.out.println("FONT NOT FOUND");
    	}
      	 
    	l.setOpaque(true);
    	l.setFont(pokemonFont);
    	l.setBackground(Color.WHITE);
	}
  	 
  	 
	/**
 	* Themes a JPanel in the Pokemon styling.
 	* @param p Panel to be styled.
 	*/
	private void themeJPanel(JPanel p) {
    	p.setBackground(Color.WHITE);
    	p.setBorder(new LineBorder(Color.BLACK, 3));
    	p.setOpaque(true);
	}
}

