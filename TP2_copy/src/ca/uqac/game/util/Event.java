package ca.uqac.game.util;

import java.util.ArrayList;
import java.util.Observable;

import ca.uqac.game.model.GraphicalFood;

public class Event extends Observable {

	private ArrayList<GraphicalFood> list;
	private boolean isScared;
	
	public Event() {
		list = new ArrayList<GraphicalFood>();
	}
	
	/**
	 * add GraphicalFood in the list
	 * @param gf GraphicalFood
	 */
	public void addNewFood(GraphicalFood gf) {
			list.add(gf);
			setChanged();
			notifyObservers();
	}
	/**
	 * @return GraphicalFood list
	 */
	public ArrayList<GraphicalFood> getFoodList(){
		return list;
	}
	
	/**
	 * get the last one in the list
	 * @return GraphicalFood
	 */
	public GraphicalFood getNewFood() {
		int index = list.size()-1;
		return list.get(index);
	}
	
	/**
	 * delete the last one in the list
	 */
	public void removeNewFood() {
		int index = list.size()-1;
		list.get(index).Remove();
		list.remove(index);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * setter
	 * @param isScared
	 */
	public void setScare(boolean isScared) {
		this.isScared = isScared;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * getter
	 * @return boolean
	 */
	public boolean getScare() {
		return isScared;
	}
	
}
