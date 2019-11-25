package ca.uqac.project.controller;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.DataBase;
import ca.uqac.project.model.Conversion;
import ca.uqac.project.model.Movie;
import ca.uqac.project.model.Session;
import ca.uqac.project.model.Time;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class MainController extends BorderPane{

	@FXML
	private	Label title;
	@FXML
	private	ListView<Session> sessionList;
	@FXML
	private	GridPane seatPane;
	private Button[][] seats = new Button[Session.NUM_ROWS][Session.NUM_COLS];
	
	
	@FXML
	private ToggleGroup group1;
	@FXML
	private RadioButton adult;
	@FXML
	private RadioButton child;
	@FXML
	private RadioButton elderly;
	private ArrayList<Session> movies = new ArrayList<Session>();
    private ObservableList<Session> observableList = FXCollections.observableArrayList();

    /**
     * initialize the background color of the scene
     */
    @FXML
    private void initialize() 
    {
    	setListView();
    	initializeSeat();
    }
    
    public MainController(){
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MainView.fxml"));
	        fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        try {
	            fxmlLoader.load();
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
    }
    private void initializeSeat() {
    	//Creating the mouse event handler 
    	
    	for(int i = 0; i < Session.NUM_ROWS; i++) {
    		for(int j = 0; j < Session.NUM_COLS; j++) {
    			String text = Character.toString(Conversion.convertIndexToRow(i)) + j;
    			seats[i][j] = new Button();
    			seats[i][j].setText(text);
    			seats[i][j].setPrefSize(120, 60);
    			seats[i][j].setStyle("-fx-background-color: #1282A2; -fx-border-color: #034078; -fx-text-fill: #FEFCFB; -fx-font-size:14;");
    			seats[i][j].setDisable(false);
    			seats[i][j].setOnMouseClicked((MouseEvent e)->{
    				
    				RadioButton selectedRadioButton =
    				        (RadioButton) group1.getSelectedToggle();
    				if(selectedRadioButton == null) {
    					System.out.println("Please select the type of ticket below");
    				}else {
    					if(selectedRadioButton == adult) {
    						//TODO ss
    					}else if(selectedRadioButton == child) {
    						//TODO ss
    					}else if(selectedRadioButton == elderly) {
    						//TODO ss
    					}
    				}
    			});
    			seatPane.add(seats[i][j], j, i);
    		}
    	}
    }
    
    private void setListView(){

    	DataBase bs = new DataBase();
    	movies= bs.getSessions();
        observableList.setAll(movies);

        sessionList.setItems(observableList);

        sessionList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Session> observable, Session oldValue, Session newValue)-> {
                // Your action here
                System.out.println("Selected item: " + newValue.toString());
                title.setText(newValue.getMovie().getName());
        });
    }

}