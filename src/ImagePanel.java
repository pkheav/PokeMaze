import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImagePanel extends JPanel {
	private Image tile;
	private Image player;
    private Image img;
    
    /**
     * Creates an image panel.
     * @param tile Tile image (lowest level).
     * @param player Player image.
     * @param overlay Image that can be placed on top (highest level).
     */
    public ImagePanel(Image tile, Image player, Image overlay){
    	this.tile = tile;
    	this.player = player;
    	this.img = overlay;
    }
    
    /**
     * Creates an image panel from a file location.
     * @param loc Location of file.
     */
    public ImagePanel(String loc){
      	try {            	 
            img = ImageIO.read(new File(loc));
        } catch (IOException ex) {}
      	player = null;
      	tile = null;
	}

    /**
     * Updates the tile image of this particular ImagePanel.
     * @param tileImage Tile of the panel.
     * @param playerImage Player image of the panel.
     * @param overlay Overlay image of the panel.
     */
	public void updateTile(Image tileImage, Image playerImage, Image overlay) {
		this.tile = tileImage;
		this.player = playerImage;
		this.img = overlay;
	}

    	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //this.getWidth(), this.getHeight(),
        //Render square
        if (tile != null) {
        	g.drawImage(tile, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        
        //If player is present render on top
        if (player != null) {
        	g.drawImage(player, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        
        //Other images
        if (img != null) {
        	g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null); // see javadoc for more info on the parameters
        }
    }

        
}