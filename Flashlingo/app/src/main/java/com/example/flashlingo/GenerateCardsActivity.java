package com.example.flashlingo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class GenerateCardsActivity extends AppCompatActivity {

    public class Card {
        String cardDefinition;
        String cardWord;

        public Card(String cardWord, String cardDefinition) {
            this.cardWord = cardWord;
            this.cardDefinition = cardDefinition;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generate_cards);
        HashMap<String,Card> lernset = new HashMap<String,Card>();

        String LernsetName = getIntent().getStringExtra("lernsetName");
        Button createCardsBtn = findViewById(R.id.addBtn);

        createCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText begriffEdt = findViewById(R.id.begriffEditText);
                EditText definitionEdt = findViewById(R.id.definitionEditText);
                String begriff = begriffEdt.getText().toString();
                String definition = definitionEdt.getText().toString();
                Card card = new Card(begriff,definition);
                lernset.put(LernsetName,card);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}