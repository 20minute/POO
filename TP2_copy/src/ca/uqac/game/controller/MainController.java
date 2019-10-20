package ca.uqac.game.controller;

import ca.uqac.game.model.GraphicalFood;
import ca.uqac.game.model.GraphicalPigeon;
import ca.uqac.game.util.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
    private Event fl;
    private boolean flag = false;
    /**
     * initialize the background color of the scene
     */
    @FXML
    private void initialize() 
    {
    	BackgroundFill fill = new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY);
    	pigeonPane.setBackground(new Background(fill));
    	fl = new Event();
    	
    }
    /**
     * create a new object of graphicalPigeon then put it in thread
     * @param event
     */
    @FXML
    protected void handleButtonAction(ActionEvent event) {
    	
    	GraphicalPigeon gp = new GraphicalPigeon(pigeonPane);
    	fl.addObserver(gp);
    	Thread t = new Thread(gp);
    	t.start();
    }
    
    /**
     * click this button to start or stop scaring the pigeons
     * @param event
     */
    @FXML 
    protected void handleButtonAction2(ActionEvent event) {
    	
    	flag =!flag;
    	fl.setScare(flag);
    	
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