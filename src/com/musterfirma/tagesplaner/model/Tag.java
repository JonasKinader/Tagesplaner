package com.musterfirma.tagesplaner.model;

import java.io.*;
import java.util.ArrayList;

public class Tag implements Serializable {

    private String tagesnamen;
    private ArrayList<Aufgaben> aufgabenListe;

    //Konstruktor
    public Tag (String tagesnamen){
        this.tagesnamen = tagesnamen;
        this.aufgabenListe = new ArrayList<>();
    }

    // Getter für tagesnamen
    public String getTagesnamen(){
        return tagesnamen;
    }

    //Setter für tagesnamen

    public void setTagesnamen(String tagesnamen){
        if (tagesnamen == null || tagesnamen.trim() .isEmpty()){
            this.tagesnamen = "Kein Tagesnamen";
        }
        else {
            this.tagesnamen = tagesnamen;
        }
    }

    //Getter für aufgabenliste
    public ArrayList<Aufgaben> getAufgabenListe(){
        return aufgabenListe;
    }

    //setter für aufgabenliste
    public void setAufgabenListe(ArrayList<Aufgaben> aufgabenListe){
        this.aufgabenListe = aufgabenListe;
    }

    //aufgabe hinzufügen
    public void  addAufgabe(Aufgaben aufgabe) {
        this.aufgabenListe.add(aufgabe);
    }





}
