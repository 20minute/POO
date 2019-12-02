package ca.uqac.project.model;

import java.io.IOException;
import java.nio.file.AccessMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class PathManip {
	public static void main(String[] args) {
		Path filePath = Paths.get("src/sample.txt");
		// Obtenir the Absolute Path :)
		Path fullPath = filePath.toAbsolutePath();

		int count = fullPath.getNameCount();
		System.out.println("Path is " + fullPath.toString());
		System.out.println("File name is " + fullPath.getFileName());
		System.out.println("---");

		System.out.println("There are " + count + " elements in the file path");
		for (int x = 0; x < count; ++x)
			System.out.println("Element " + x + " is " + fullPath.getName(x));
		System.out.println("---");

		// Checking File Accessibility
		try {
			filePath.getFileSystem().provider().checkAccess(filePath, AccessMode.READ, AccessMode.EXECUTE);
			System.out.println("File can be read and executed");
		} catch (IOException e) {
			System.out.println("File cannot be used for this application");
			e.printStackTrace();
		}
		System.out.println("---");
		
		// Get Ze Attributes
		try {
			BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
			System.out.println("Creation time " + attr.creationTime());
			System.out.println("Last modified time " + attr.lastModifiedTime());
			System.out.println("Size " + attr.size());
		} catch (IOException e) {
			System.out.println("IO Exception");
		}
		System.out.println("---");

	}
}

