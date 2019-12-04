package application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import ca.uqac.project.model.*;

public class TransferTool {


	private ArrayList<Info> seatInfoList;
    
    
    public TransferTool() {
    	seatInfoList = new ArrayList<Info>();
    	
    }
	public void getSeatInfo(SeatReservation seatReservation) {
		
		int type = -1;
		System.out.println(seatReservation.getClass().toString()); 
		
		if(seatReservation instanceof ChildReservation){type = 1;} 
		else if(seatReservation instanceof AdultReservation){type = 2;} 
		else if (seatReservation instanceof ElderlyReservation){type = 3;} 
		
		char row = seatReservation.getRow();
		int col = seatReservation.getCol();
			
		seatInfoList.add(new Info(row,col,type));
	}
	
	public void writeInfo() {
		try {
			RandomAccessFile aFile = new RandomAccessFile("src/nio-data.txt", "rw");
			FileChannel      channel = aFile.getChannel();
		
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream (baos);
			oos.writeObject(seatInfoList);
			oos.flush();
			channel.write (ByteBuffer.wrap (baos.toByteArray()));
//			byte[] objData = baos.toByteArray(); // get the byte array
//			ByteBuffer buffer = ByteBuffer.wrap(objData);  // wrap around the data
//			buffer.flip(); //prep for writing
//			
//			while(buffer.hasRemaining()) {
//				channel.write(buffer); //write
//			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void readInfo() {
		try {
			RandomAccessFile aFile = new RandomAccessFile("src/nio-data.txt", "rw");
			FileChannel      channel = aFile.getChannel();
		
			ObjectInputStream ois = new ObjectInputStream (Channels.newInputStream(channel));
			
			Object test = ois.readObject();
			System.out.println(test.toString());
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
