package ca.uqac.game.util;

import java.util.ArrayList;
import java.util.Observable;

import ca.uqac.game.model.GraphicalFood;

public class FoodList extends Observable{

	private ArrayList<GraphicalFood> list;
	
	public FoodList() {
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
	
}
