package ca.uqac.game.view;


import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ca.uqac.game.controller.*;





 
public class World extends Application {
	
	final static int width = 600;
	final static int height = 700;
	
	
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pigeon World!");
        
        
        
        Button btn = new Button();
        btn.setText("Add pigeons");
        
        Button btn2 = new Button();
        btn2.setText("Scare pigeons");
        
        Field field = new Field(height,width);
        GraphicsContext gc = field.GetGraphics();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, height, width);
        
        
        Command c = new Command();
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    c.createGraphicalPigeon(field);
            }
        });
        
        
        
       
        
        field.setOnMouseClicked(event -> {
            double x = event.getX(), y = event.getY();
            Point2D foodPosition = new Point2D(x,y);
            c.createGraphicalFood(field, foodPosition);
        });
        
       
        HBox hBox = new HBox(btn,btn2);
        VBox vBox = new VBox(hBox, field);
        Scene scene = new Scene(vBox, height, width, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
     
    }
 
}