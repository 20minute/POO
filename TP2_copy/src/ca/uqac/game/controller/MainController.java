package ca.uqac.game.controller;




import ca.uqac.game.model.FoodList;
import ca.uqac.game.model.GraphicalFood;
import ca.uqac.game.model.GraphicalPigeon;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private FoodList fl;
    private boolean flag = false;
    @FXML
    private void initialize() 
    {
    	BackgroundFill fill = new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY);
    	pigeonPane.setBackground(new Background(fill));
    	fl = new FoodList();
    	
    }
    @FXML
    protected void handleButtonAction(ActionEvent event) {
    	
    	GraphicalPigeon gp = new GraphicalPigeon(pigeonPane);
    	fl.addObserver(gp);
    	Thread t = new Thread(gp);
    	t.start();
    	
    	
//    	backgroundThread = new Service<Void>() {
//    		@Override 
//    		protected Task<Void> createTask(){
//    			return new Task<Void>() {
//    				@Override 
//    				protected Void call() throws Exception {
//    					final CountDownLatch latch = new CountDownLatch(1);
//    					Platform.runLater(new Runnable() {
//    						@Override
//    						public void run() {
//    							try {
//    								double height = pigeonPane.getPrefHeight();
//    						    	double width = pigeonPane.getPrefWidth();
//    						    	GraphicalPigeon gp = new GraphicalPigeon(height,width);
//    						    	pigeonPane.getChildren().add(gp.Draw());
//    							}
//    							finally {
//    								latch.countDown();
//    							}
//    						}
//    					});
//    					latch.await();
//    					
//    					
//    					
//    					
//    					return null;
//    				}
//    				
//    			};
//    		}
//    	};
////    	backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
////    		@Override 
////    		public void handle(WorkerStateEvent e) {
////    			System.out.println("ok");
////    		}
////    	});
//    	
//    	//pigeonPane.accessibleRoleProperty().bind(backgroundThread.getValue());
//    	backgroundThread.start();
    }
    
    @FXML 
    protected void handleButtonAction2(ActionEvent event) {
    	
    	flag =!flag;
    	fl.setScare(flag);
    	
    }
    
    @FXML 
    protected void handleOnMouseClicked(MouseEvent event)
    {
    	double x = event.getX();
    	double y = event.getY();
    	GraphicalFood gf = new GraphicalFood(x,y,pigeonPane);
    	fl.addNewFood(gf);
    }

}