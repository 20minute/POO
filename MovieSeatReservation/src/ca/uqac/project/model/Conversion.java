package ca.uqac.project.model;

public interface Conversion {

	/**
     * A method that converts an char of row to a integer of index to be used in an array.
     * @param rowLetter the character of the row.
     * @return the index of the character of the row based on the characters.
     */
    public static int convertRowToIndex(char rowLetter)
    {
        return (rowLetter - '0') - 17;
    }
    
    /**
     * A method that converts an integer of index to a char of row to be used in a seat reservation.
     * @param rowIndex the index of the character of the row.
     * @return the character of the row based on the index.
     */
    public static char convertIndexToRow(int rowIndex)
    {
        return (char) ((rowIndex + '0') + 17);
    }
}
