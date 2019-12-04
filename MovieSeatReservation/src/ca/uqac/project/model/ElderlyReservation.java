package ca.uqac.project.model;

public class ElderlyReservation extends SeatReservation
{
	public static float discount = 0.3f;
    /**
     * Constructor that sends in the row and column information back to the Seat Reservation parent.
     * @param row which row the seat is in.
     * @param col which column the seat is in.
     */
    public ElderlyReservation(char row, int col)
    {
        super(row, col);
    }

    /**
     * A Method that returns the ticket price, and checks if the reservation is complementary or not for an Elderly Reservation.
     * @return the ticket price of the Elderly Seat Reservation.
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
            AdultReservation adultPrice = new AdultReservation('A', 0);
            float price = adultPrice.getTicketPrice() - (adultPrice.getTicketPrice() * discount);

            return price;
        }
    }

}
