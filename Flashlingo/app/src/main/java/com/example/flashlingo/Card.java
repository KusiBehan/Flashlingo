package com.example.flashlingo;


import java.io.Serializable;

public class Card implements Serializable{
    String cardDefinition;
    String cardWord;

    public Card(String cardWord, String cardDefinition) {
        this.cardWord = cardWord;
        this.cardDefinition = cardDefinition;
    }

}

