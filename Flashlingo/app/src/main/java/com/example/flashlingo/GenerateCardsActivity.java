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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateCardsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generate_cards);

        HashMap<String,ArrayList<Card>> lernset = new HashMap<>();
        String LernsetName = getIntent().getStringExtra("lernsetName");
        Button createCardsBtn = findViewById(R.id.addBtn);
        Button doneBtn = findViewById(R.id.DoneBtn);
        ArrayList <Card> cardList = new ArrayList<>();

        createCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText begriffEdt = findViewById(R.id.begriffEditText);
                EditText definitionEdt = findViewById(R.id.definitionEditText);
                String begriff = begriffEdt.getText().toString();
                String definition = definitionEdt.getText().toString();
                Card card = new Card(begriff,definition);
                cardList.add(card);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lernset.put(LernsetName,cardList);
                Intent Lernsetintent = new Intent(GenerateCardsActivity.this, LernsetAuswahlActivity.class);
                Lernsetintent.putExtra("lernset", lernset);
                startActivity(Lernsetintent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}