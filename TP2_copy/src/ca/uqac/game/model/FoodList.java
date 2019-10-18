package ca.uqac.game.model;

import java.util.ArrayList;
import java.util.Observable;

public class FoodList extends Observable {

	private ArrayList<GraphicalFood> list;
	private boolean isScared;
	
	public FoodList() {
		list = new ArrayList<GraphicalFood>();
	}
	
	public void addNewFood(GraphicalFood gf) {
			list.add(gf);
			setChanged();
			notifyObservers();
	}
	public ArrayList<GraphicalFood> getFoodList(){
		return list;
	}
	
	public GraphicalFood getNewFood() {
		int index = list.size()-1;
		return list.get(index);
	}
	
	public void removeNewFood() {
		int index = list.size()-1;
		list.get(index).Remove();
		list.remove(index);
		setChanged();
		notifyObservers();
	}
	
	public void setScare(boolean isScared) {
		this.isScared = isScared;
		setChanged();
		notifyObservers();
	}
	
	public boolean getScare() {
		return isScared;
	}
	
}
