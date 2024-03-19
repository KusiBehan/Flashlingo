package com.example.flashlingo;

import com.example.flashlingo.Card;

import java.util.ArrayList;
import java.util.HashMap;

public class Lernset {

    private static Lernset instance;

   private HashMap<String, ArrayList<Card>> lernset;

    public HashMap<String, ArrayList<Card>> getLernset() {
        return lernset;
    }

    public void setLernset(HashMap<String, ArrayList<Card>> lernset) {
        this.lernset = lernset;
    }

    public void setLernsetValues(String lernsetname, ArrayList<Card> cardList){
        this.lernset.put(lernsetname,cardList);
    }

    private Lernset() {
        // Initialize shared data if needed
        lernset = new HashMap<String, ArrayList<Card>>();
    }

    public static synchronized Lernset getInstance() {
        // Create the instance if it doesn't exist
        if (instance == null) {
            instance = new Lernset();
        }
        return instance;
    }
}
