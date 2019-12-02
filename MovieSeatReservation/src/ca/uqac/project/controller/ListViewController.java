package ca.uqac.project.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.uqac.project.model.Movie;
import ca.uqac.project.model.Session;
import ca.uqac.project.model.Time;
public class ListViewController extends ListView {
	
	@FXML
    private MainController mainController;
	
    @FXML
    private ListView<Session> sessionList;

    private List<String> stringList = new ArrayList<>(5);
    private ObservableList observableList = FXCollections.observableArrayList();

    public ListViewController()
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ListView.fxml"));
	    fxmlLoader.setRoot(this);
	    fxmlLoader.setController(this);
	    try {
	        fxmlLoader.load();
	    } catch (IOException exception) {
	        throw new RuntimeException(exception);
	    }
    }

    @FXML
    void initialize() {
        assert sessionList != null : "fx:id=\"listView\" was not injected: check your FXML file 'ListView.fxml'.";

        setListView();
    }
    private void setListView(){

    	Session[] myMovieSession = new Session[8];
        myMovieSession[0] = new Session(new Movie("SCP: The Movie", 'R'), new Time(16, 30));
        myMovieSession[1] = new Session(new Movie("The Predator", 'M'), new Time(4, 45, 30));
        myMovieSession[2] = new Session(new Movie("Justice League", 'G'), new Time(4, 30));
        myMovieSession[3] = new Session(new Movie("Deadpool 2", 'R'), new Time(4, 30));
        myMovieSession[4] = new Session(new Movie("Avengers: Infinity War", 'G'), new Time(2));
        myMovieSession[5] = new Session(new Movie("Logan", 'R'), new Time(7, 30));
        myMovieSession[6] = new Session(new Movie("Slenderman", 'M'), new Time(2, 30, 25));
        myMovieSession[7] = new Session(new Movie("Avenger End", 'G'), new Time(1, 45, 30));
        myMovieSession[8] = new Session(new Movie("Despasito", 'M'), new Time()); 
        ArrayList<Session> movies = new ArrayList(Arrays.asList(myMovieSession));
        stringList.add("String 1");
        stringList.add("String 2");
        stringList.add("String 3");
        stringList.add("String 4");

        observableList.setAll(movies);

        sessionList.setItems(observableList);

        sessionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Session>() {

            @Override
            public void changed(ObservableValue<? extends Session> observable, Session oldValue, Session newValue) {
                // Your action here
                System.out.println("Selected item: " + newValue.toString());
            }
        });
    }
}