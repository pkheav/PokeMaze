import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {
	private int x;
	private int y;
	private int xPrev;
	private int yPrev;
	private Image playerUp;
	private Image playerDown;
	private Image playerLeft;
	private Image playerRight;
	private Image player;
	
	public Player() {
		this.x = 1;
		this.y = 0;
		ImageIcon imgD = new ImageIcon("Images/playerD.png");  //player facing down
		ImageIcon imgU = new ImageIcon("Images/playerU.png");  //player facing up
		ImageIcon imgL = new ImageIcon("Images/playerL.png");  //player facing left
		ImageIcon imgR = new ImageIcon("Images/playerR.png");  //player facing right
		this.playerDown = imgD.getImage();
		this.playerUp = imgU.getImage();
		this.playerLeft = imgL.getImage();
		this.playerRight = imgR.getImage();
		this.player = playerDown;
		this.xPrev = 1;
		this.yPrev = 0;
	}
	
	public Player(int a, int mapWidth, int mapHeight) {
		this.x = mapWidth - 2;
		this.y = mapHeight - 1;
		ImageIcon imgD = new ImageIcon("Images/player2D.png");  //player facing down
		ImageIcon imgU = new ImageIcon("Images/player2U.png");  //player facing up
		ImageIcon imgL = new ImageIcon("Images/player2L.png");  //player facing left
		ImageIcon imgR = new ImageIcon("Images/player2R.png");  //player facing right
		this.playerDown = imgD.getImage();
		this.playerUp = imgU.getImage();
		this.playerLeft = imgL.getImage();
		this.playerRight = imgR.getImage();
		this.player = playerUp;
		this.xPrev = mapWidth - 2;
		this.yPrev = mapHeight - 1;
	}
	
	public Image getPlayer() {		
		return this.player;
	}
	
	public void processMove(int move, boolean legal) {
		yPrev = y;
		xPrev = x;
		
		if (move == KeyEvent.VK_UP || move == KeyEvent.VK_W) {
			changeOrientation(1);
			if (legal) y--;
		} else if (move == KeyEvent.VK_LEFT || move == KeyEvent.VK_A) {
			changeOrientation(2);
			if (legal) x--;
		} else if (move == KeyEvent.VK_RIGHT || move == KeyEvent.VK_D) {
			changeOrientation(3);
			if (legal) x++;
		} else if (move == KeyEvent.VK_DOWN || move == KeyEvent.VK_S) {
			changeOrientation(0);
			if (legal) y++;
		}
	}
	
	public void changeOrientation (int pos) {
		if (pos == 1) {
			player = playerUp;
		} else if (pos == 2) {
			player = playerLeft;
		} else if (pos == 3) {
			player = playerRight;
		} else {
			player = playerDown;
		}
	}
	
	public int xCoord() {
		return this.x;
	}
	
	public int yCoord() {
		return this.y;
	}
	
	public void updateXCoord(int newX) {
		this.xPrev = x;
		this.x = newX;
	}
	
	public void updateYCoord(int newY) {
		this.yPrev = y;
		this.y = newY;
	}

	public int xCoordPrev() {
		return xPrev;
	}
	
	public int yCoordPrev() {
		return yPrev;
	}
}
