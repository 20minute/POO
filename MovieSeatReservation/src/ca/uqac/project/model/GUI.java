package ca.uqac.project.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The front end GUI of the MovieSession java, the GUI is used to book movies.
 * <p>
 * <b><u>BUTTONS:</u></b>
 * <br>
 * <b>NEW</b> - Create new reservations<br> 
 * <b>BOOK</b> - Book the reservation set<br> 
 * <b>CANCEL</b> - Cancel the reservation set<br> 
 * <b>COMPLEMENTARY</b> - Checked if it's complementary, unchecked if it's not<br> 
 * <b>EXIT</b> - Exit the GUI<br> 
 * @author lyleb
 */
public class GUI extends JPanel implements ActionListener, ListSelectionListener
{
    private JRadioButton adultButton, elderlyButton, childButton, invisibleRadioButton;
    private JButton newButton, bookButton, cancelButton, exitButton;
    private JCheckBox complementaryButton;
    private JList moviePlayingList;
    private DefaultListModel model;
    private ArrayList<SeatReservation> currentReservation;
    private JButton[][] seatingButtons;
    private JLabel movieHeader, ticketLabel;
    private int seatRowAmount, seatColAmount; // How many seats (Row and Column)
    // Design Fields
    private Color primaryColor, secondaryColor, tertiaryColor, adultColor, elderlyColor, childColor;
    private Border raisedBevel, loweredBevel, lineBorder;
    private TitledBorder title;

    /**
     * Constructor for initializing, designing, and creating the GUI. 
     * The constructor also sets things like the color scheme, size, and borders.
     * @param movies the array of movies.
     */
    public GUI(ArrayList movies)
    {
        super(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        this.currentReservation = new ArrayList();
        this.currentReservation.clear();
        this.seatRowAmount = 8;
        this.seatColAmount = 6;

        // Setting up the Colours
        this.primaryColor = new Color(3, 64, 120); // Dark Blue
        this.secondaryColor = new Color(18, 130, 162); // Cyan
        this.tertiaryColor = new Color(254, 252, 251); // White
        this.adultColor = new Color(0, 31, 84); // Blue
        this.elderlyColor = new Color(242, 244, 243); // Dirty White
        this.childColor = new Color(247, 179, 43); // Yellow

        // Setting up the Border Styles
        this.raisedBevel = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        this.loweredBevel = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        this.lineBorder = BorderFactory.createLineBorder(this.primaryColor, 1);
        
        // Label on top of the GUI
        JPanel label = new JPanel();
        this.movieHeader = new JLabel("MOVIE BOOKING SYSTEM");
        this.movieHeader.setFont(new Font("Tahoma", Font.BOLD, 24));
        this.movieHeader.setForeground(this.tertiaryColor);
        label.setBackground(this.primaryColor);
        label.add(this.movieHeader);
        label.setBorder(this.raisedBevel);

        // Ticket Panel Label for the JRadio Buttons
        this.ticketLabel = new JLabel("Ticket Panel: ");
        this.ticketLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.ticketLabel.setForeground(this.tertiaryColor);

        // JRadio Buttons (Adult, Elderly, and Child)
        this.adultButton = new JRadioButton("Adult");
        this.adultButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.adultButton.setBackground(this.adultColor);
        this.adultButton.setForeground(this.tertiaryColor);
        this.adultButton.setPreferredSize(new Dimension(60, 25));
        this.adultButton.setEnabled(false);
        this.adultButton.addActionListener(this);

        this.elderlyButton = new JRadioButton("Elderly");
        this.elderlyButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.elderlyButton.setBackground(this.elderlyColor);
        this.elderlyButton.setForeground(this.primaryColor);
        this.elderlyButton.setPreferredSize(new Dimension(70, 25));
        this.elderlyButton.setEnabled(false);
        this.elderlyButton.addActionListener(this);

        this.childButton = new JRadioButton("Child");
        this.childButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.childButton.setBackground(this.childColor);
        this.childButton.setForeground(this.primaryColor);
        this.childButton.setPreferredSize(new Dimension(60, 25));
        this.childButton.setEnabled(false);
        this.childButton.addActionListener(this);

        // For turning off the selection of the ticket panel
        this.invisibleRadioButton = new JRadioButton("Child");
                
        // Other Buttons (Complementary, New, Book, Exit)
        this.complementaryButton = new JCheckBox("Complementary");
        this.complementaryButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.complementaryButton.setBackground(this.secondaryColor);
        this.complementaryButton.setForeground(this.tertiaryColor);
        this.complementaryButton.setPreferredSize(new Dimension(125, 25));
        this.complementaryButton.setEnabled(false);
        this.complementaryButton.addActionListener(this);
        
        this.newButton = new JButton("New");
        this.newButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.newButton.setBackground(this.secondaryColor);
        this.newButton.setForeground(this.tertiaryColor);
        this.newButton.setPreferredSize(new Dimension(80, 35));
        this.newButton.setEnabled(false);
        this.newButton.addActionListener(this);

        this.bookButton = new JButton("Book");
        this.bookButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.bookButton.setBackground(this.secondaryColor);
        this.bookButton.setForeground(this.tertiaryColor);
        this.bookButton.setEnabled(false);
        this.bookButton.setPreferredSize(new Dimension(80, 35));
        this.bookButton.addActionListener(this);
        
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.cancelButton.setBackground(this.secondaryColor);
        this.cancelButton.setForeground(this.tertiaryColor);
        this.cancelButton.setEnabled(false);
        this.cancelButton.setPreferredSize(new Dimension(80, 35));
        this.cancelButton.addActionListener(this);
        
        this.exitButton = new JButton("Exit");
        this.exitButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        this.exitButton.setBackground(this.secondaryColor);
        this.exitButton.setForeground(this.tertiaryColor);
        this.exitButton.setPreferredSize(new Dimension(80, 35));
        this.exitButton.addActionListener(this);

        // ButtonGroup to group up all the buttons together, and to make buttons have one instance of selection at all times
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.adultButton);
        buttonGroup.add(this.elderlyButton);
        buttonGroup.add(this.childButton);
        buttonGroup.add(this.newButton);
        buttonGroup.add(this.bookButton);
        buttonGroup.add(this.cancelButton);
        buttonGroup.add(this.exitButton);
        buttonGroup.add(this.invisibleRadioButton);
        // Complementary is separated so that it can be used together with the radio buttons
        add(this.complementaryButton);

        // Ticket Panel - Adult/Child/Elderly
        JPanel ticketPanel = new JPanel();
        ticketPanel.setBackground(this.secondaryColor);
        ticketPanel.add(this.ticketLabel);
        ticketPanel.add(this.adultButton);
        ticketPanel.add(this.childButton);
        ticketPanel.add(this.elderlyButton);
        ticketPanel.add(this.complementaryButton);

        // JPanel that holds all the Buttons on the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(ticketPanel);
        buttonPanel.add(this.newButton);
        buttonPanel.add(this.bookButton);
        buttonPanel.add(this.cancelButton);
        buttonPanel.add(this.exitButton);
        buttonPanel.setBackground(this.primaryColor);
        buttonPanel.setBorder(this.raisedBevel);

        // Setting all the Seats 
        this.seatingButtons = new JButton[this.seatRowAmount][this.seatColAmount];
        JPanel movieSeats = new JPanel(new GridLayout(this.seatRowAmount, this.seatColAmount));
        movieSeats.setBackground(this.tertiaryColor);
        for (int row = 0; row < this.seatRowAmount; row++)
        {
            for (int col = 0; col < this.seatColAmount; col++)
            {
                StringBuilder seat = new StringBuilder();
                seat.append(Conversion.convertIndexToRow(row));
                seat.append(col);
                this.seatingButtons[row][col] = new JButton(seat.toString());
                this.seatingButtons[row][col].setBackground(this.secondaryColor);
                this.seatingButtons[row][col].setForeground(this.tertiaryColor);
                this.seatingButtons[row][col].setFont(new Font("Tahoma", Font.BOLD, 12));
                this.seatingButtons[row][col].setEnabled(false);
                this.seatingButtons[row][col].addActionListener(this);
                movieSeats.add(this.seatingButtons[row][col]);
            }
        }

        //Create the JList and listModel
        this.model = new DefaultListModel();
        for (Object p : movies)
        {
            this.model.addElement(p);
        }
        this.moviePlayingList = new JList(this.model);
        this.moviePlayingList.setLayoutOrientation(JList.VERTICAL);
        this.moviePlayingList.addListSelectionListener(this);
        this.moviePlayingList.setForeground(this.secondaryColor);
        this.moviePlayingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.moviePlayingList.setFont(new Font("Tahoma", Font.BOLD, 14));
        JScrollPane pane = new JScrollPane(this.moviePlayingList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(this.primaryColor, 4),
                "Movies", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Tahoma", Font.BOLD, 18), this.primaryColor);
        pane.setBorder(this.title);
        pane.setBackground(this.tertiaryColor);
        
        //Add the buttonPanel
        add(label, BorderLayout.NORTH);
        add(pane, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(movieSeats, BorderLayout.CENTER);
    }

    /**
     *  Updating the seats depending whether they have already been booked, being booked, and if the seat reservation list by the user is empty.
     */
    public void updateSeats()
    {
        // Update the buttons to set if there's anything inside the current reservation
        if (this.currentReservation.isEmpty())
        {
            this.newButton.setEnabled(true);
            this.cancelButton.setEnabled(false);
            this.bookButton.setEnabled(false);
        }
        else
        {
            this.newButton.setEnabled(false);
            this.cancelButton.setEnabled(true);
            this.bookButton.setEnabled(true);
        }
        
        // Updating the buttons depending on the Already Booked seats and the Other Seats
        if (this.moviePlayingList.getSelectedValue() != null)
        {
            Session movie = (Session) this.moviePlayingList.getSelectedValue();
            for (int row = 0; row < this.seatRowAmount; row++)
            {
                for (int col = 0; col < this.seatColAmount; col++)
                {
                    this.seatingButtons[row][col].setBorder(this.lineBorder);
                    if (movie.getSeat(Conversion.convertIndexToRow(row), col) instanceof AdultReservation)
                    {
                        this.seatingButtons[row][col].setEnabled(false);
                        this.seatingButtons[row][col].setBackground(this.adultColor);
                        this.seatingButtons[row][col].setForeground(this.tertiaryColor);
                    }
                    else if (movie.getSeat(Conversion.convertIndexToRow(row), col) instanceof ElderlyReservation)
                    {
                        this.seatingButtons[row][col].setEnabled(false);
                        this.seatingButtons[row][col].setBackground(this.elderlyColor);
                        this.seatingButtons[row][col].setForeground(this.primaryColor);
                    }
                    else if (movie.getSeat(Conversion.convertIndexToRow(row), col) instanceof ChildReservation)
                    {
                        this.seatingButtons[row][col].setEnabled(false);
                        this.seatingButtons[row][col].setBackground(this.childColor);
                        this.seatingButtons[row][col].setForeground(this.primaryColor);
                    }
                    else
                    {
                        this.seatingButtons[row][col].setBackground(this.secondaryColor);
                        this.seatingButtons[row][col].setForeground(this.tertiaryColor);
                    }
                }
            }
        }
       
        // Printing and Setting Selected Current Reservations to update the buttons
        int selectedRow, selectedCol;
        for (int counter = 0; counter < this.currentReservation.size(); counter++)
        {
            selectedRow = Conversion.convertRowToIndex(this.currentReservation.get(counter).getRow());
            selectedCol = this.currentReservation.get(counter).getCol();
            this.seatingButtons[selectedRow][selectedCol].setEnabled(false);
            this.seatingButtons[selectedRow][selectedCol].setBorder(BorderFactory.createLineBorder(new Color(34,139,34), 1));
            // If the current reservation made is an instanc eof adult reservation
            if (this.currentReservation.get(counter) instanceof AdultReservation)
            {
                this.seatingButtons[selectedRow][selectedCol].setBackground(this.adultColor);
                this.seatingButtons[selectedRow][selectedCol].setForeground(this.tertiaryColor);
            }
            // If the current reservation made is an instance of elderly reservation
            else if (this.currentReservation.get(counter) instanceof ElderlyReservation)
            {
                this.seatingButtons[selectedRow][selectedCol].setBackground(this.elderlyColor);
                this.seatingButtons[selectedRow][selectedCol].setForeground(this.primaryColor);
            }
            // If the current reservation made is an instance of child  reservation
            else if (this.currentReservation.get(counter) instanceof ChildReservation)
            {
                this.seatingButtons[selectedRow][selectedCol].setBackground(this.childColor);
                this.seatingButtons[selectedRow][selectedCol].setForeground(this.primaryColor);
            }
        }
    }
    
    /**
     * Manages the Ticket Panel on whether they should be enabled or disabled.
     * @param bool how should they be set.
     */
    public void manageTicketPanel(Boolean bool)
    {
        // Set Enable or Disable
        this.adultButton.setEnabled(bool);
        this.elderlyButton.setEnabled(bool);
        this.childButton.setEnabled(bool);
        this.complementaryButton.setEnabled(bool);
        
        // Turn off any selection
        this.invisibleRadioButton.setSelected(true);
    }
    
    /**
     * Manages the Movie Seats on whether they should be enabled or disabled.
     * @param bool how should they be set.
     */
    public void manageMovieSeats(Boolean bool)
    {
        for (int row = 0; row < this.seatRowAmount; row++)
        {
            for (int col = 0; col < this.seatColAmount; col++)
            {
                this.seatingButtons[row][col].setEnabled(bool);
            }
        }
    }
    /**
     * Action listener for when the button is pressed, and how they should react.
     * @param e event that is called.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        SeatReservation reservation;
        boolean complementary;
        updateSeats();
        // Check if one of the seating buttons is pressed
        for (int row = 0; row < this.seatRowAmount; row++)
        {
            for (int col = 0; col < this.seatColAmount; col++)
            {
                if (source == this.seatingButtons[row][col])
                {
                    if (this.adultButton.isSelected() || this.elderlyButton.isSelected() || this.childButton.isSelected())
                    {
                        // Set if it's complementary or not.
                        complementary = this.complementaryButton.isSelected();
                        if (this.adultButton.isSelected())
                        {
                            reservation = new AdultReservation(Conversion.convertIndexToRow(row), col);
                            reservation.setComplementary(complementary);
                            this.currentReservation.add(reservation);
                        }
                        else if (this.elderlyButton.isSelected())
                        {
                            reservation = new ElderlyReservation(Conversion.convertIndexToRow(row), col);
                            reservation.setComplementary(complementary);
                            this.currentReservation.add(reservation);
                        }
                        else if (this.childButton.isSelected())
                        {
                            reservation = new ChildReservation(Conversion.convertIndexToRow(row), col);
                            reservation.setComplementary(complementary);
                            this.currentReservation.add(reservation);
                        }
                        updateSeats();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Please select the type of ticket below", "Booking Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        
        // Create a new Seat Reservation
        if (source == this.newButton)
        {
            this.newButton.setBackground(this.secondaryColor);
            this.currentReservation.clear();
            manageTicketPanel(true);
            manageMovieSeats(true);
            updateSeats();
        }
        // Clear Current Reservations Made
        else if (source == this.cancelButton)
        {
            this.currentReservation.clear();
            manageTicketPanel(false);
            manageMovieSeats(false);
            updateSeats();
        }
        // Book Current Reservations
        else if (source == this.bookButton)
        {
            float totalPrice = 0.0f;
            int ticketCounter = 0;
            // Checking if there's an item selected in the list
            if (this.moviePlayingList.getSelectedValue() != null)
            {
                Session movie = (Session) this.moviePlayingList.getSelectedValue();
                // Check if applying bookings doesn't have any errors
                if (movie.applyBookings(this.currentReservation))
                {
                    // Add all the tickets from all reservations
                    for (; ticketCounter < this.currentReservation.size(); ticketCounter++)
                    {
                        totalPrice += this.currentReservation.get(ticketCounter).getTicketPrice();
                    }
                    JOptionPane.showMessageDialog(this, "TICKET COST IS: $" + totalPrice, ticketCounter + " Tickets Requested", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    // If there's an error, then there must be a child in a R-Rate film or without supervision
                    JOptionPane.showMessageDialog(this, "Cannot book a child in R-Rated Movies or Unsupervised in M-Rated Movies", ticketCounter + " Tickets Not Booked", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                // If a movie isn't selected
                JOptionPane.showMessageDialog(this, "Please select a movie first", "Booking Error", JOptionPane.ERROR_MESSAGE);
            }
            manageTicketPanel(false);
            manageMovieSeats(false);
            this.currentReservation.clear();
            updateSeats();
        }
        // Exit the GUI
        else if (source == this.exitButton)
        {
            this.currentReservation.clear();
            System.exit(0);
        }
    }

    /**
     * Listens to any activities that happens in the list, and act on it.
     * @param e event in the list that changed or called.
     */
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        // Checking if there's an item selected in the list
        if (this.moviePlayingList.getSelectedValue() != null)
        {
            // Check if they pressed another movie without booking / cancelling their seat reservations
            if (!this.currentReservation.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "CANCELLING BOOKING...", "Booking Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
            // If there's no seat reservations set or created
            Session movie = (Session) this.moviePlayingList.getSelectedValue();
            this.movieHeader.setText(movie.getMovie().getName());
            manageTicketPanel(false);
            manageMovieSeats(false); 
            this.newButton.setEnabled(true);
            this.currentReservation.clear();
            updateSeats();
        }
    }

    public static void main(String[] args)
    {
        // Initializing all the movies in an array
        Session[] myMovieSession = new Session[8];
        myMovieSession[0] = new Session(new Movie("SCP: The Movie", 'R'), new Time(16, 30));
        myMovieSession[1] = new Session(new Movie("The Predator", 'M'), new Time(4, 45, 30));
        myMovieSession[2] = new Session(new Movie("Justice League", 'G'), new Time(4, 30));
        myMovieSession[3] = new Session(new Movie("Deadpool 2", 'R'), new Time(4, 30));
        myMovieSession[4] = new Session(new Movie("Avengers: Infinity War", 'G'), new Time(2));
        myMovieSession[5] = new Session(new Movie("Logan", 'R'), new Time(7, 30));
        myMovieSession[6] = new Session(new Movie("Slenderman", 'M'), new Time(2, 30, 25));
        myMovieSession[7] = new Session(new Movie("Despasito", 'M'), new Time()); 
        ArrayList<Session> movies = new ArrayList(Arrays.asList(myMovieSession));
        Collections.sort(movies);

        // Send the movie array to the GUI
        GUI myPanel = new GUI(movies);
        JFrame frame = new JFrame("Movie Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myPanel);
        frame.pack();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(new Point((d.width / 2) - (frame.getWidth() / 2), (d.height / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }
}
