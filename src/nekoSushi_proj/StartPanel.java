package nekoSushi_proj;

/**
 * This program displays the main menu of Neko Sushi.
 * @author Sandra Le
 */

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


/**
 * This class implements the main menu, which consists of a background image with the Neko Sushi logo, a start button, and a tutorial button.
 */

public class StartPanel extends JPanel{
	public static final int OG_ENTER_WIDTH = 200;
	public static final int OG_ENTER_HEIGHT = 70;
	public static final int OG_FISH_WIDTH = 100;
	public static final int OG_TUTORIAL_WIDTH = 275;
	public static final int OG_TUTORIAL_HEIGHT = 70;
	
	public static final int OG_Y_SPACE = 16;
	public static final int Y_ENTER = NekoSushiTest.getNewSize(761);
	public static final int Y_TUTORIAL = NekoSushiTest.getNewSize(877);
	
	private Image backgroundImage;
	
	private JLabel enterFish;
	private JLabel enterLabel;
	private JLabel fish1Label;
	private JLabel fish1OutlinedLabel;
	
	private JLabel tutorialFish;
	private JLabel tutorialLabel;
	private JLabel fish2Label;
	private JLabel fish2OutlinedLabel;	

	/**
	 * Constructs the main menu panel, which consists of a background graphic, an enter button, and a tutorial button.
	 */
	public StartPanel() {	
		try {
			BufferedImage bgImage = ImageIO.read(getClass().getClassLoader().getResource("background w logo.png"));
			backgroundImage = bgImage.getScaledInstance(NekoSushiTest.DEFAULT_FRAME_WIDTH, NekoSushiTest.DEFAULT_FRAME_HEIGHT, Image.SCALE_DEFAULT);
			
			enterLabel = new JLabel(nameToImgIcon("enter.png", OG_ENTER_WIDTH, OG_ENTER_HEIGHT), JLabel.CENTER);
			fish1Label = new JLabel(nameToImgIcon("fish (no outline).png", OG_FISH_WIDTH, OG_FISH_WIDTH), JLabel.CENTER);
			fish1OutlinedLabel = new JLabel(nameToImgIcon("fish (outline).png", OG_FISH_WIDTH, OG_FISH_WIDTH), JLabel.CENTER);
			
			tutorialLabel = new JLabel(nameToImgIcon("tutorial.png", OG_TUTORIAL_WIDTH, OG_TUTORIAL_HEIGHT), JLabel.CENTER);
			fish2Label = new JLabel(nameToImgIcon("fish (no outline).png", OG_FISH_WIDTH, OG_FISH_WIDTH), JLabel.CENTER);
			fish2OutlinedLabel = new JLabel(nameToImgIcon("fish (outline).png", OG_FISH_WIDTH, OG_FISH_WIDTH));		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fish1OutlinedLabel.setVisible(false);
		fish2OutlinedLabel.setVisible(false);
		
		enterFish = new JLabel();
		enterFish.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the game panel when the enter label is pressed by the mouse button.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				NekoSushiTest.frame.add(NekoSushiTest.game);
				NekoSushiTest.game.setVisible(true);
			}
			/**
			 * Displays the corresponding outlined fish label when the mouse cursor enters the enter label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				fish1OutlinedLabel.setVisible(true);
				fish1Label.setVisible(false);
			}
			/**
			 * Displays the corresponding fish label when the mouse cursor exits the enter label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				fish1OutlinedLabel.setVisible(false);
				fish1Label.setVisible(true);
			}
		});
		
		tutorialFish = new JLabel();		
		tutorialFish.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the tutorial panel when the tutorial label is pressed by the mouse button.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				setVisible(false);
				NekoSushiTest.frame.add(NekoSushiTest.tutorial);
				NekoSushiTest.tutorial.setVisible(true);
				NekoSushiTest.tutorial.getBackYellow().setVisible(false);
				NekoSushiTest.tutorial.getBackRed().setVisible(true);
			}
			/**
			 * Displays the corresponding outlined fish label when the mouse cursor enters the tutorial label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				fish2OutlinedLabel.setVisible(true);
				fish2Label.setVisible(false);
			}
			/**
			 * Displays the corresponding fish label when the mouse cursor exits the tutorial label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				fish2OutlinedLabel.setVisible(false);
				fish2Label.setVisible(true);
			}
		});
	
//		addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				if(isVisible()) {
//					int width = Math.min(getWidth(), getHeight());
//				    Insets i = NekoSushiTest.frame.getInsets();
//				    NekoSushiTest.frame.setSize(width + i.left + i.right, width + i.top + i.bottom);
//				}
//			    
//			}
//		});
		
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
	 * Displays the background image, enter button, and tutorial button.
	 * @param g - the graphics tool
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
//		float scale = Math.min((float) getWidth() / backgroundImage.getWidth(this), (float) getHeight() / backgroundImage.getHeight(this));
//
//		int displayWidth = (int) (backgroundImage.getWidth(this) * scale);
//		int displayHeight = (int) (backgroundImage.getHeight(this) * scale);
//
//		g.drawImage(backgroundImage, getWidth()/2 - displayWidth/2, getHeight()/2 - displayHeight/2, displayWidth, displayHeight, null);
		
		
		//background
        g2D.drawImage(backgroundImage, 0, 0, null);


		int newFishWidth = getNewSize(OG_FISH_WIDTH);
		int tutorialWidth = tutorialLabel.getWidth();
		int enterWidth = enterLabel.getWidth();

		//enter with fish label
		add(enterFish);
		enterFish.setBounds(getXToCenter(newFishWidth + enterWidth),Y_ENTER, enterWidth + newFishWidth, Math.max(newFishWidth, getNewSize(OG_ENTER_HEIGHT)));
		
		enterLabel.setBounds(newFishWidth,0, getNewSize(OG_ENTER_WIDTH), getNewSize(OG_ENTER_HEIGHT));
		fish1Label.setBounds(0,0, newFishWidth, newFishWidth);
		fish1OutlinedLabel.setBounds(0,0, newFishWidth, newFishWidth);
		
		enterFish.add(fish1Label);
		enterFish.add(enterLabel);
		enterFish.add(fish1OutlinedLabel);
		
		//tutorial with fish label
		add(tutorialFish);
		tutorialFish.setBounds(getXToCenter(newFishWidth + tutorialWidth),Y_TUTORIAL, tutorialWidth + newFishWidth, Math.max(newFishWidth, getNewSize(OG_TUTORIAL_HEIGHT)));
		
		
		tutorialLabel.setBounds(newFishWidth,0, getNewSize(OG_TUTORIAL_WIDTH), getNewSize(OG_TUTORIAL_HEIGHT));
		fish2Label.setBounds(0,0, newFishWidth, newFishWidth);
		fish2OutlinedLabel.setBounds(0,0, newFishWidth, newFishWidth);
		
		tutorialFish.add(tutorialLabel);
		tutorialFish.add(fish2Label);
		tutorialFish.add(fish2OutlinedLabel);
	}
	
}
