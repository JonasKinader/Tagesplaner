package com.musterfirma.tagesplaner.model;

import java.io.Serializable;


public class Aufgaben implements Serializable {

    private String aufgabe;
    private boolean erledigt;


    //Konstruktor
    public Aufgaben(String aufgabe, boolean erledigt){
        this.aufgabe = aufgabe;
        this.erledigt = erledigt;
    }

    //Getter für aufgabe
    public String getAufgabe(){

        return aufgabe;
    }

    //Setter für aufgabe
    public void setAufgabe(String aufgabe){
        if (aufgabe == null || aufgabe.trim() .isEmpty()){
            this.aufgabe = "Leere Aufgabe";
        }
        else {
            this.aufgabe = aufgabe;
        }
    }

    // Getter für erledigt
    public boolean istErledigt(){
        return erledigt;
    }

    //Setter für erledigt
    public void setErledigt(boolean erledigt){
        this.erledigt = erledigt;
    }

}
