package ca.uqac.game.model;

import java.util.Observable;
import java.util.Observer;

import ca.uqac.game.util.Event;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicalPigeon extends Pigeon implements Runnable, Observer{

	private Circle c;
	private Pane pigeonPane;
	private Event event;
	public GraphicalPigeon(Pane pigeonPane) {
		
		super(pigeonPane.getPrefHeight(), pigeonPane.getPrefWidth());
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

	/* 
	 * update event
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		event = (Event) o;
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
					if(event!=null) {
						if(event.getScare()) {
							Move(); 
							
						}
						else {
							if(event.getFoodList().size() != 0) {
								Move(event.getNewFood().getP());
								if(this.canEatFood(event.getNewFood().getP())) {
									event.removeNewFood();
								}
							}else if(event.getFoodList().size() == 0) {
								// do nothing
							}
						}
					}
					Draw();
				});
			}
	}
}
