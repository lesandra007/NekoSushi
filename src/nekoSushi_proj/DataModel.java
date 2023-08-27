package nekoSushi_proj;

/**
 * This program implements a model that holds Neko Sushi's data.
 * @author Sandra Le
 */

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A data model that manages the Neko Sushi items.
 */

public class DataModel {
	public static final int SHRIMP_ITEM_OG_WIDTH = 750;
	public static final int OTHER_ITEM_OG_WIDTH = 700;
	public static final int IKURA_UNI_ITEM_OG_HEIGHT = 500;
	public static final int OTHER_ITEM_OG_HEIGHT = 450;
	
	private ArrayList<Item> allItemsList;
	private Item plate;
	private Item lid;
	private Item currItem;
	private ArrayList<Item> cart;
	private ArrayList<ChangeListener> listeners;
	
	/**
	 * Constructs DataModel object.
	 */
	public DataModel() {
		allItemsList = new ArrayList<>();
//		allItemsList.add(new Item(this, "1", "tuna nigiri.png", "fish (outline).png"));
//		allItemsList.add(new Item(this, "2", "tuna nigiri.png", "fish (no outline).png"));
//		allItemsList.add(new Item(this, "3", "tuna nigiri.png", "logo.png"));
//		allItemsList.add(new Item(this, "4", "tuna nigiri.png", "tuna nigiri cat.png"));
//		allItemsList.add(new Item(this, "outline", "tuna nigiri.png", "fish (outline).png"));
//		allItemsList.add(new Item(this, "no outline", "tuna nigiri.png", "fish (no outline).png"));
//		allItemsList.add(new Item(this, "logo", "tuna nigiri.png", "logo.png"));
		allItemsList.add(new Item(this, "tuna nigiri", "tuna nigiri.png", 450, 100, "tuna nigiri cat.png", OTHER_ITEM_OG_WIDTH, OTHER_ITEM_OG_HEIGHT));
		
		plate = new Item(this, Holder.PLATE, "plate.png");
		lid = new Item(this, Holder.LID, "lid.png");
		currItem = randomItem();
		cart = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	/**
	 * Returns list of all possible items user can obtain.
	 * @return list of all possible items user can obtain
	 */
	public ArrayList<Item> getAllPossibleItems() {
		return allItemsList;
	}
	
	/**
	 * Adds specified item to list of possible items user can obtain.
	 * @param item - an item a user can obtain.
	 */
	public void addToPossibleItems(Item item) {
		allItemsList.add(item);
	}
	
	/**
	 * Returns list of items in user's cart.
	 * @return list of items in user's cart
	 */
	public ArrayList<Item> getCart() {
		return cart;
	}
	
	/**
	 * Adds specified item to user's cart.
	 * @param item - the item to add to user's cart
	 */
	public void addToCart(Item item) {
		cart.add(item);
	}
	
	/**
	 * Returns the number of items in cart.
	 * @return the number of items in cart
	 */
	public int getCartSize() {
		int count = 0;
		for(Item i: cart) {
			count += i.getCount();
		}
		return count;
	}
	
	/**
	 * Removes all items from cart.
	 */
	public void clearCart() {
		cart.clear();
		update();
	}
	
	/**
	 * Returns a plate for Neko Sushi items.
	 * @return a plate for Neko Sushi items
	 */
	public Item getPlate() {
		return plate;
	}
	
	/**
	 * Returns a lid for Neko Sushi items.
	 * @return a lid for Neko Sushi items
	 */
	public Item getLid() {
		return lid;
	}
	
	/**
	 * Returns the current item.
	 * @return the current item
	 */
	public Item getCurrItem() {
		return currItem;
	}
	
	/**
	 * Sets the current item to be the specified item
	 * @param item - the new current item
	 */
	public void setCurrItem(Item item) {
		currItem = item;
		update();
	}
	
	/**
	 * Saves the x-coordinate indicating the item's position on the conveyor belt.
	 */
	public void prepResumeGame() {
		NekoSushiTest.game.removeAll();
		int xResume = currItem.getX();
		currItem.getGameLabel().setLocation(xResume, currItem.getGameLabel().getY());
	}
	
	/**
	 * Returns a random item.
	 * @return a random item
	 */
	public Item randomItem() {
		int min = 0;
		int max = allItemsList.size() - 1;
		int randomIndex = min + (int)(Math.random() * ((max - min) + 1));
		return allItemsList.get(randomIndex);
	}
	
	/**
	 * Updates current item to be a random item.
	 */
	public void randomCurrItem() {
		currItem = randomItem();
		update();
	}
	
	/**
	 * Updates current item's x-coordinate to the specified value.
	 * @param x - the new x-coordinate of the current item
	 */
	public void setCurrX(int x) {
		currItem.setX(x);
	}
	
	/**
	 * Sets the bounds of the current item's game JLabel.
	 * @param x - the x-coordinate
	 * @param y - the y-coordinate
	 * @param width - the width
	 * @param height - the height
	 */
	public void setCurrGameBounds(int x, int y, int width, int height) {
		currItem.getGameLabel().setBounds(x, y, width, height);
	}
	
	/**
	 * Moves the plate, lid, and current item to the right by 1 pixel.
	 */
	public void move() {
		plate.move();
		int xLidOffset = NekoSushiTest.getNewSize(Item.PLATE_WIDTH)/2 - NekoSushiTest.getNewSize(Item.LID_WIDTH)/2;
		lid.setX(xLidOffset + plate.getX());
		if(currItem != null) {
			int xCurrOffset = NekoSushiTest.getNewSize(Item.PLATE_WIDTH)/2 - NekoSushiTest.getNewSize(currItem.getOgItemGameWidth())/2;
			currItem.setX(xCurrOffset + plate.getX());
		}
	}
	
	/**
	 * Resets the positions of the plate, lid, and current item.
	 */
	public void reset() {
		plate.reset();
		lid.setX(-NekoSushiTest.getNewSize(Item.PLATE_WIDTH));
		if(currItem != null) {
			currItem.setX(-NekoSushiTest.getNewSize(Item.PLATE_WIDTH));
		}
	}
	
	/**
	 * Adds item to cart.
	 * @param toClaim - the item to claim
	 */
	public void claimItem(Item toClaim) {
		boolean isClaimed = false;
		String name = toClaim.getName();
		//find if item already in cart
		for(Item i: cart) {
			//up count if already in cart
			if(i.getName().equals(name)) {
				i.upCount();
				isClaimed = true;
			}
		}
		//add new item to cart and up count
		if(!isClaimed) {
			Item claimed = toClaim.clone();
			cart.add(claimed);
			claimed.setClaimed(true);
			claimed.upCount();
		}
	}
	
	/**
	 * Attaches a listener to the model.
	 */
	public void attach(ChangeListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Changes data for all listeners of the model.
	 */
	public void update() {
		for (ChangeListener l: listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}
