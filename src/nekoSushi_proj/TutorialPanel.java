package nekoSushi_proj;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This program displays the instructions on how to play Neko Sushi.
 * @author Sandra Le
 *
 */

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class provides the tutorial on how to play Neko Sushi in a user-controlled slideshow manner.
 */
public class TutorialPanel extends JPanel{
	public static final String[] instructions = {"slide1.png","slide2.png","slide3.png","slide4.png",
												 "slide5.png","slide6.png","slide7.png","slide8.png"};
	public static final int OG_TITLE_WIDTH = 300;
	public static final int OG_TITLE_HEIGHT = 100-25;
	public static final int OG_TITLE_X = 412-10;
	public static final int OG_TITLE_Y = 143+16;
	
	public static final int OG_SLIDE_WIDTH = 900;
	public static final int OG_SLIDE_HEIGHT = 450;
	public static final int OG_SLIDE_Y = 200+55;

	public static final int OG_BUTTON_HEIGHT = 100;
	public static final int OG_BUTTON_Y = 710-9;
	
	public static final int OG_EXIT_WIDTH = 250;
	public static final int OG_EXIT_X = 177-12;
	
	public static final int OG_ARROW_WIDTH = 170;
	
	public static final int OG_BACK_X = 600-12;
	public static final int OG_NEXT_X = 785-12;
	
	private int index;
	
	private Image backgroundImage;
	private JLabel title;
	private ArrayList<JLabel> slideList;
	private JLabel currSlide;
	private JLabel exitRed;
	private JLabel exitYellow;
	private JLabel backRed;
	private JLabel backYellow;
	private JLabel nextRed;
	private JLabel nextYellow;
	

	public TutorialPanel() {
		index = 0;
		slideList = new ArrayList<>();
		try {
			BufferedImage bufferedBackground = ImageIO.read(getClass().getClassLoader().getResource("background.png"));
			backgroundImage = bufferedBackground.getScaledInstance(NekoSushiTest.DEFAULT_FRAME_WIDTH, NekoSushiTest.DEFAULT_FRAME_HEIGHT, Image.SCALE_DEFAULT);
			title = new JLabel(nameToImgIcon("tutorial.png", OG_TITLE_WIDTH, OG_TITLE_HEIGHT), JLabel.CENTER);
			//slides
			for(String s: instructions) {
				JLabel slide = new JLabel(nameToImgIcon(s, OG_SLIDE_WIDTH, OG_SLIDE_HEIGHT), JLabel.CENTER);
				slideList.add(slide);
			}
			//buttons
			exitRed = new JLabel(nameToImgIcon("tutorial exit (red).png", OG_EXIT_WIDTH, OG_BUTTON_HEIGHT), JLabel.CENTER);
			exitYellow = new JLabel(nameToImgIcon("tutorial exit (yellow).png", OG_EXIT_WIDTH, OG_BUTTON_HEIGHT), JLabel.CENTER);
			backRed = new JLabel(nameToImgIcon("back button (red).png", OG_ARROW_WIDTH, OG_BUTTON_HEIGHT), JLabel.CENTER);
			backYellow = new JLabel(nameToImgIcon("back button (yellow).png", OG_ARROW_WIDTH, OG_BUTTON_HEIGHT), JLabel.CENTER);
			nextRed = new JLabel(nameToImgIcon("forward button (red).png", OG_ARROW_WIDTH, OG_BUTTON_HEIGHT), JLabel.CENTER);
			nextYellow = new JLabel(nameToImgIcon("forward button (yellow).png", OG_ARROW_WIDTH, OG_BUTTON_HEIGHT), JLabel.CENTER);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		currSlide = slideList.get(index);
		
		//exit button
		exitRed.setVisible(false);
		exitRed.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the main menu when the exit label is pressed by the mouse button.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				setSlideVisible(0);
				index = 0;
				removeAll();
				setVisible(false);
				NekoSushiTest.start.setVisible(true);
				
				exitRed.setVisible(false);
				exitYellow.setVisible(true);
				nextRed.setVisible(false);
				nextYellow.setVisible(true);
			}
			/**
			 * Displays the yellow exit label when the mouse cursor exits the red exit label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				exitRed.setVisible(false);
				exitYellow.setVisible(true);
			}
		});
		exitYellow.addMouseListener(new MouseAdapter() {
			/**
			 * Displays red exit label when the mouse cursor enters the yellow exit label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				exitYellow.setVisible(false);
				exitRed.setVisible(true);
			}
			
		});
		
		//back arrow button
		backYellow.setVisible(false);
		backRed.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the previous instruction slide.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				if(index > 0) {
					setSlideVisible(--index);
					if(index == slideList.size()-2) {
						nextYellow.setVisible(true);
					}
				}
			}
			/**
			 * Displays the yellow back arrow label when the mouse cursor exits the red back arrow label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				if(index > 0) {
					backRed.setVisible(false);
					backYellow.setVisible(true);
				}
			}
		});
		backYellow.addMouseListener(new MouseAdapter() {
			/**
			 * Displays red back arrow label when the mouse cursor enters the yellow back arrow label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				if(index > 0) {
					backYellow.setVisible(false);
					backRed.setVisible(true);
				}
			}
			
		});
		
		//forward arrow button
		nextRed.setVisible(false);
		nextRed.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the next instruction slide.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				if(index < slideList.size()-1) {
					setSlideVisible(++index);
				 	if(index == 1){
				 		backYellow.setVisible(true);
				 	}
				}
			}
			/**
			 * Displays the yellow forward arrow label when the mouse cursor exits the red forward arrow label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				if(index < slideList.size()-1) {
					nextRed.setVisible(false);
					nextYellow.setVisible(true);
				}
			}
		});
		nextYellow.addMouseListener(new MouseAdapter() {
			/**
			 * Displays red forward arrow label when the mouse cursor enters the yellow forward arrow label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				if(index < slideList.size()-1) {
					nextYellow.setVisible(false);
					nextRed.setVisible(true);
				}
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
	 * Sets the slide with the specified index to be visible
	 * @param newIndex - the index of the slide to be visible
	 */
	public void setSlideVisible(int newIndex) {
		remove(currSlide);
		validate();
		repaint();
		currSlide = slideList.get(newIndex);
		currSlide.setBounds(getXToCenter(getNewSize(OG_SLIDE_WIDTH)),getNewSize(OG_SLIDE_Y),getNewSize(OG_SLIDE_WIDTH), getNewSize(OG_SLIDE_HEIGHT));
	 	add(currSlide);
	 	validate();
	 	repaint();
	}
	
	/**
	 * Returns the yellow back arrow JLabel.
	 * @return the yellow back arrow JLabel.
	 */
	public JLabel getBackYellow() {
		return backYellow;
	}
	
	/**
	 * Returns the red back arrow JLabel.
	 * @return the yellow back arrow JLabel.
	 */
	public JLabel getBackRed() {
		return backRed;
	}
	
	/**
	 * Displays the background image, instruction slide, and control buttons.
	 * @param g - the graphics tool
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		g2D.drawImage(backgroundImage, 0, 0, null);
		
		title.setBounds(getNewSize(OG_TITLE_X),getNewSize(OG_TITLE_Y),getNewSize(OG_TITLE_WIDTH), getNewSize(OG_TITLE_HEIGHT));
	 	add(title);
	 	
	 	currSlide.setBounds(getXToCenter(getNewSize(OG_SLIDE_WIDTH)),getNewSize(OG_SLIDE_Y),getNewSize(OG_SLIDE_WIDTH), getNewSize(OG_SLIDE_HEIGHT));
	 	add(currSlide);
		
		int buttonHeight = getNewSize(OG_BUTTON_HEIGHT);
		int arrowY = getNewSize(OG_BUTTON_Y);
		int arrowWidth = getNewSize(OG_ARROW_WIDTH);
		
		exitYellow.setBounds(getNewSize(OG_EXIT_X),getNewSize(OG_BUTTON_Y),getNewSize(OG_EXIT_WIDTH), buttonHeight);
	 	add(exitYellow);
	 	exitRed.setBounds(getNewSize(OG_EXIT_X),getNewSize(OG_BUTTON_Y),getNewSize(OG_EXIT_WIDTH), buttonHeight);
	 	add(exitRed);
	 	
	 	backYellow.setBounds(getNewSize(OG_BACK_X),arrowY,arrowWidth, buttonHeight);
	 	add(backYellow);
	 	backRed.setBounds(getNewSize(OG_BACK_X),arrowY,arrowWidth, buttonHeight);
	 	add(backRed);
	 	
	 	nextYellow.setBounds(getNewSize(OG_NEXT_X),arrowY,arrowWidth, buttonHeight);
	 	add(nextYellow);
	 	nextRed.setBounds(getNewSize(OG_NEXT_X),arrowY,arrowWidth, buttonHeight);
	 	add(nextRed);
	}
}
