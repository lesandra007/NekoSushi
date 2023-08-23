package nekoSushi_proj;

/**
 * This program displays the sushi conveyor belt simulator.
 * @author Sandra Le
 */

import java.awt.Font;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class implements the sushi conveyor belt simulator, which consists of a plated Neko Sushi item that moves across the screen.
 */
public class GamePanel extends JPanel implements ChangeListener {
	private DataModel model;
	private int count;
	private Image backgroundImage;
	private Item currItem;
	private Item plate;
	private Item lid;
	
	//Neko Sushi and holder
	public static final int OG_PLATE_Y = 592;
	public static final int OG_LID_Y = 333;
	public static final int OG_ITEM_Y = 462;
	
	//plate count and check out button
	public static final int OG_PLATE_COUNT_WIDTH = 400;
	public static final int OG_PLATE_COUNT_HEIGHT = 100;
	public static final int OG_PLATE_COUNT_X = 61-7;
	public static final int OG_PLATE_COUNT_Y = 65-6;
	public static final int OG_COUNT_WIDTH = 100;
	public static final int OG_COUNT_X = 336;
	public static final int OG_COUNT_Y = 63-5;
	public static final int OG_CHECK_OUT_WIDTH = 300;
	public static final int OG_CHECK_OUT_HEIGHT = 100;
	public static final int OG_CHECK_OUT_X = 750-15;
	public static final int OG_CHECK_OUT_Y = 64-6;
	
	//conveyor belt
	public static final int OG_BELT_WIDTH = 1600;
	public static final int OG_BELT_HEIGHT = 650;
	public static final int OG_BELT_Y = 527-25;
	
	public static final int OG_BELT_LINES_WIDTH = 1600;
	public static final int OG_BELT_LINES_HEIGHT = 650;
	public static final int OG_BELT_LINES_Y = 580-51-25;

//	private Font pressStart2P;
	
	private JLabel plateCount;
	private JLabel countLabel;
	private JLabel yellowCOButton;
	private JLabel redCOButton;
	private JLabel conveyor;
	private JLabel beltLines1;
	private JLabel beltLines2;
	
	private JLabel plateLabel;
	private JLabel currLabel;
	private JLabel lidLabel;
	
	/**
	 * Constructs the panel, which consists of a moving Neko Sushi item that can be claimed.
	 * @param model - the model that manages the data
	 */
	public GamePanel(DataModel model) {
		this.model = model;
		model.randomCurrItem();
		currItem = model.getCurrItem();
		currLabel = currItem.getGameLabel();
		plate = model.getPlate();
		plateLabel = plate.getGameLabel();
		lid = model.getLid();
		lidLabel = lid.getGameLabel();
		count = model.getCartSize();
		setLayout(null);
		
		try {
			BufferedImage bufferedBackground = ImageIO.read(getClass().getClassLoader().getResource("background.png"));
			backgroundImage = bufferedBackground.getScaledInstance(NekoSushiTest.DEFAULT_FRAME_WIDTH, NekoSushiTest.DEFAULT_FRAME_HEIGHT, Image.SCALE_DEFAULT);

			plateCount = new JLabel(nameToImgIcon("plate count.png", OG_PLATE_COUNT_WIDTH, OG_PLATE_COUNT_HEIGHT), JLabel.CENTER);

			yellowCOButton = new JLabel(nameToImgIcon("check out (yellow).png", OG_CHECK_OUT_WIDTH, OG_CHECK_OUT_HEIGHT), JLabel.CENTER);
			redCOButton = new JLabel(nameToImgIcon("check out (red).png", OG_CHECK_OUT_WIDTH, OG_CHECK_OUT_HEIGHT), JLabel.CENTER);

			conveyor = new JLabel(nameToImgIcon("conveyor belt (no lines).png", OG_BELT_WIDTH, OG_BELT_HEIGHT), JLabel.CENTER);
			beltLines1 = new JLabel(nameToImgIcon("conveyor belt (lines).png", OG_BELT_LINES_WIDTH, OG_BELT_LINES_HEIGHT), JLabel.CENTER);
			beltLines2 = new JLabel(nameToImgIcon("conveyor belt (lines).png", OG_BELT_LINES_WIDTH, OG_BELT_LINES_HEIGHT), JLabel.CENTER);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Neko Sushi and holder
		plateLabel.setBounds(-getNewSize(Item.PLATE_WIDTH), getNewSize(OG_PLATE_Y), getNewSize(Item.PLATE_WIDTH), getNewSize(Item.PLATE_HEIGHT));
		lidLabel.setBounds(-getNewSize(Item.PLATE_WIDTH), getNewSize(OG_LID_Y), getNewSize(Item.LID_WIDTH), getNewSize(Item.LID_HEIGHT));
		currLabel.setBounds(-getNewSize(Item.PLATE_WIDTH), getNewSize(OG_ITEM_Y), getNewSize(Item.GAME_WIDTH), getNewSize(Item.GAME_HEIGHT));
		
		//belt lines
		beltLines1.setLocation(-10, getNewSize(OG_BELT_LINES_Y));	
		beltLines2.setLocation(getWidth() + getNewSize(OG_BELT_LINES_WIDTH), getNewSize(OG_BELT_LINES_Y));	
		
		//plate count
		countLabel = new JLabel(String.valueOf(count),SwingConstants.CENTER);
		countLabel.setFont(new Font(countLabel.getName(), Font.BOLD, 22));
		
		//check out buttons
		redCOButton.setVisible(false);
		redCOButton.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the receipt panel when the checkout is pressed by the mouse button.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				model.reset(); //reset positioning of plate, lid, item
				model.randomCurrItem();
				setVisible(false);
				NekoSushiTest.frame.add(NekoSushiTest.receipt);
				NekoSushiTest.receipt.setVisible(true);	
			}
			/**
			 * Displays the yellow check out label when the mouse cursor exits the red check out label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				redCOButton.setVisible(false);
				yellowCOButton.setVisible(true);
			}
		});
		yellowCOButton.addMouseListener(new MouseAdapter() {
			/**
			 * Displays red check out label when the mouse cursor enters the yellow check out label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				redCOButton.setVisible(true);
				yellowCOButton.setVisible(false);
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
		Image img = bufferedimg.getScaledInstance(getNewSize(ogWidth), getNewSize(ogHeight), Image.SCALE_DEFAULT);
		return new ImageIcon(img);
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
	 * Calculates [expected frame size] * (measurement/[original frame width])
	 * @param measurement - measurement to convert
	 * @return [expected frame size] * (measurement/[original frame width])
	 */
	public int getNewSize(int measurement) {
		return NekoSushiTest.getNewSize(measurement);
	}

	/**
	 * Displays items moving across the panel.
	 */
	public void animationLoop() {
		while(true) {
			if(isVisible()) {
				try {
				Thread.sleep(1); //wait 10 milliseconds
				}
				catch(InterruptedException ex){
					Thread.currentThread().interrupt();
				}
				repaint();
			}	
		}
	}
	
	/**
	 * Moves the image of this item to the right by 1 pixel.
	 */
	public void moveBeltLines() {
		beltLines1.setLocation(beltLines1.getX() + 1,beltLines1.getY());
		beltLines2.setLocation(beltLines2.getX() + 1,beltLines2.getY());
		if(beltLines1.getX() == 0) {
			beltLines2.setLocation(-beltLines2.getWidth(),beltLines2.getY());
		}
		else if(beltLines2.getX() == 0) {
			beltLines1.setLocation(-beltLines1.getWidth(),beltLines1.getY());
		}
	}
	
	/**
	 * Displays the background image, plate, and current item.
	 * @param g - the graphics tool
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(backgroundImage, 0, 0, null);
		//move item
		model.move();
		
		//lid
		lidLabel.setBounds(lid.getX(), lid.getY(), getNewSize(Item.LID_WIDTH), getNewSize(Item.LID_HEIGHT));	
		add(lidLabel);
		
		//current Neko Sushi item
		if(currItem != null) {
			currLabel.setBounds(currItem.getX(), currItem.getY(), getNewSize(Item.GAME_WIDTH), getNewSize(Item.GAME_HEIGHT));
			add(currLabel);
		}	
		
		//plate
		plateLabel.setBounds(plate.getX(), plate.getY(), getNewSize(Item.PLATE_WIDTH), getNewSize(Item.PLATE_HEIGHT));	
		add(plateLabel);
		
		//conveyor belt lines
		beltLines1.setBounds(beltLines1.getX(), getNewSize(OG_BELT_LINES_Y), getNewSize(OG_BELT_LINES_WIDTH), getNewSize(OG_BELT_LINES_HEIGHT));	
		add(beltLines1);
		beltLines2.setBounds(beltLines2.getX(), getNewSize(OG_BELT_LINES_Y), getNewSize(OG_BELT_LINES_WIDTH), getNewSize(OG_BELT_LINES_HEIGHT));	
		add(beltLines2);
		
		//move conveyor belt lines
		moveBeltLines();
		
	 	//plate count
		countLabel.setBounds(getNewSize(OG_COUNT_X),getNewSize(OG_COUNT_Y),getNewSize(OG_COUNT_WIDTH), getNewSize(OG_PLATE_COUNT_HEIGHT));
	 	add(countLabel);
	 	plateCount.setBounds(getNewSize(OG_PLATE_COUNT_X),getNewSize(OG_PLATE_COUNT_Y),getNewSize(OG_PLATE_COUNT_WIDTH), getNewSize(OG_PLATE_COUNT_HEIGHT));
	 	add(plateCount);
	 	
	 	
	 	//check out buttons
	 	yellowCOButton.setBounds(getNewSize(OG_CHECK_OUT_X),getNewSize(OG_CHECK_OUT_Y),getNewSize(OG_CHECK_OUT_WIDTH), getNewSize(OG_CHECK_OUT_HEIGHT));
	 	add(yellowCOButton);
	 	redCOButton.setBounds(getNewSize(OG_CHECK_OUT_X),getNewSize(OG_CHECK_OUT_Y),getNewSize(OG_CHECK_OUT_WIDTH), getNewSize(OG_CHECK_OUT_HEIGHT));
	 	add(redCOButton);
	 	
	 	//conveyor 
	 	conveyor.setBounds(getXToCenter(getNewSize(OG_BELT_WIDTH)),getNewSize(OG_BELT_Y),getNewSize(OG_BELT_WIDTH), getNewSize(OG_BELT_HEIGHT));
	 	add(conveyor);
	}
	
	/**
	 * Updates the item to be displayed.
	 * @param e - the change event
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		currItem = model.getCurrItem();
		//reentering frame
		if(currItem != null) {
			currLabel = currItem.getGameLabel();
			currLabel.setBounds(-getNewSize(Item.PLATE_WIDTH), getNewSize(OG_ITEM_Y), getNewSize(Item.GAME_WIDTH), getNewSize(Item.GAME_HEIGHT));
			
//			plateLabel.setBounds(-getNewSize(Item.PLATE_WIDTH), getNewSize(OG_PLATE_Y), getNewSize(Item.PLATE_WIDTH), getNewSize(Item.PLATE_HEIGHT));
//			lidLabel.setBounds(-getNewSize(Item.PLATE_WIDTH), getNewSize(OG_LID_Y), getNewSize(Item.LID_WIDTH), getNewSize(Item.LID_HEIGHT));
			//reset bounds of plate/lid for restart
		}
		//added to cart
		else {
			currLabel = null;
		}
		count = model.getCartSize();
		countLabel.setText(String.valueOf(count));
		
	}

}
