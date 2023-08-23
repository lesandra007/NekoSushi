package nekoSushi_proj;

/**
 * This program represents a Neko Sushi item.
 * @author Sandra Le
 */

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * This class represents a Neko Sushi item.
 */
public class Item implements Cloneable {
	//game
	public static final int LID_WIDTH = 800;
	public static final int LID_HEIGHT = 400;
	public static final int PLATE_WIDTH = 800;
	public static final int PLATE_HEIGHT = 200;
	public static final int GAME_WIDTH = 400;
	public static final int GAME_HEIGHT = 250;
	
	//info
	public static final int OG_INFO_WIDTH = 700;
	public static final int OG_INFO_HEIGHT = 450;
//	public static final int OG_INFO_NAME_WIDTH = 450;
//	public static final int OG_INFO_NAME_HEIGHT = 100;
	
	//receipt
//	public static final int OG_RECEIPT_NAME_WIDTH = 200;
//	public static final int OG_RECEIPT_NAME_HEIGHT = 50;
	
	//name
	public static final double NAME_RECEIPT_OVER_INFO_WIDTH = 200/450.0;
	public static final double NAME_RECEIPT_OVER_INFO_HEIGHT = 50/100.0;
	
	private String name;
	private int count; 
	
	private JLabel nameInfoLabel;
	private int ogNameInfoWidth;
	private int ogNameInfoHeight;
	private JLabel nameReceiptLabel;
	private JLabel gameLabel;
	private JLabel infoLabel;
	private boolean isClaimed;
	
	private DataModel model;
	
	private int x;
	private int y;
	
	/**
	 * Constructs an empty NekoSushi holder part.
	 * @param model - the model that manages the data
	 * @param part - the part of the NekoSushi holder
	 * @param fileName - the name of the file holding the plate image
	 */
	public Item(DataModel model, Holder part, String fileName) {
		this.model = model;
		if(part == Holder.LID) {
			this.name = "lid";
			try {
				gameLabel = new JLabel(nameToImgIcon(fileName, LID_WIDTH, LID_HEIGHT), JLabel.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		else if(part == Holder.PLATE) {
			this.name = "plate";
			try {
				gameLabel = new JLabel(nameToImgIcon(fileName, PLATE_WIDTH, PLATE_HEIGHT), JLabel.CENTER);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		x = gameLabel.getX();
		y = gameLabel.getY();
		
		gameLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(model.getCurrItem() != null) {
					//show description screen
					NekoSushiTest.game.setVisible(false);
					NekoSushiTest.frame.add(NekoSushiTest.info);
					NekoSushiTest.info.setVisible(true);
				}	
			 }
		});
		
	}
	
	/**
	 * Constructs a Neko Sushi item.
	 * @param model - the model that manages the data
	 * @param name - the name of this item
	 * @param nameFile - the name of the file holding the name image
	 * @param itemFile - the name of the file holding the item image
	 */
	public Item(DataModel model, String name, String nameFile, int nameInfoOGWidth, int nameInfoOGHeight, String itemFile) {
		this.model = model;
		this.name = name;
		ogNameInfoWidth = nameInfoOGWidth;
		ogNameInfoHeight = nameInfoOGHeight;
		try {
			gameLabel = new JLabel(nameToImgIcon(itemFile, GAME_WIDTH, GAME_HEIGHT), JLabel.CENTER);
			infoLabel = new JLabel(nameToImgIcon(itemFile, OG_INFO_WIDTH, OG_INFO_HEIGHT), JLabel.CENTER);
			nameInfoLabel =  new JLabel(nameToImgIcon(nameFile, nameInfoOGWidth, nameInfoOGHeight), JLabel.CENTER);
			nameReceiptLabel =  new JLabel(nameToImgIcon(nameFile, (int)Math.round(nameInfoOGWidth*NAME_RECEIPT_OVER_INFO_WIDTH), (int)Math.round(nameInfoOGHeight*NAME_RECEIPT_OVER_INFO_HEIGHT)), JLabel.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		isClaimed = false;
		x = gameLabel.getX();
		y = gameLabel.getY();
		
		gameLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//show description screen
				NekoSushiTest.game.setVisible(false);
				NekoSushiTest.frame.add(NekoSushiTest.info);
				NekoSushiTest.info.setVisible(true);	
			 }
		});
	}
	
	/**
	 * Converts image under the named file to be an ImageIcon while maintaining aspect ratio.
	 * @param name - name of file holding image
	 * @param ogWidth - the original width of the image during design
	 * @param ogHeight - the original height of the image during design
	 * @return an ImageIcon with a scaled image
	 * @throws IOException
	 */
	public ImageIcon nameToImgIcon(String name, int ogWidth, int ogHeight) throws IOException {
		BufferedImage bufferedimg = ImageIO.read(getClass().getClassLoader().getResource(name));
		Image img = bufferedimg.getScaledInstance(NekoSushiTest.getNewSize(ogWidth), NekoSushiTest.getNewSize(ogHeight), Image.SCALE_DEFAULT);
		return new ImageIcon(img);
	}
	
	/**
	 * Returns the name of this item.
	 * @return the name of this item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the count of this item in cart.
	 * @return the count of this item in cart
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Sets the count of this item in cart.
	 */
	public void upCount() {
		count++;
	}
	
	public int getOgNameInfoWidth() {
		return ogNameInfoWidth;
	}
	
	public int getOgNameInfoHeight() {
		return ogNameInfoHeight;
	}
	
	/**
	 * Determines if item is claimed.
	 * @return true if previous item is claimed, false otherwise
	 */
	public boolean isClaimed() {
		return isClaimed;
	}
	
	/**
	 * Sets value of isClaimed to specified value.
	 * @param claimed - true if previous item was claimed, false otherwise
	 */
	public void setClaimed(boolean claimed) {
		isClaimed = claimed;
	}
	
	/**
	 * Returns the JLabel for this item for the game panel.
	 * @return the JLabel for this item for the game panel
	 */
	public JLabel getGameLabel() {
		return gameLabel;
	}
	
	/**
	 * Returns the JLabel for this item for the information panel.
	 * @return the JLabel for this item for the information panel
	 */
	public JLabel getInfoLabel() {
		return infoLabel;
	}
	
	/**
	 * Returns the JLabel for this item's name for the InfoPanel.
	 * @return the JLabel for this item's name for the InfoPanel
	 */
	public JLabel getNameInfoLabel() {
		return nameInfoLabel;
	}
	
	/**
	 * Returns the JLabel for this item's name for the ReceiptPanel.
	 * @return the JLabel for this item's name for the ReceiptPanel
	 */
	public JLabel getNameReceiptLabel() {
		return nameReceiptLabel;
	}
	
	/**
	 * Returns the x-coordinate for the JLabel for this closed item.
	 * @return the x-coordinate for the JLabel for this closed item
	 */
	public int getX() {
		return  gameLabel.getX();
	}
	
	/**
	 * Sets the x coordinate of the item to the specified value.
	 * @param x - the new x coordinate of the closed dish image
	 */
	public void setX(int x) {
		this.x = x;
		gameLabel.setLocation(x, gameLabel.getY());
	}
	
	/**
	 * Returns the y-coordinate for the JLabel for this closed item.
	 * @return the x-coordinate for the JLabel for this closed item
	 */
	public int getY() {
		return  gameLabel.getY();
	}
	
	/**
	 * Sets the y coordinate of the item to the specified value.
	 * @param x - the new x coordinate of the closed dish image
	 */
	public void setY(int y) {
		this.y = y;
		gameLabel.setLocation(gameLabel.getX(), y);
	}
	
	
	/**
	 * Clones this item.
	 * @return a clone of this item
	 */
	public Item clone() {
		try {
			Item cloned = (Item)super.clone();
			cloned.isClaimed = isClaimed();
			return cloned;
		}
		catch(CloneNotSupportedException e) {
			return null; //won't happen
		}
	}

	/**
	 * Moves the image of this item to the right by 1 pixel.
	 */
	public void move() {
		x++;
		if(x > NekoSushiTest.DEFAULT_FRAME_WIDTH) {
			x = -NekoSushiTest.getNewSize(PLATE_WIDTH);
			model.randomCurrItem();
		}
		setX(x);
	}
	
	/**
	 * Resets the position of the image of this item.
	 */
	public void reset() {
		x = -NekoSushiTest.getNewSize(PLATE_WIDTH);
		setX(x);
	}
}

