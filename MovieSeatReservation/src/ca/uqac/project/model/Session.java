package ca.uqac.project.model;

import java.util.List;



public class Session implements Conversion,Comparable<Session>{

	private Movie movie;
	private Time sessionTime;
	private SeatReservation[][] sessionSeats;
	public static int NUM_ROWS = 8; //How many Rows of Seats
    public static int NUM_COLS = 6; //How many Columns of Seats
	
	
	 /**
     * Constructor to set the movie's name, rating, how many seats, and session time.
     * @param movieName Name of the movie.
     * @param rating Rating of the movie (G/M/R).
     * @param sessionTime Time the movie starts (HOURS:MINUTES:SECONDS).
     */
    public Session(Movie movie, Time sessionTime)
    {
        this.movie = movie;
        this.sessionTime = sessionTime;
        this.sessionSeats = new SeatReservation[Session.NUM_ROWS][Session.NUM_COLS];
    }
    /**
     * Method to get the Movie.
     * @return the Movie.
     */
	public Movie getMovie() {
		return movie;
	}
	/**
     * Method to get the Session class session time.
     * @return the Movie Session's session time.
     */
	public Time getSessionTime() {
		return sessionTime;
	}

	/**
     * A method to get the seat specified by the caller.
     * @param row which seat row.
     * @param col which seat column. 
     * @return the seat based on its row and column parameters.
     */
    public SeatReservation getSeat(char row, int col)
    {
        return sessionSeats[Conversion.convertRowToIndex(row)][col];
    }

    /**
     * A method to check if the seat is available, true is if it's available, false if it's not.
     * @param row which seat row.
     * @param col which seat column.
     * @return a boolean indicating if it's available or not.
     */
    public boolean isSeatAvailable(char row, int col)
    {
        return (this.sessionSeats[Conversion.convertRowToIndex(row)][col] == null);
    }
    
    /**
     * Applies the booking set, and if it's true, then apply them in the MovieSession.
     * If it's false, then deny the set reservation that was tried to be booked.
     * @param reservations The seat reservations requested to be booked by the caller.
     * @return a boolean to determine if the reservation is successful or not.
     */
    public boolean applyBookings(List<SeatReservation> reservations)
    {
        for (int z = 0; z < reservations.size(); z++)
        {
            if (getMovie().getRating() == 'R' && reservations.get(z) instanceof ChildReservation)
            {
                // Children are restricted to watch R-rated movies
                System.out.println("CHILDREN CANNOT WATCH R-RATED FILMS !!");
                return false;
            }
        }
        
        // Loop through the Reservation List for checking
        for (int x = 0; x < reservations.size(); x++)
        {
            if (getMovie().getRating() == 'R' && reservations.get(x) instanceof ChildReservation)
            {
                // Children are restricted to watch R-rated movies
                System.out.println("CHILDREN CANNOT WATCH R-RATED FILMS !!");
                return false;
            }
            else if (getMovie().getRating() == 'M' && reservations.get(x) instanceof ChildReservation)
            {
                boolean adultFound = false;
                for (int y = 0; y < reservations.size(); y++)
                {
                    // Check if it's anything other than a child reservation (Adult and Elderly)
                    if (!(reservations.get(y) instanceof ChildReservation))
                    {
                        // Found a Child with an Adult
                        System.out.println("Found a Child with an Adult !!");
                        adultFound = true;
                        break;
                    }
                }
                if (!adultFound)
                {
                    // No Child Found with an Adult
                    System.out.println("THERE'S NO ADULTS FOUND TO ACCOMPANY THE CHILD !!");
                    return false;
                }
            }
            if (isSeatAvailable(reservations.get(x).getRow(), reservations.get(x).getCol()))
            {
                // Saves the reservation into the 2D Array sessionSeats
                this.sessionSeats[Conversion.convertRowToIndex(reservations.get(x).getRow())][reservations.get(x).getCol()] = reservations.get(x);
            }
            else
            {
                // Seat is not Available (Not null)
                System.out.println("ONE OR MORE OF THE SEATS SET IS NOT AVAILABLE !!");
                return false;
            }
        }
        // If every seat available check or rating check went fine, then return true
        return true;
    }
    
    /**
     * Converts the data of the current movie session into a string that shows all its data.
     * @return a string of the movie session with all its information.
     */
    @Override
    public String toString()
    {
    	int rest = 0;
        String movieInfo = String.format("(" + getMovie().getRating() + ") " + getMovie().getName() +  " - [" + this.sessionTime + "]");
//        if(sessionSeats == null) 
//        {
//        	
//        	for(int i = 0 ; i <NUM_ROWS ;i++ ){
//            	for(int j = 0; j <NUM_COLS ; j++) {
//            		if(!sessionSeats[i][j].complementary)
//            		{
//            			rest++;
//            		}
//            	}
//            }
//            String result = movieInfo + "\n This session has " + rest +"seats left.\n ";
//            return result;
//        }
        return movieInfo;
    }

    /**
     * Overridden method used to compare different movie sessions.
     * @param otherMovieSession
     * @return
     */
    @Override
    public int compareTo(Session otherMovieSession)
    {
        // If both times are equal
        if (this.sessionTime.compareTo(otherMovieSession.sessionTime) == 0)
        {
            // Compare their movie names
            return getMovie().getName().compareTo(otherMovieSession.getMovie().getName());
        }
        else
        {
            // If not, then compare their time
            return this.sessionTime.compareTo(otherMovieSession.getSessionTime());
        }
    }
}
