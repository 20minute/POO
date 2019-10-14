package ca.uqac.game.view;

import java.util.ArrayList;
import java.util.Observable;

public class GraphicalFoodListener extends Observable {

	private ArrayList<GraphicalFood> list;
	private GraphicalFood freshFood;
	
	public GraphicalFoodListener() {
		list = new ArrayList<GraphicalFood>();
	}
	
	public void addNewFood(GraphicalFood gf) {
		if(gf.isFresh()) {
			list.add(gf);
			setChanged();
			notifyObservers();
		}
		
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
	
}
