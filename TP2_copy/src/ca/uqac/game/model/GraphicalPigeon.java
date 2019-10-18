package ca.uqac.game.model;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicalPigeon extends Pigeon implements Runnable, Observer{

	private Circle c;
	private Pane pigeonPane;
	private FoodList foodUpdate;
	public GraphicalPigeon(Pane pigeonPane) {
		
		super(pigeonPane.getPrefHeight(), pigeonPane.getPrefWidth());
		this.pigeonPane = pigeonPane;
		// TODO Auto-generated constructor stub
		this.c = new Circle();
	}

	public void Draw()
	{
		c.setCenterX(this.getP().getX());
		c.setCenterY(this.getP().getY());
		c.setRadius(PIGEON_SIZE);
		c.setFill(Color.WHITE);
		pigeonPane.getChildren().add(c);
	}
	
	public void Remove() {
		pigeonPane.getChildren().remove(c);
	}

	@Override
	public void update(Observable o, Object arg) {
		foodUpdate = (FoodList) o;
		
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			while(true) {
				try{
					synchronized (this) {
						Thread.sleep(300);		
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.runLater(()->{
					Remove();
					if(foodUpdate!=null) {
						if(foodUpdate.getScare()) {
							Move();
							
						}
						else {
							if(foodUpdate.getFoodList().size() != 0) {
								Move(foodUpdate.getNewFood().getP());
								
								if(this.canEatFood(foodUpdate.getNewFood().getP())) {
									foodUpdate.removeNewFood();
								}
							}else if(foodUpdate.getFoodList().size() == 0) {
								System.out.println("u can have a break");
							}
						}
					}
					Draw();
				});
			}
		
		
		
	}

	

}
