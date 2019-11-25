package ca.uqac.project.model;

public class ChildReservation extends SeatReservation
{

    /**
     * Constructor that sends in the row and column information back to the Seat Reservation parent.
     * @param row which row the seat is in.
     * @param col which column the seat is in.
     */
    public ChildReservation(char row, int col)
    {
        super(row, col);
    }

    /**
     * A Method that returns the ticket price, and checks if the reservation is complementary or not for a Child Reservation.
     * @return the ticket price of the Child Seat Reservation.
     */
    @Override
    public float getTicketPrice()
    {
        if (complementary)
        {
            return 0.0f;
        }
        else
        {
            return 8.0f;
        }
    }
}
