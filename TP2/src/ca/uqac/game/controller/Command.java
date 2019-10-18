package ca.uqac.game.controller;

import ca.uqac.game.view.*;
import javafx.application.Platform;
import javafx.geometry.Point2D;
public class Command {

	private GraphicalFoodListener gfl;
	public Command()
	{
		 gfl = new GraphicalFoodListener();
	}
	
	public void createGraphicalPigeon(Field field)
	{
		 GraphicalPigeon gp = new GraphicalPigeon(field);
         gfl.addObserver(gp);
         
         Thread t = new Thread(gp);
         t.start();
         
	}
	
	public void createGraphicalFood(Field field, Point2D foodPosition)
	{
		GraphicalFood gf = new GraphicalFood(field,foodPosition);
        gfl.addNewFood(gf);
	}
}
