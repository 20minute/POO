package ca.uqac.registraire;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.tools.*;


public class ApplicationServeur {
	public static final int port = 8020;
	public ServerSocket serverSocket = null;
	public Socket socket =null;
	public Class classeDeLobjet =null;
	public Object pointeurObjet =null;
	
	/**
	* prend le numéro de port, crée un SocketServer sur le port
	*/
	public ApplicationServeur (int port) {
		try {
			serverSocket=new ServerSocket(port);//8020
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Se met en attente de connexions des clients. Suite aux connexions, elle lit
	* ce qui est envoyé à travers la Socket, recrée l’objet Commande envoyé par
	* le client, et appellera traiterCommande(Commande uneCommande)
	*/
	public void aVosOrdres() {
		try {
			Boolean run = true;
			while(run) {
            	socket = serverSocket.accept();
        	    InputStream input = socket.getInputStream();
				ObjectInput in = new ObjectInputStream(input);
				Commande result = (Commande)in.readObject();
				if(result!=null) {
					traiteCommande(result);		
				}else {
					run = false;
				}
				//System.out.println(result.typeCommande);
				socket.close();
			}  
			
			serverSocket.close();
        }catch (IOException e) {
        	e.printStackTrace();
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* prend uneCommande dument formattée, et la traite. Dépendant du type de commande,
	* elle appelle la méthode spécialisée
	*/
	public void traiteCommande(Commande uneCommande) {
		String type = uneCommande.typeCommande;
		String rest = uneCommande.rest;
		String[] rests = null;
		String nomAttribut = null;
		switch(type) {
		case "compilation" :
				System.out.println("BEGIN compilation");
				traiterCompilation(rest);
				System.out.println("END");
				break;
		case "chargement" :
				System.out.println("BEGIN chargement");
				traiterChargement(rest);
				System.out.println("END");
				break;
		case "creation" :
				System.out.println("BEGIN creation");
				traiterCreation(classeDeLobjet,rest);
				System.out.println("END");
				break;
		case "ecriture" :
				System.out.println("BEGIN ecriture");
				rests=rest.split("#");
				nomAttribut = rests[1];
				Object valeur = rests[2];
				traiterEcriture(pointeurObjet, nomAttribut,valeur);
				System.out.println("END");
				break;
		case "lecture" :
				System.out.println("BEGIN lecture");
				rests=rest.split("#");
				nomAttribut = rests[1];
				traiterLecture(pointeurObjet, nomAttribut);
				System.out.println("END");
				break;
		case "fonction" :
				break;
		default:
				break;
		
		}
	}
	/**
	* traiterEcriture : traite l’écriture d’un attribut. Confirmes au client que l’écriture
	* s’est faite correctement.
	*/
	public void traiterEcriture(Object pointeurObjet, String attribut, Object valeur) {
		System.out.println("traiter ecriture...");
		
		try {
			Field champ = pointeurObjet.getClass().getDeclaredField(attribut);
			champ.setAccessible(true);
			champ.set(pointeurObjet, valeur);
			System.out.println("ecriture ok");
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			OutputStream o = socket.getOutputStream();
			ObjectOutput s=new ObjectOutputStream(o);
			s.writeObject("ecriture ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	/**
	* traiterLecture : traite la lecture d’un attribut. Renvoies le résultat par le
	* socket
	*/
	public void traiterLecture(Object pointeurObjet, String attribut) {
		System.out.println("traiter lecture...");
		Object obj=null;
		
		try {
			Field champ = pointeurObjet.getClass().getDeclaredField(attribut);
			champ.setAccessible(true);
			obj = champ.get(pointeurObjet);
			System.out.println(obj.toString());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			OutputStream o = socket.getOutputStream();
			ObjectOutput s=new ObjectOutputStream(o);
			s.writeObject(attribut+" : "+ obj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	/**
	* traiterCreation : traite la création d’un objet. Confirme au client que la création
	* s’est faite correctement.
	*/
	public void traiterCreation(Class classeDeLobjet, String rest) {
			System.out.println("traiter creation... ");
			String[] rests = rest.split("#");
			String identificateur = rests[1];
			
			Constructor constructor1 = null;
			
				try {
					constructor1 = classeDeLobjet.getConstructor(String.class);
					
					pointeurObjet = constructor1.newInstance(identificateur);
					System.out.println(pointeurObjet.toString());
					OutputStream o = socket.getOutputStream();
					ObjectOutput s=new ObjectOutputStream(o);
					s.writeObject("creation d'object: "+ pointeurObjet.toString());
				} catch (InstantiationException  | IllegalAccessException 
						| IllegalArgumentException | InvocationTargetException 
						| IOException |NoSuchMethodException 
						| SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			 
				
	}
	
	/**
	* traiterChargement : traite le chargement d’une classe. Confirmes au client que la création
	* s’est faite correctement.
	*/
	public void traiterChargement(String nomQualifie) {
		System.out.println("traiter chargement...");
//		try {
//				classeDeLobjet = Class.forName(nomQualifie);
//			    String className = classeDeLobjet.getName();
//			    System.out.println("class name:"+className);
//			    System.out.println("traiter chargement ends ");
//		    
//		    
//				OutputStream o = socket.getOutputStream();
//				ObjectOutput s=new ObjectOutputStream(o);
//				s.writeObject("chargement ok");
//				
//		}catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}	
		
		ClassLoader cl = ApplicationServeur.class.getClassLoader();
		try {
			classeDeLobjet = cl.loadClass(nomQualifie);
	        System.out.println("classeDeLobjet.getName() = " + classeDeLobjet.getName());
	        OutputStream o = socket.getOutputStream();
			ObjectOutput s=new ObjectOutputStream(o);
		s.writeObject("chargement ok");
		}catch(ClassNotFoundException | IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	* traiterCompilation : traite la compilation d’un fichier source java. Confirme au client
	* que la compilation s’est faite correctement. Le fichier source est donné par son chemin
	* relatif par rapport au chemin des fichiers sources.
	*/
	public void traiterCompilation(String cheminRelatifFichierSource) {
				/*./src/ca/uqac/registraire/Cours.java,./src/ca/uqac/registraire/Etudiant.java#./classes*/
				String[] rests=cheminRelatifFichierSource.split("#");
				/*./src/ca/uqac/registraire/Cours.java,./src/ca/uqac/registraire/Etudiant.java*/
				String sources = rests[0];
				/*//./classes*/
				String cheminClass = rests[1];
				String[] cheminSources = sources.split(",");
				int success = 0 ;
				for(int i = 0; i< cheminSources.length; i++)
				{
					try {
						JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
						
						int compilationResult = compiler.run(null, null, null, cheminSources[i]);
						if(compilationResult == 0)
						{
							System.out.println("compilation OK");
							success ++;
						}else {
							System.out.println("compilation erreur");
						}
	
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				try {
					OutputStream o = socket.getOutputStream();
					ObjectOutput s=new ObjectOutputStream(o);
					if(success==cheminSources.length) {
						s.writeObject("compilation ok");
					}else {
						s.writeObject("compilation erreur");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}	
	}
	/**
	* programme principal. Prend 4 arguments: 1) numéro de port, 2) répertoire source, 3)
	* répertoire classes, et 4) nom du fichier de traces (sortie)
	* Cette méthode doit créer une instance de la classe ApplicationServeur, l’initialiser
	* puis appeler aVosOrdres sur cet objet
	*/
	public static void main(String[] args) {

		ApplicationServeur appServeur = new ApplicationServeur(port);
		appServeur.aVosOrdres();
		
	}
}

