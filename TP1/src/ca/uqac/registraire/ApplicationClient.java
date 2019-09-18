package ca.uqac.registraire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
  * Cette classe a été écrite afin de réaliser la partie utilisateur demandée pour le TP1
  * @author Zheng Lijun & Fussien Paul
  * @Version 1.01
  */

public class ApplicationClient {
	/**
	 * Cette fonction prend en entrée la liste de commande comme indiquée sur le TP
	 * celle-ci est chargée dans une variable du type Commande qui sera retourné
	 * @param fichier
	 * @return null
	 * @return commande
	 */
	public Commande saisisCommande(BufferedReader fichier) {
		String s = "";
		String ss[] = new String[100];
		Commande commande = new Commande();
		try {
			 s=fichier.readLine();//prend une ligne de commande
			 ss=s.split("#",2); // separer le string par 2 parties
	         commande.typeCommande=ss[0]; // premiere partie est la type de commande
	         commande.rest=ss[1];// deuximere partie est la reste de commande
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return commande;
	}
	
	/**
	* initialise : ouvre les différents fichiers de lecture et écriture
	*/
	public void initialise(String fichCommandes, String fichSortie) {
		
		try{
	         FileReader fileIn = new FileReader(fichCommandes);
	         BufferedReader fichier = new BufferedReader(fileIn);
	         

	         
	    }catch(IOException e){
	    	e.printStackTrace();
	    	return;	
	    }
	}

	public Object traiteCommande(Commande uneCommande) {
		
		try{
			//1.Créer un nouveau Socket
			Socket soc=new Socket(InetAddress.getLocalHost(),8020);
			//2.Obtenir un OutputStream à partir du socket
			OutputStream o=soc.getOutputStream();
			//3.Créer un Object OutputStream à partir du OutputStream
			ObjectOutput s=new ObjectOutputStream(o);
			//4.Ecrire les objets
			s.writeObject(uneCommande);
			
			
			
			InputStream input = soc.getInputStream();
			ObjectInput in = new ObjectInputStream(input);
			Object result = in.readObject();
			//5.Fermer les Stream
			s.flush();
			
			s.close();
			soc.close();
			return result;
			
			}catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(1);
			return null;
			}
		
	}
	
	
	  public void scenario(BufferedReader commandesReader) {
		  System.out.println("Debut des traitements:");
		    Commande prochaine = saisisCommande(commandesReader);
		    while (prochaine != null) {
		    	System.out.println("\tTraitement de la commande " + prochaine.typeCommande + " ...");
		      Object resultat = traiteCommande(prochaine);
		      System.out.println("\t\tResultat: " + resultat);
		      prochaine = saisisCommande(commandesReader);
		    }
		    System.out.println("Fin des traitements");
		  }

	
	public static void main(String[] args) {
		int port = 8020;
		String hostname = "127.0.0.1";
		String fichCommandes = "./tmp/commandes.txt";
		String fichSortie = "./tmp/fichSortie.txt";
		ApplicationClient appClient = new ApplicationClient();
		//appClient.initialise(fichCommandes, fichSortie);

		
		try{
	         FileReader fileIn = new FileReader(fichCommandes);
	         BufferedReader fichier = new BufferedReader(fileIn);
	         
	 		appClient.scenario(fichier);

	         
	    }catch(IOException e){
	    	e.printStackTrace();
	    	return;	
	    }
		

	}
		
		
	
}
