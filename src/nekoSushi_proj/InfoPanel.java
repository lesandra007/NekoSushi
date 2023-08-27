package nekoSushi_proj;

/**
 * This program displays information about a Neko Sushi item and options to add to cart or cancel.
 * @author Sandra Le
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class implements the information panel, which consists of the item, its name, and options to add to cart or cancel.
 */
public class InfoPanel extends JPanel implements ChangeListener {
	public static final int OG_Y_ITEM = 183-45;
	public static final int OG_Y_NAME = 583;
	
	public static final int OG_CART_WIDTH = 300;
	public static final int OG_CART_HEIGHT = 70;
	public static final int OG_CANCEL_WIDTH = 200;
	public static final int OG_CANCEL_HEIGHT = 70;
	public static final int OG_Y_BUTTONS = 727;
	public static final int OG_SPACE_WIDTH = 171;

	private DataModel model;
	private Item currItem;
	private JLabel currItemLabel;
	private JLabel nameLabel;
	
	private JLabel cartLabel;
	private JLabel cancelLabel;
	private JPanel buttonPanel;
	
	private Image backgroundImage;

	/**
	 * Constructs the panel, which consists of background image, the item, the item's name, and options to add to cart or cancel.
	 * @param model - the model that manages the data
	 */
	public InfoPanel(DataModel model) {
		this.model = model;
		currItem = model.getCurrItem();
		currItemLabel = currItem.getInfoLabel();
		nameLabel = currItem.getNameInfoLabel();
		try {
			BufferedImage bufferedBackground = ImageIO.read(getClass().getClassLoader().getResource("description background.png"));
			backgroundImage = bufferedBackground.getScaledInstance(NekoSushiTest.DEFAULT_FRAME_WIDTH, NekoSushiTest.DEFAULT_FRAME_HEIGHT, Image.SCALE_DEFAULT);
			cartLabel = new JLabel(nameToImgIcon("add to cart.png", OG_CART_WIDTH, OG_CART_HEIGHT), JLabel.CENTER);
			cancelLabel = new JLabel(nameToImgIcon("cancel.png", OG_CANCEL_WIDTH, OG_CANCEL_HEIGHT), JLabel.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cartLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				 setVisible(false);
				 NekoSushiTest.game.setVisible(true);
				 model.prepResumeGame();
				 model.claimItem(currItem);
				 model.setCurrItem(null);
				 model.update();
				 removeAll();
			 }
		});
		
		cancelLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				NekoSushiTest.game.setVisible(true);
				removeAll();
			}
		});
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(cartLabel);
		buttonPanel.add(Box.createRigidArea(new Dimension(NekoSushiTest.getNewSize(OG_SPACE_WIDTH), 0)));
		buttonPanel.add(cancelLabel);
		buttonPanel.setOpaque(false);
		add(buttonPanel);
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
	 * Calculates [expected frame size] * (measurement/[original frame width])
	 * @param measurement - measurement to convert
	 * @return [expected frame size] * (measurement/[original frame width])
	 */
	public int getNewSize(int measurement) {
		return NekoSushiTest.getNewSize(measurement);
	}
	
	/**
	 * Returns the x coordinate needed to center an object with the specified width.
	 * @param width - the width of the object to center
	 * @return the x coordinate needed to center an object with the specified width
	 */
	public int getXToCenter(int width) {
		return (getWidth()/2) - (width/2);
	}
	
	/**
	 * Displays the background image, the item, the item's name, and options to add to cart or cancel.
	 * @param g - the graphics tool
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(backgroundImage, 0, 0, null);
		
		int itemWidth = getNewSize(currItem.getOgItemInfoWidth());
		int itemHeight = getNewSize(currItem.getOgItemInfoHeight());
		
		int nameWidth = getNewSize(currItem.getOgNameInfoWidth());
		int nameHeight = getNewSize(currItem.getOgNameInfoHeight());
		
		int cartWidth = getNewSize(OG_CART_WIDTH);
		int cartHeight = getNewSize(OG_CART_HEIGHT);
		int cancelWidth = getNewSize(OG_CANCEL_WIDTH);
		int panelWidth = cartWidth + getNewSize(OG_SPACE_WIDTH) + cancelWidth;
		
		currItemLabel.setBounds(getXToCenter(itemWidth),getNewSize(OG_Y_ITEM), itemWidth, itemHeight);
		add(currItemLabel);
//		currItemLabel.setOpaque(true);
		
		nameLabel.setBounds(getXToCenter(nameWidth),getNewSize(OG_Y_NAME), nameWidth, nameHeight);
		add(nameLabel);
		
		buttonPanel.setBounds(getXToCenter(panelWidth), getNewSize(OG_Y_BUTTONS), panelWidth, cartHeight);
		add(buttonPanel);
		
//		buttonPanel.setOpaque(true);
	}
	
	/**
	 * Updates the current item to be displayed.
	 * @param e - the change event
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		currItem = model.getCurrItem();
		if(currItem == null) {
			currItemLabel = null;
			nameLabel = null;
		}
		else {
			currItemLabel = currItem.getInfoLabel();
			nameLabel = currItem.getNameInfoLabel();
		}

	}

}
