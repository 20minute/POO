package ca.uqac.registraire;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;


public class ApplicationClient {

	/**
	* prend le fichier contenant la liste des commandes, et le charge dans une
	* variable du type Commande qui est retournée
	*/
	public Commande saisisCommande(BufferedReader fichier) {
		String s = "";
		String ss[] = new String[100];
		Commande commande = new Commande();
		try {
			s=fichier.readLine();
			 ss=s.split("#",2);
	         commande.typeCommande=ss[0];
	         commande.rest=ss[1];
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return commande;
	}
	
	

	
	public static void main(String[] args) {
		ApplicationClient appclient = new ApplicationClient();
		Commande c =new Commande();
		String s ="asd";
		String ss[] = new String[100];
		try
	      {
	         FileReader fileIn = new FileReader("I:/JEEworkspace2/TP1/tmp/commandes.txt");
	         BufferedReader fichier = new BufferedReader(fileIn);
	         c=appclient.saisisCommande(fichier);
	         System.out.print(c.typeCommande);
	 		System.out.print("\n---\n");
	 		System.out.print(c.rest);
	 		c=appclient.saisisCommande(fichier);
	         System.out.print(c.typeCommande);
	 		System.out.print("\n---\n");
	 		System.out.print(c.rest);
	 		c=appclient.saisisCommande(fichier);
	         System.out.print(c.typeCommande);
	 		System.out.print("\n---\n");
	 		System.out.print(c.rest);
	 
	         fichier.close();
	    }catch(IOException e){
	    	e.printStackTrace();
	    	return;	
	    }
		
		
		
	}
}
