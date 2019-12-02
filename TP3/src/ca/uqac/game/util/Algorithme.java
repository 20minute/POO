package ca.uqac.game.util;

import ca.uqac.game.model.*;
import javafx.geometry.Point2D;
import java.lang.Math;

import java.util.*;

public class Algorithme implements Observer{

	// maximum distance between two points 
	private static double variable = 300.0;
	
	public static ArrayList<GraphicalFood> result;
	
	
	ArrayList<GraphicalFood> foodList = new ArrayList<GraphicalFood>();
	GraphicalFood A;
	GraphicalFood B;
	
	@Override
	public void update(Observable o, Object arg) {
		FoodList fl = (FoodList) o;
		foodList =  fl.getFoodList();
		A = foodList.get(0);
		B = foodList.get(foodList.size()-1);
	}
	
	/**
	 * un algorithme pour trouver, pour un noeud donn¨¦ ni, la liste des noeuds
	 * connect¨¦s ¨¤ n selon la distance (plus petite ou ¨¦gale ¨¤ une variable d).
	 * @param n 
	 * @param d max distance
	 * @return
	 */
	public  ArrayList<GraphicalFood> algo1(GraphicalFood n, double d)
	{
		
		ArrayList<GraphicalFood> list = new ArrayList<GraphicalFood>();
		foodList.remove(n);
		for(GraphicalFood f: foodList) {
			double distance = this.getDistance(n.getP(),f.getP());
			if(!(distance > d))
			{
				list.add(f);
			}
		}
		
		if(list.size()==0) {
			System.err.println("Can not find food  < " + variable);
			return null;
		}else {
			return list;
		}
		
	}
	
	private double getDistance(Point2D p1, Point2D p2)
	{
		double x = p1.getX()-p2.getX();
		double y = p1.getY()-p2.getY();
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * un algorithme pour s¨¦lectionner le prochain noeud selon la plus petite distance
	 * @param n
	 * @param list
	 * @return
	 */
	public GraphicalFood algo2(GraphicalFood n, ArrayList<GraphicalFood> list)
	{
		try {
			GraphicalFood result = list.get(0);
			double min = getDistance(n.getP(),result.getP());
			for(GraphicalFood f: list) {
				double distance = getDistance(n.getP(),f.getP());
				if(distance < min) {
					result = f;
				}
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  un algorithme pour acheminer m messages de A ¨¤ B
	 */
	public void algo3() {
		result = new ArrayList<GraphicalFood>();
		
		GraphicalFood f = A;
		System.out.println("A:"+A.getP());
		System.out.println("B:" + B.getP());
		do {
			if(algo1(f,variable) == null) {
				break;
			}else {
				f = algo2(f,algo1(f,variable)); //get the next food
				System.out.println(f.getP());
				result.add(f);
			}
		}
		while(!f.getP().equals(B.getP()));
	}
	/////////////////////////A STAR ALGORITHME////////////////////////
	/**
	 * @brief Find surrounding nodes for the current node.
	 * @param currentNode
	 * @return a list of node
	 */
	private ArrayList<Node> getSurroundNodes(Node currentNode){
		try {
			ArrayList<GraphicalFood> surroundFood = algo1(currentNode.getFood(), variable);
			ArrayList<Node> surroundNodes = new ArrayList<>();
		
			for(GraphicalFood f:surroundFood) {
				surroundNodes.add(new Node(f));
			}
			return surroundNodes;
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * @brief find the road and put nodes in the list
	 * @return a list of node
	 */
	public ArrayList<Node> searchRoad() {
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closeList = new ArrayList<>();
		ArrayList<Node> resultList = new ArrayList<>();
		Node startNode = new Node(A);
		Node endNode = new Node(B);
		int resultIndex = -1;
		boolean isGetResult = false;
		//1. put start node in the openList 
		openList.add(startNode);
			do {
				// get current Node from openList to closeList
				Node currentNode = openList.get(0);
				openList.remove(0);
				closeList.add(currentNode);
				// get reachable Nodes for current Node
				ArrayList<Node> surroundNodes = getSurroundNodes(currentNode);
				if(surroundNodes == null) {break;}
				for(Node n : surroundNodes){
					// whether in the closeList
					boolean isExistList = closeList.contains(n);
					
					if(!isExistList) {
						//calculate G 
						double G = currentNode.G + this.getDistance(currentNode.getFood().getP(), n.getFood().getP());
						//if this node is not in the openList, then calculate H , F and put them in the openList
						if(!openList.contains(n)) {
							//double s = Math.floor(this.getDistance(n.f.getP(),endNode.f.getP()) / variable);
							n.H = this.getDistance(n.getFood().getP(),endNode.getFood().getP());
							n.G = G;
							n.F = n.H + n.G;
							n.father = currentNode;
							openList.add(n);
						}else {
							// recalculate G and F
							int index = openList.indexOf(n);
							if(G <  openList.get(index).G ) {
								openList.get(index).father = currentNode;
								openList.get(index).G = G;
								openList.get(index).F = G + openList.get(index).H;
							}
						}
					}
					if(openList.isEmpty()) {
						System.out.println("No road");
						break;
					}
					if(openList.contains(startNode)) {
						openList.remove(startNode);
					}
					// set order of openList by F
					openList.sort((Node o1,Node o2)-> Double.compare(o1.F, o2.F));
					
					for(Node tmpNode : openList){
						if(tmpNode.getFood().getP().equals(endNode.getFood().getP())) {
							isGetResult = true;
							resultIndex = openList.indexOf(tmpNode);
							closeList.add(tmpNode);
						}
					}
				}
			}while(!isGetResult);
		
		if(resultIndex == -1) {
			return null;
		}else {
			boolean stop = false;
			Node currentN = openList.get(resultIndex);
			do {
                resultList.add(currentN);
                
                if(currentN.getFood().getP().equals(startNode.getFood().getP())) {
                	 stop = true;
                }else {
                	 currentN = currentN.father;
                }
               
            }while (!stop);
			
			return resultList;
		}
	}
	/**
	 * @brief algo4: A Star Driver.
	 */
	public void NodesToFood() {
		try {
			ArrayList<Node> nodes = new ArrayList<>();
			ArrayList<GraphicalFood> list = new ArrayList<>();
			nodes = searchRoad();
			for(Node n : nodes) {
				list.add(n.getFood());
			}
			result = list;
			Collections.reverse(result);
		}catch(Exception e){
			return;
		}
	}
}

/**
 * Node Class for A Star algo.
 */
class Node{
	private GraphicalFood f;
	public double F,G,H;
	public Node father;
	public Node(GraphicalFood f) {
		this.f = f;
	}
	
	public GraphicalFood getFood()
	{
		return f;
	}
}
