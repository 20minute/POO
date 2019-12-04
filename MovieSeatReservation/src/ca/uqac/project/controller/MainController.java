package ca.uqac.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import application.*;
import ca.uqac.project.model.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;

public class MainController extends BorderPane{

	@FXML
	private	Label title;
	@FXML
	private	ListView<Session> sessionList;
	@FXML
	private	GridPane seatPane;
	private Button[][] seatingButtons = new Button[Session.NUM_ROWS][Session.NUM_COLS];
	@FXML
	private CheckBox complementary;
	
	@FXML
	private ToggleGroup group1;
	@FXML
	private RadioButton adult;
	@FXML
	private RadioButton child;
	@FXML
	private RadioButton elderly;
	@FXML
	private Button newButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button bookButton;
	@FXML
	private Button exitButton;
	
    private ArrayList<SeatReservation> currentReservation;
    private Session currentSession;

	private ArrayList<Session> movies = new ArrayList<Session>();
    private ObservableList<Session> observableList = FXCollections.observableArrayList();
    
    private TransferTool transferTool = new TransferTool();

    /**
     * initialize the background color of the scene
     */
    @FXML
    private void initialize() 
    {
    	setListView();
    	initializeSeat();
    	initializeToolBar();
    }
    
    public MainController(){
    	currentReservation = new ArrayList();
        currentReservation.clear();
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MainView.fxml"));
	        fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);
	        try {
	            fxmlLoader.load();
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
    }
    private void initializeToolBar() {
    	complementary.setDisable(true);
    	adult.setDisable(true);
    	child.setDisable(true);
    	elderly.setDisable(true);
    	newButton.setDisable(true);
    	cancelButton.setDisable(true);
    	bookButton.setDisable(true);
    }
    private void initializeSeat() {
    	for(int i = 0; i < Session.NUM_ROWS; i++) {
    		for(int j = 0; j < Session.NUM_COLS; j++) {
    			int tmpI = i;
    			int tmpJ = j;
    			String text = Character.toString(Conversion.convertIndexToRow(i)) + j;
    			seatingButtons[i][j] = new Button();
    			seatingButtons[i][j].setText(text);
    			seatingButtons[i][j].setPrefSize(120, 65);
    			seatingButtons[i][j].setStyle("-fx-background-color: #1282A2; -fx-border-color: #034078; -fx-text-fill: #FEFCFB; -fx-font-size:14;");
    			seatingButtons[i][j].setDisable(true);
    			seatingButtons[i][j].setOnMouseClicked((MouseEvent e)->{
    				
    				if(group1.getSelectedToggle() == null) {
    					System.out.println("Please select the type of ticket below");
    				}else {
    					SeatReservation reservation;
    					if(adult.isSelected()) {
    						reservation = new AdultReservation(Conversion.convertIndexToRow(tmpI), tmpJ);
                            reservation.setComplementary(complementary.isPressed());
                            this.currentReservation.add(reservation);
    					}else if(child.isSelected()) {
    						reservation = new ChildReservation(Conversion.convertIndexToRow(tmpI), tmpJ);
                            reservation.setComplementary(complementary.isPressed());
                            this.currentReservation.add(reservation);
    					}else if(elderly.isSelected()) {
    						reservation = new ElderlyReservation(Conversion.convertIndexToRow(tmpI), tmpJ);
                            reservation.setComplementary(complementary.isPressed());
                            this.currentReservation.add(reservation);
    					}
    					 updateSeats();
    				}
    			});
    			seatPane.add(seatingButtons[i][j], j, i);
    		}
    	}
    }
    

    /**
     *  Updating the seats depending whether they have already been booked, being booked, and if the seat reservation list by the user is empty.
     */
    private void updateSeats()
    {
        // Update the buttons to set if there's anything inside the current reservation
        if (this.currentReservation.isEmpty())
        {
            newButton.setDisable(false);
            cancelButton.setDisable(true);
            bookButton.setDisable(true);
        }
        else
        {
        	newButton.setDisable(true);
            cancelButton.setDisable(false);
            bookButton.setDisable(false);
        }
        
        for(int row = 0; row < Session.NUM_ROWS;row++) {
        	for(int col = 0; col < Session.NUM_COLS;col++) {
                //seatingButtons[row][col].setStyle("-fx-border-color: #034078;");
                seatingButtons[row][col].setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));

                if (currentSession.getSeat(Conversion.convertIndexToRow(row), col) instanceof AdultReservation)
                {
                    this.seatingButtons[row][col].setDisable(true);
                    seatingButtons[row][col].setStyle("-fx-background-color: #001F54; -fx-text-fill: #FEFCFB;");
                }
                else if (currentSession.getSeat(Conversion.convertIndexToRow(row), col) instanceof ElderlyReservation)
                {
                    this.seatingButtons[row][col].setDisable(true);
                    seatingButtons[row][col].setStyle("-fx-background-color: #F2F4F3; -fx-text-fill: #034078;");
                }
                else if (currentSession.getSeat(Conversion.convertIndexToRow(row), col) instanceof ChildReservation)
                {
                    this.seatingButtons[row][col].setDisable(true);
                    seatingButtons[row][col].setStyle("-fx-background-color: #F7B32B; -fx-text-fill: #034078;");
                }
                else
                {
                    seatingButtons[row][col].setStyle("-fx-background-color: #1282A2; -fx-text-fill: #FEFCFB;");
                }
        	}
        }
        
        // Printing and Setting Selected Current Reservations to update the buttons
        int selectedRow, selectedCol;
        for (int counter = 0; counter < this.currentReservation.size(); counter++)
        {
            selectedRow = Conversion.convertRowToIndex(this.currentReservation.get(counter).getRow());
            selectedCol = this.currentReservation.get(counter).getCol();
            seatingButtons[selectedRow][selectedCol].setDisable(true);
            seatingButtons[selectedRow][selectedCol].setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, BorderWidths.DEFAULT)));
            
            //seatingButtons[selectedRow][selectedCol].setStyle("-fx-border-color: #034078;");
            // If the current reservation made is an instanc eof adult reservation
            if (this.currentReservation.get(counter) instanceof AdultReservation)
            {
                seatingButtons[selectedRow][selectedCol].setStyle("-fx-background-color: #001F54; -fx-text-fill: #FEFCFB;");
            }
            // If the current reservation made is an instance of elderly reservation
            else if (this.currentReservation.get(counter) instanceof ElderlyReservation)
            {
                seatingButtons[selectedRow][selectedCol].setStyle("-fx-background-color: #F2F4F3; -fx-text-fill: #034078;");
            }
            // If the current reservation made is an instance of child  reservation
            else if (this.currentReservation.get(counter) instanceof ChildReservation)
            {
                seatingButtons[selectedRow][selectedCol].setStyle("-fx-background-color: #F7B32B; -fx-text-fill: #034078;");
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
                //update title
                title.setText(newValue.getMovie().getName());
                //update seats
                currentSession = newValue;
                
                disableTicketPanel(true);
                disableMovieSeats(true); 
                this.newButton.setDisable(false);
                this.currentReservation.clear();
                updateSeats();
        });
    }
	
	 /**
     * Manages the Ticket Panel on whether they should be enabled or disabled.
     * @param bool how should they be set.
     */
    private void disableTicketPanel(Boolean bool)
    {
        // Set Enable or Disable
        adult.setDisable(bool);
        elderly.setDisable(bool);
        child.setDisable(bool);
        complementary.setDisable(bool);
    }
    
    /**
     * Manages the Movie Seats on whether they should be enabled or disabled.
     * @param bool how should they be set.
     */
    public void disableMovieSeats(Boolean bool)
    {
        for (int row = 0; row < Session.NUM_ROWS; row++)
        {
            for (int col = 0; col < Session.NUM_COLS; col++)
            {
                this.seatingButtons[row][col].setDisable(bool);
            }
        }
    }
	
	@FXML 
    protected void newEvent(MouseEvent event)
    {
		//this.newButton.setBackground(this.secondaryColor);
        currentReservation.clear();
        disableTicketPanel(false);
        disableMovieSeats(false);
        updateSeats();
    }
	
	@FXML 
    protected void bookEvent(ActionEvent event)
    {
		 float totalPrice = 0.0f;
         int ticketCounter = 0;
         // Checking if there's an item selected in the list
         if (currentSession != null)
         {
             // Check if applying bookings doesn't have any errors
             if (currentSession.applyBookings(this.currentReservation))
             {
                 // Add all the tickets from all reservations
                 for (; ticketCounter < this.currentReservation.size(); ticketCounter++)
                 {
                	 transferTool.getSeatInfo(this.currentReservation.get(ticketCounter));
                	 transferTool.writeInfo();
                	 transferTool.readInfo();
                	 
                     totalPrice += this.currentReservation.get(ticketCounter).getTicketPrice();
                 }
                 System.out.println("TICKET COST IS: $" + totalPrice);
                 Alert a1 = new Alert(AlertType.NONE,  
                		 "TICKET COST IS: $" + totalPrice,ButtonType.OK);
                 a1.show();
             }
             else
             {
                 // If there's an error, then there must be a child in a R-Rate film or without supervision
                 System.out.println("Cannot book a child in R-Rated Movies or Unsupervised in M-Rated Movies");
                 Alert a1 = new Alert(AlertType.NONE,  
                		 "Cannot book a child in R-Rated Movies or Unsupervised in M-Rated Movies",ButtonType.OK);
                 a1.show();
             }
         }
         else
         {
             // If a movie isn't selected
             System.out.println("select a movie");
             Alert a1 = new Alert(AlertType.NONE,  
            		 "Please select a movie first",ButtonType.OK);
             a1.show();
             
             //JOptionPane.showMessageDialog(this, "Please select a movie first", "Booking Error", JOptionPane.ERROR_MESSAGE);
         }
         disableTicketPanel(true);
         disableMovieSeats(true);
         this.currentReservation.clear();
         updateSeats();
    }
	
	@FXML 
    protected void cancelEvent(MouseEvent event)
    {
		 this.currentReservation.clear();
         disableTicketPanel(true);
         disableMovieSeats(true);
         updateSeats();
    }
	
	@FXML 
    protected void exitEvent(MouseEvent event)
    {
		 this.currentReservation.clear();
         System.exit(0);
    }
	
	

}