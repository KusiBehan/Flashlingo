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

import java.util.ArrayList;

public class GenerateCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generate_cards);
        String learnSetName = getIntent().getStringExtra("learnSetName");
        Button createCardsBtn = findViewById(R.id.addBtn);
        Button doneBtn = findViewById(R.id.DoneBtn);
        ArrayList<Card> cardList = new ArrayList<>();
        createCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText termEditText = findViewById(R.id.termEditText);
                EditText definitionEditText = findViewById(R.id.definitionEditText);
                String term = termEditText.getText().toString();
                String definition = definitionEditText.getText().toString();
                Card card = new Card(term, definition);
                cardList.add(card);
                termEditText.setText("");
                definitionEditText.setText("");
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Learnset.getInstance().setLearnsetValues(learnSetName, cardList);
                Intent mainMenuIntent = new Intent(GenerateCardsActivity.this, MainActivity.class);
                startActivity(mainMenuIntent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
