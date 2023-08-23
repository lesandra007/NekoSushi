package nekoSushi_proj;

/**
 * This program displays the Neko Sushi items the user obtained on a receipt.
 * @author Sandra Le
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class displays the Neko Sushi items the user obtained on a receipt.
 */
public class ReceiptPanel extends JPanel implements ChangeListener{
	public static final int OG_PAPER_WIDTH = 732;
	public static final int OG_PAPER_HEIGHT = 848;
	public static final int OG_PAPER_X = 210;
	public static final int OG_PAPER_Y = 67;
	
	public static final int OG_TITLE_WIDTH = 450;
	public static final int OG_TITLE_HEIGHT = 200;
	public static final int OG_TITLE_X = 335;
	public static final int OG_TITLE_Y = 108;
		
	public static final int OG_X_WIDTH = 40;
	public static final int OG_X_HEIGHT = 50;
	
	public static final int OG_TOTAL_WIDTH = 150;
	public static final int OG_TOTAL_HEIGHT = 50;
	
	public static final int OG_THANK_YOU_WIDTH = 250;
	public static final int OG_THANK_YOU_HEIGHT = 50;
	
	public static final int OG_EXIT_WIDTH = 300;
	public static final int OG_EXIT_HEIGHT = 100;
	public static final int OG_EXIT_X = 256;
	public static final int OG_EXIT_Y = 938;
	
	public static final int OG_RESTART_WIDTH = 300;
	public static final int OG_RESTART_HEIGHT = 100;
	public static final int OG_RESTART_X = 580;
	public static final int OG_RESTART_Y = 938;

	private DataModel model;
	private ArrayList<Item> cart;
	private int cartListSize;
	
	private Image backgroundImage;
	private JLabel paper;
	private JLabel title;
	private JLabel exitRed;
	private JLabel exitYellow;
	private JLabel restartRed;
	private JLabel restartYellow;
	private JLabel total;
	private JLabel thankYou;
	
	private ImageIcon x;
	
	private JPanel receiptPanel;
	private JLabel countLabel;
	private JPanel listP;
	
	public ReceiptPanel(DataModel model) {
		this.model = model;
		this.cart = model.getCart();
		cartListSize = cart.size();
		
		try {
			BufferedImage bufferedBackground = ImageIO.read(getClass().getClassLoader().getResource("background.png"));
			backgroundImage = bufferedBackground.getScaledInstance(NekoSushiTest.DEFAULT_FRAME_WIDTH, NekoSushiTest.DEFAULT_FRAME_HEIGHT, Image.SCALE_DEFAULT);
			paper = new JLabel(nameToImgIcon("receipt paper.png", OG_PAPER_WIDTH, OG_PAPER_HEIGHT), JLabel.CENTER);
			title = new JLabel(nameToImgIcon("receipt title.png", OG_TITLE_WIDTH, OG_TITLE_HEIGHT), JLabel.CENTER);
			total = new JLabel(nameToImgIcon("total.png", OG_TOTAL_WIDTH, OG_TOTAL_HEIGHT), JLabel.CENTER);
			thankYou = new JLabel(nameToImgIcon("thank you.png", OG_THANK_YOU_WIDTH, OG_THANK_YOU_HEIGHT), JLabel.CENTER);
			
			exitRed = new JLabel(nameToImgIcon("exit (red).png", OG_EXIT_WIDTH, OG_EXIT_HEIGHT), JLabel.CENTER);
			exitYellow = new JLabel(nameToImgIcon("exit (yellow).png", OG_EXIT_WIDTH, OG_EXIT_HEIGHT), JLabel.CENTER);
			
			restartRed = new JLabel(nameToImgIcon("restart (red).png", OG_RESTART_WIDTH, OG_RESTART_HEIGHT), JLabel.CENTER);
			restartYellow = new JLabel(nameToImgIcon("restart (yellow).png", OG_RESTART_WIDTH, OG_RESTART_HEIGHT), JLabel.CENTER);
			
			x = nameToImgIcon("x.png", OG_X_WIDTH, OG_X_HEIGHT);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		receiptPanel = new JPanel();
		receiptPanel.setLayout(new BoxLayout(receiptPanel,BoxLayout.Y_AXIS));
		
		JPanel titleP = new JPanel(new BorderLayout());
		titleP.add(title);
		receiptPanel.add(titleP);
		titleP.setBackground(Color.green);
		titleP.setOpaque(false);
		
		listP = new JPanel();
		listP.setLayout(new BoxLayout(listP,BoxLayout.Y_AXIS));
		receiptPanel.add(listP);
		listP.setOpaque(false);
		
		//split total
		JPanel totalCount = new JPanel(new BorderLayout());
		totalCount.add(total, BorderLayout.WEST);
		countLabel = new JLabel(String.valueOf(model.getCartSize()),SwingConstants.CENTER);
		countLabel.setFont(new Font(countLabel.getName(), Font.BOLD, 22));
		totalCount.add(countLabel, BorderLayout.EAST);
		totalCount.setBackground(Color.RED);
		receiptPanel.add(totalCount);
		totalCount.setOpaque(false);
		
		JPanel thanksP = new JPanel(new BorderLayout());
		thanksP.setPreferredSize(new Dimension(getNewSize(OG_THANK_YOU_WIDTH), getNewSize(OG_THANK_YOU_HEIGHT)));
		thanksP.add(thankYou);
		thanksP.setBackground(Color.BLUE);
		receiptPanel.add(thanksP);
		thanksP.setOpaque(false);
		
		setLayout(null);
	 	receiptPanel.setPreferredSize(new Dimension(getNewSize(OG_PAPER_WIDTH-120), getNewSize(OG_PAPER_HEIGHT-150)));
	 	receiptPanel.setOpaque(false);
	 	
	 	JScrollPane scrollFrame = new JScrollPane(receiptPanel);
	 	receiptPanel.setAutoscrolls(true);
	 	scrollFrame.setPreferredSize(new Dimension(getNewSize(OG_PAPER_WIDTH-120), getNewSize(OG_PAPER_HEIGHT-150)));
	 	this.add(scrollFrame);

		exitRed.setVisible(false);
		exitRed.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the main menu when the exit label is pressed by the mouse button.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				removeAll();
				listP.removeAll();
				model.clearCart();
				setVisible(false);
				NekoSushiTest.start.setVisible(true);
				
				exitRed.setVisible(false);
				exitYellow.setVisible(true);
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
				exitRed.setVisible(true);
				exitYellow.setVisible(false);
			}
			
		});
		
		restartRed.setVisible(false);
		restartRed.addMouseListener(new MouseAdapter() {
			/**
			 * Displays the game panel when the restart label is pressed by the mouse button.
			 * @param e - the mouse event
			 */
			public void mousePressed(MouseEvent e) {
				removeAll();
				listP.removeAll();
				model.clearCart();
				setVisible(false);
				NekoSushiTest.game.setVisible(true);
				
				restartRed.setVisible(false);
				restartYellow.setVisible(true);
				//need to reset positioning of items/plate/lid in game --> set curr to a random item, update
			}
			/**
			 * Displays the yellow restart label when the mouse cursor exits the red restart label.
			 * @param e - the mouse event
			 */
			public void mouseExited(MouseEvent e) {
				restartRed.setVisible(false);
				restartYellow.setVisible(true);
			}
			
		});
		restartYellow.addMouseListener(new MouseAdapter() {
			/**
			 * Displays red restart label when the mouse cursor enters the yellow restart label.
			 * @param e - the mouse event
			 */
			public void mouseEntered(MouseEvent e) {
				restartRed.setVisible(true);
				restartYellow.setVisible(false);
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
	 * Displays the background image, plate, and current item.
	 * @param g - the graphics tool
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(backgroundImage, 0, 0, null);
		
		countLabel.setText(String.valueOf(model.getCartSize()));
		while(cartListSize > 0) {
			Item i = cart.get(cart.size() - cartListSize);
			JPanel anItem = new JPanel(new BorderLayout());
			JLabel nameL = i.getNameReceiptLabel();
			anItem.add(nameL, BorderLayout.WEST);
			
			JLabel num = new JLabel(String.valueOf(i.getCount()), x, SwingConstants.HORIZONTAL);
			num.setFont(new Font(countLabel.getName(), Font.BOLD, 22));
			anItem.add(num, BorderLayout.EAST);
			
			anItem.setBackground(Color.CYAN);
			listP.add(anItem);
			anItem.setOpaque(false);
			cartListSize--;
		}	
		
		int receiptYMargin = getNewSize(OG_TITLE_Y-OG_PAPER_Y);
		int receiptXMargin = getNewSize(OG_TITLE_X-OG_PAPER_X);
		
		receiptPanel.setBounds(getXToCenter(getNewSize(OG_PAPER_WIDTH) - receiptXMargin*2), getNewSize(OG_TITLE_Y), getNewSize(OG_PAPER_WIDTH) - receiptXMargin*2, getNewSize(OG_PAPER_HEIGHT) - receiptYMargin*2);
		add(receiptPanel);
		
		paper.setBounds(getXToCenter(getNewSize(OG_PAPER_WIDTH)),getNewSize(OG_PAPER_Y),getNewSize(OG_PAPER_WIDTH), getNewSize(OG_PAPER_HEIGHT));
	 	add(paper);
	 	
	 	int OGButtonsLength = OG_RESTART_X + OG_RESTART_WIDTH - OG_EXIT_X;
		int OGButtonsGap = OGButtonsLength - OG_EXIT_WIDTH - OG_RESTART_WIDTH;
		int xExit = getXToCenter(getNewSize(OGButtonsLength));
		int xRestart = xExit + getNewSize(OG_EXIT_WIDTH) + getNewSize(OGButtonsGap);
		
		exitYellow.setBounds(xExit,getNewSize(OG_EXIT_Y),getNewSize(OG_EXIT_WIDTH), getNewSize(OG_EXIT_HEIGHT));
	 	add(exitYellow);
	 	exitRed.setBounds(xExit,getNewSize(OG_EXIT_Y),getNewSize(OG_EXIT_WIDTH), getNewSize(OG_EXIT_HEIGHT));
	 	add(exitRed);
	 	
	 	restartYellow.setBounds(xRestart,getNewSize(OG_RESTART_Y),getNewSize(OG_RESTART_WIDTH), getNewSize(OG_RESTART_HEIGHT));
	 	add(restartYellow);
	 	restartRed.setBounds(xRestart,getNewSize(OG_RESTART_Y),getNewSize(OG_RESTART_WIDTH), getNewSize(OG_RESTART_HEIGHT));
	 	add(restartRed);
	}

	/**
	 * Updates the cart and the number of items in cart.
	 * @param e - the change event
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		cart = model.getCart();
		cartListSize = cart.size();
	}
	
}
