package nekoSushi_proj;
/**
 * This program creates Neko Sushi, a revolving sushi bar simulator, that allows users to choose Neko Sushi items from the conveyor belt.
 * @author Sandra Le; Illustrations by Caroline Park
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JFrame;

/**
 * This class creates the visual components of the frame that work with the model to make a functioning Neko Sushi game.
 */

public class NekoSushiTest {

	public static final double OG_FRAME_WIDTH = 1080.0;
	public static final int DEFAULT_FRAME_WIDTH = 650;
	public static final int DEFAULT_FRAME_HEIGHT = DEFAULT_FRAME_WIDTH;	
	public static final JFrame frame = new JFrame("Neko Sushi by Caroline & Sandra");
	public static final DataModel model = new DataModel();
	public static final GamePanel game = new GamePanel(model);
	public static final StartPanel start = new StartPanel();
	public static final TutorialPanel tutorial = new TutorialPanel();
	public static final InfoPanel info = new InfoPanel(model);
	public static final ReceiptPanel receipt = new ReceiptPanel(model);
	
	/**
	 * Returns the width of the frame's content pane.
	 * @return the width of the frame's content pane
	 */
	public static final double getContentWidth() {
		double width = frame.getContentPane().getWidth();
		if(width == 0) {
			width = DEFAULT_FRAME_WIDTH;
		}
		return width;
	}
	
	/**
	 * Returns the height of the frame's content pane.
	 * @return the height of the frame's content pane.
	 */
	public static final double getContentHeight() {
		double height = frame.getContentPane().getHeight();
		if(height == 0) {
			height = DEFAULT_FRAME_HEIGHT;
		}
		return height;
	}
	
	/**
	 * Calculates [expected frame size] * (measurement/[original frame width])
	 * @param measurement - measurement to convert
	 * @return [expected frame size] * (measurement/[original frame width])
	 */
	public static int getNewSize(int measurement) {
		double ratio = measurement/NekoSushiTest.OG_FRAME_WIDTH;
		return (int) Math.round(NekoSushiTest.getContentWidth() * ratio);
	}

	/*
	 * Creates the frame with an initial main menu, game, and end receipt panels
	 * @param args unused
	 */
	public static void main(String[] args) {
		
		frame.getContentPane().setPreferredSize(new Dimension(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT));
		frame.getContentPane().setBackground(Color.GREEN);
		
		model.attach(game);
		model.attach(info);
		model.attach(receipt);
		
		frame.add(start);
		
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		game.animationLoop();
	}
}
