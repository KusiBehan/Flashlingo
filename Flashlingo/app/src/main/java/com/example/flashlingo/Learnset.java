package com.example.flashlingo;

import java.util.ArrayList;
import java.util.HashMap;

public class Learnset {

    private static Learnset instance;

    private HashMap<String, ArrayList<Card>> learnset;

    public HashMap<String, ArrayList<Card>> getLearnset() {
        return learnset;
    }

    public void setLearnset(HashMap<String, ArrayList<Card>> learnset) {
        this.learnset = learnset;
    }

    public void setLearnsetValues(String learnsetName, ArrayList<Card> cardList){
        this.learnset.put(learnsetName, cardList);
    }

    private Learnset() {
        // Initialize shared data if needed
        learnset = new HashMap<String, ArrayList<Card>>();
    }

    public static synchronized Learnset getInstance() {
        // Create the instance if it doesn't exist
        if (instance == null) {
            instance = new Learnset();
        }
        return instance;
    }
}
