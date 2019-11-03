package ca.uqac.game.util;

import ca.uqac.game.model.*;
import javafx.geometry.Point2D;
import java.lang.Math;

import java.util.*;

public class Algorithme {

	Event e = new Event();
	ArrayList<GraphicalFood> foodList =  e.getFoodList();
	GraphicalFood A = foodList.get(0);
	GraphicalFood B = e.getNewFood();
		
	public  ArrayList<GraphicalFood> algo1(GraphicalFood n, double d)
	{
		
		ArrayList<GraphicalFood> list = new ArrayList<GraphicalFood>();
		for(GraphicalFood f: foodList) {
			if(!n.getP().equals(f.getP()))
			{
				double distance = this.getDistance(n.getP(),f.getP());
				if(!(distance > d))
				{
					list.add(f);
				}
			}
		}
		return list;
	}
	
	private double getDistance(Point2D p1, Point2D p2)
	{
		double x = p1.getX()-p2.getX();
		double y = p1.getY()-p2.getY();
		return Math.sqrt(x*x + y*y);
	}
	
	public GraphicalFood algo2(GraphicalFood n, ArrayList<GraphicalFood> list)
	{
		GraphicalFood result = list.get(0);
		double min = getDistance(n.getP(),result.getP());
		for(GraphicalFood f: list) {
			double distance = getDistance(n.getP(),f.getP());
			if(distance < min) {
				result = f;
			}
		}
		return result;
	}
	
	public ArrayList<GraphicalFood> algo3(GraphicalFood A, GraphicalFood B) {
		double d = 100.0;
		ArrayList<GraphicalFood> result = new ArrayList<GraphicalFood>();
		GraphicalFood f = A;
		do {
			f = algo2(f,algo1(A,d)); //get the next food
			result.add(f);
		}
		while(f.getP().equals(B.getP()));
		
		return result;
	}
}
