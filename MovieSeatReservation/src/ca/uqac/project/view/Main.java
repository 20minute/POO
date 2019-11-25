package ca.uqac.project.view;
	
import ca.uqac.project.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MainController main = new MainController();
            primaryStage.setScene(new Scene(main));
            primaryStage.setTitle("Movie Reservation System");
            primaryStage.setWidth(1000);
            primaryStage.setHeight(800);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}