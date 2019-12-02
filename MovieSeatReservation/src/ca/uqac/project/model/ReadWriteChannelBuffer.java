package ca.uqac.project.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadWriteChannelBuffer {

	public static void main(String[] args) { 
	try {
	RandomAccessFile aFile = new RandomAccessFile("src/sample.txt", "rw");
	FileChannel inChannel = aFile.getChannel(); 
	ByteBuffer buf = ByteBuffer.allocate(48); 
	int bytesRead = inChannel.read(buf);  // channel writes into a buffer (read into buffer using channel)
	while (bytesRead != -1) { 
		System.out.println("Read " + bytesRead); 
		buf.flip(); 		//change du mode écriture au mode lecture
		while(buf.hasRemaining()) { 
			System.out.print((char) buf.get());  // lecture données du buffer directement avec get()
		} 
		buf.clear(); 
		bytesRead = inChannel.read(buf); 
	} 
	aFile.close(); 
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	}
}
