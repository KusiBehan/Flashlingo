package com.example.flashlingo;

import java.io.Serializable;

public class Card implements Serializable {
    String cardDefinition;
    String cardWord;

    public Card(String cardWord, String cardDefinition) {
        this.cardWord = cardWord;
        this.cardDefinition = cardDefinition;
    }

    public String getCardWord() {
        return cardWord;
    }

    public void setCardWord(String cardWord) {
        this.cardWord = cardWord;
    }

    public String getCardDefinition() {
        return cardDefinition;
    }

    public void setCardDefinition(String cardDefinition) {
        this.cardDefinition = cardDefinition;
    }
}
