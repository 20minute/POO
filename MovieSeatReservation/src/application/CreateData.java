package application;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import ca.uqac.project.model.*;

public class CreateData {

	private ArrayList<Session> movies = new ArrayList<Session>();
	private ArrayList<Info> seatInfoList;
    
	
	public CreateData() {
		seatInfoList = new ArrayList<Info>();
		
	}
	
	private void createData()
	{
        movies.add(new Session(1,new Movie( "SCP: The Movie", 'R'), new Time(16, 30)));
		movies.add(new Session(2,new Movie( "The Predator", 'M'), new Time(4, 45, 30)));
		movies.add(new Session(3,new Movie( "Justice League", 'G'), new Time(4, 30)));
		movies.add(new Session(4,new Movie( "Deadpool 2", 'R'), new Time(4, 30)));
		movies.add(new Session(5,new Movie( "Avengers: Infinity War", 'G'), new Time(2)));
		movies.add(new Session(6,new Movie( "Logan", 'R'), new Time(7, 30)));
		movies.add(new Session(7,new Movie( "Slenderman", 'M'), new Time(2, 30, 25)));
		movies.add(new Session(8,new Movie( "Avenger End", 'G'), new Time(1, 45, 30)));
		movies.add(new Session(9,new Movie( "Despasito", 'M'), new Time()));
	}
	
	public ArrayList<Session> getSessions() {
		createData();
		createFiles();
		
		return movies;
	}
	
	private void createFiles() {
		try {
			for(Session session : movies)
			{
				String uri = "src/" + session.getId() + ".txt";
				
				System.out.println(uri);
				RandomAccessFile aFile = new RandomAccessFile(uri, "rw");
				FileChannel      channel = aFile.getChannel();
			
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream (baos);
				oos.writeObject(session);
				oos.flush();
				channel.write (ByteBuffer.wrap (baos.toByteArray()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public int getMoviesSize() {
		return movies.size();
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
	
	public void reserveSeats(Session currentSession) {
		try {
			
			String uri = "src/" + currentSession.getId() + ".txt";
			
			System.out.println(uri);
			RandomAccessFile aFile = new RandomAccessFile(uri, "rw");
			FileChannel      channel = aFile.getChannel();
		
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream (baos);
			System.out.println("preSession:"+currentSession.toString());
			oos.writeObject(currentSession);
			oos.flush();
			channel.write (ByteBuffer.wrap (baos.toByteArray()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public ArrayList<Session> readSessions() {
        try {
        	Path startingDir = Paths.get("I:\\JEEworkspace2\\POO\\MovieSeatReservation\\src");

            FileFinder  filterFilesVisitor= new FileFinder(".txt");
            Files.walkFileTree(startingDir, filterFilesVisitor);
	        for (String name : filterFilesVisitor.getFilenameList()) {
	        	RandomAccessFile aFile = new RandomAccessFile(name, "rw");
				FileChannel      channel = aFile.getChannel();
			
				ObjectInputStream ois = new ObjectInputStream (Channels.newInputStream(channel));
				Session session = (Session) ois.readObject();
				System.out.println("final:"+session.toString());
				movies.add(session);
	        }
        } catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
        return movies;
	}
	
}
