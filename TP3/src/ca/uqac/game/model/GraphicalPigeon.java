package ca.uqac.game.model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ca.uqac.game.util.Algorithme;
import ca.uqac.game.util.FoodList;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicalPigeon extends Pigeon implements Runnable{

	private Circle c;
	private Pane pigeonPane;
	
	public GraphicalPigeon(Pane pigeonPane) {
		
		super(pigeonPane.getPrefHeight(), pigeonPane.getPrefWidth());
		this.pigeonPane = pigeonPane;
		this.c = new Circle();
	}
	
	public GraphicalPigeon(Pane pigeonPane, Point2D p) {
		super(p);
		this.pigeonPane = pigeonPane;
		this.c = new Circle();
	}

	/**
	 * add a pigeon on the pane
	 */
	public void Draw()
	{
		c.setCenterX(this.getP().getX());
		c.setCenterY(this.getP().getY());
		c.setRadius(PIGEON_SIZE);
		c.setFill(Color.WHITE);
		pigeonPane.getChildren().add(c);
	}
	
	/**
	 * remove a pigeon on the pane
	 */
	public void Remove() {
		pigeonPane.getChildren().remove(c);
	}

	/**
	 * get the last one in the list
	 * @return GraphicalFood
	 */
	public GraphicalFood getNewFood(ArrayList<GraphicalFood> list) {
		int index = list.size()-1;
		return list.get(index);
	}
	
	

	@Override
	public void run() {
		while(true) {
			try{
				synchronized (this) {
					Thread.sleep(300);		
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Platform.runLater(()->{
				Remove();
				ArrayList<GraphicalFood> list = Algorithme.result;
				if(list == null) {return;}
				if(list.size() != 0) {
					Move(list.get(0).getP());
					if(this.canEatFood(list.get(0).getP())) {
						list.remove(0);
					}
				}else if(list.size() == 0) {
					// do nothing
				}
				Draw();
			});
		}
	}
}
