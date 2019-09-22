package ca.uqac.registraire;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.tools.*;

/**
 *  Title:        Classes for TP1
 * Description:
 * Copyright:    Copyright (c) 2019
 * Company:      UQAC
 * @author Zheng Lijun & Fussien Paul
 * @Version 1.01
 */
public class ApplicationServeur {
	public static final int port = 8020;
	public ServerSocket serverSocket = null;
	public Socket socket =null;
	public Class classeDeLobjet =null;
	public Object pointeurObjet =null;
	public ArrayList<Object> pointeurObjets = new ArrayList<Object>();
	public int index = 0; // index of object
	
	/**
	 * Permet d'établir un port pour créer un socket sur celui-ci
	 * @param port
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
	 * Cette fonction permet de prendre uneCommande pour la traiter avec une méthode spécialisée
	 * @param uneCommande
	 */
	public void traiteCommande(Commande uneCommande) {
		String type = uneCommande.typeCommande;
		String rest = uneCommande.rest;
		String[] rests = null;
		String nomAttribut = null;
		String identificateur = null;
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
				identificateur = rests[0]; 
				nomAttribut = rests[1];
				Object valeur = rests[2];
				for(Object o: pointeurObjets) {
					if(o.toString().equals(identificateur)) {
						pointeurObjet = o;
					}
						
				}
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
				System.out.println("BEGIN fonction");
				rests=rest.split("#");
				identificateur = rests[0];
				String nomFonction = rests[1];
				String[] types = null;
				Object[] valeurs = null;
				if(rests.length > 2) {
					String parametre = rests[2];
					String parametres[] = parametre.split(",");
					types = new String[parametres.length];
					valeurs = new Object[parametres.length];
					for(int i = 0;i < parametres.length;i++)
					{
						if (parametres[i].contains("ID")) //l’argument de la fonction est un autre des objets
						{
							types[i] = parametres[i].split(":")[0];
							String StrValeur = parametres[i].split(":")[1].substring(3, parametres[i].split(":")[1].length()-1);
							for(Object o : pointeurObjets) {
								if(o.toString().contains(StrValeur) ){
									valeurs[i] = o;
								}
							}
						}else {
							types[i] = parametres[i].split(":")[0];
							String StrValeur = parametres[i].split(":")[1];
							valeurs[i] = Float.valueOf(StrValeur);
						}
					}
					
				}
				for(Object o : pointeurObjets) {
					if(o.toString().contains(identificateur)) {
						pointeurObjet = o;
					}
				}
				traiterAppel(pointeurObjet,nomFonction,types,valeurs);
				System.out.println("END");
				
				break;
		default:
				break;
		
		}
	}

	/**
	 * Traite l'écriture d'un attibut et renvoie une confirmation au client
	 * @param pointeurObjet
	 * @param attribut
	 * @param valeur
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
	 * Traite la lecture d'un attribut et renvoie la réponse par la socket
	 * @param pointeurObjet
	 * @param attribut
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
	 * Traite la création d'un objet avec une confirmation renvoyée au client
	 * @param classeDeLobjet
	 * @param rest
	 */
	public void traiterCreation(Class classeDeLobjet, String rest) {
			System.out.println("traiter creation... ");
			String[] rests = rest.split("#");
			String identificateur = rests[1];
			
			Constructor constructor1 = null;
			
				try {
					constructor1 = classeDeLobjet.getConstructor(String.class);
					
					pointeurObjets.add(constructor1.newInstance(identificateur));
					pointeurObjet = constructor1.newInstance(identificateur);
					
					System.out.println(pointeurObjets.toString());
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
	/**
	 * Traite le chargement d'un classe avec le retour de la création
	 * @param nomQualifie
	 */
	public void traiterChargement(String nomQualifie) {
		System.out.println("traiter chargement...");
		//method 1
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
		// method 2
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
	 * @param cheminRelatifFichierSource
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
	* traiterAppel : traite l’appel d’une méthode, en prenant comme argument l’objet
	* sur lequel on effectue l’appel, le nom de la fonction à appeler, un tableau de nom de
	* types des arguments, et un tableau d’arguments pour la fonction. Le résultat de la
	* fonction est renvoyé par le serveur au client (ou le message que tout s’est bien
	* passé)
	**/
	public void traiterAppel(Object pointeurObjet, String nomFonction, String[] types, Object[] valeurs) {
		System.out.println("Appel de la fonction: " + nomFonction);
		Class[] t = null;
		if(types!=null) {
			t = new Class[types.length];
			for(int i = 0; i < types.length; i++){
				try {
					if(types[i].contains("float")) {
						t[i] = float.class;
					}else {
						t[i]= Class.forName (types[i]);
						//System.out.println((((String) types[i]).indexOf(".")>0?"":"java.lang.") + (String)types[i]);
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		try {
			Method m = pointeurObjet.getClass().getMethod(nomFonction,t);
			Object result = m.invoke(pointeurObjet, valeurs);
			OutputStream o = socket.getOutputStream();
			ObjectOutput s=new ObjectOutputStream(o);
			if(result!=null) {
				s.writeObject("appel fonction - "+ nomFonction+ " ,result - " +result);
			}else {
				s.writeObject("appel fonction - "+ nomFonction+ " ok" );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * programme principal. Prend 4 arguments: 1) numéro de port, 2) répertoire source, 3)
	 * répertoire classes, et 4) nom du fichier de traces (sortie)
	 * Cette méthode doit créer une instance de la classe ApplicationServeur, l’initialiser
	 * puis appeler aVosOrdres sur cet objet
	 * @param args
	 */
	public static void main(String[] args) {

		ApplicationServeur appServeur = new ApplicationServeur(port);
		appServeur.aVosOrdres();
		
	}
}

