package application;

import java.io.Serializable;

public class Info implements Serializable{

	private char row;
    private int col;
    private int type;
    
	public Info(char row, int col, int type) {
		super();
		this.row = row;
		this.col = col;
		this.type = type;
	}
}
