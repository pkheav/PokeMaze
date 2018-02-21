import java.awt.Image;
import javax.swing.ImageIcon;

public class MazeTile {
	private ImageIcon tile;
	private ImageIcon fog;
	private ImageIcon hint;
	private String type;
	private Image onMap;
	
	/**
	 * Creates an tile for the maze
	 * @param tileRep	The type of tile
	 * @param mazeType	The theme of the maze
	 */
	public MazeTile (char tileRep, int mazeType) {
		fog = new ImageIcon("Images/fog.png");
		hint = new ImageIcon("Images/pokeballIcon.png");
		if (tileRep == '*') {
			tile = new ImageIcon(String.format("Images/maps/%d/wall.png", mazeType));
			this.onMap = tile.getImage();
			this.type = "wall";
		} else {
			tile = new ImageIcon(String.format("Images/maps/%d/floor.png", mazeType));
			this.type = "floor";
			this.onMap = tile.getImage();
		}
	}
	
	/**
	 * Swaps the image of the tile
	 * @param type	The type of tile
	 */
	public void onMap(String type) {
		if (type.equals("fog")) {
			onMap = fog.getImage();
		} else if (type.equals("hint")) {
			onMap = hint.getImage();
		} else {
			onMap = tile.getImage();
		}
	}

	/**
	 * @return the tile
	 */
	public Image getOnMap() {
		return onMap;
	}
	
	/**
	 * @return the tile
	 */
	public ImageIcon getTile() {
		return tile;
	}

	/**
	 * @return the fog
	 */
	public ImageIcon getFog() {
		return fog;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
}


