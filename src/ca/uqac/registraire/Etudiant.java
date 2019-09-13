package ca.uqac.registraire;
import java.util.*;
/**
 * Title:        Classes for TP1
 * Description:
 * Copyright:    Copyright (c) 2015
 * Company:      UQAC
 * @author Hamid Mcheick et Hafedh Mili
 * @version 1.0
 */

public class Etudiant {

  private String nom;

  private Vector<Cours> cours;

  public Etudiant() {
    cours = new Vector<Cours>();
  }

  public Etudiant(String unNom) {
    this();
    nom = unNom;
  }

  public void inscrisDansCours(Cours unCours) {
    cours.add(unCours);
    unCours.ajouteEtudiant(this);
  }

  public void setNom(String unNom) {
    nom = unNom;
  }

  public String getNom() {
    return nom;
  }

  public float getMoyenne() {
    float totalNotes = 0;
    int nombreCours = 0;
    Iterator<Cours> lesCours = cours.iterator();
    while (lesCours.hasNext()) {
      totalNotes = totalNotes + ((Cours)lesCours.next()).getNote(this);
      nombreCours++;
    }
    return totalNotes/nombreCours;
  }

  public String toString() {
    return nom + "[" + getMoyenne() + "]";
  }
}