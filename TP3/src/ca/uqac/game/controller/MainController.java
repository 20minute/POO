package ca.uqac.game.controller;

import ca.uqac.game.model.GraphicalFood;
import ca.uqac.game.model.GraphicalPigeon;
import ca.uqac.game.util.Algorithme;
import ca.uqac.game.util.FoodList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;

public class MainController {
    @FXML
    private Button addPigeon;
    @FXML
    private Pane pigeonPane;
    @FXML
    private CheckBox aStar;
    private FoodList fl;
    private Algorithme algo;
    /**
     * initialize the background color of the scene
     */
    @FXML
    private void initialize() 
    {
    	BackgroundFill fill = new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY);
    	pigeonPane.setBackground(new Background(fill));
    	fl = new FoodList();
    	algo = new Algorithme();
    	fl.addObserver(algo);
    	
    }
    /**
     * create a new object of graphicalPigeon then put it in thread
     * @param event
     */
    @FXML
    protected void handleButtonAction(ActionEvent event) {
    	Point2D p = fl.getFoodList().get(0).getP();
    	GraphicalPigeon gp = new GraphicalPigeon(pigeonPane, p);
    	if(aStar.isSelected()) {
        	algo.NodesToFood();
    	}else {
    		algo.algo3();
    	}
    	Thread t = new Thread(gp);
    	
    	t.start();
    }
    
   
    
    /**
     * get the position of the mouse and create a new object of graphicalFood
     * @param event
     */
    @FXML 
    protected void handleOnMouseClicked(MouseEvent event)
    {
    	double x = event.getX();
    	double y = event.getY();
    	GraphicalFood gf = new GraphicalFood(x,y,pigeonPane);
    	fl.addNewFood(gf);
    }

}