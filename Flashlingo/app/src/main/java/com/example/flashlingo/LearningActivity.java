package com.example.flashlingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class LearningActivity extends AppCompatActivity {

    Integer falseCounter = 0;
    Float falsePercentage;
    Float rightPercantage;

    public void calculateResult(ArrayList<Card> cards) {
        HashMap<String, Float> result = new HashMap<>();
        Integer cardSize = cards.size();
        Float cardSizePercentage = 100F;
        falsePercentage = (cardSizePercentage / cardSize * falseCounter);
        rightPercantage = 100F - falsePercentage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learning);
        Intent intent = getIntent();
        ArrayList<Card> cardlist = (ArrayList<Card>) intent.getSerializableExtra("learnsetCards");
        TextView card = findViewById(R.id.CardText);
        Button falseBtn = findViewById(R.id.FalseBtn);
        Button rightBtn = findViewById(R.id.RightBtn);

        int[] currentIndex = {0};

        falseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(card.getText().toString().isEmpty())) {
                    falseCounter += 1;
                }

                currentIndex[0]++;
                if (currentIndex[0] < cardlist.size()) {
                    Card nextCard = cardlist.get(currentIndex[0]);
                    card.setText(nextCard.cardWord);
                } else {
                    calculateResult(cardlist);
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex[0]++;
                if (currentIndex[0] < cardlist.size()) {
                    Card nextCard = cardlist.get(currentIndex[0]);
                    card.setText(nextCard.cardWord);
                } else {
                    calculateResult(cardlist);
                }
            }
        });

        for (Card cardElem : cardlist) {

            card.setText(cardElem.cardWord);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (card.getText().toString().equals(cardElem.cardWord)){
                        card.setText(cardElem.cardDefinition);
                    }
                    else {
                        card.setText(cardElem.cardWord);
                    }
                }
            });
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}