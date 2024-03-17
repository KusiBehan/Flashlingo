package com.example.flashlingo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LernsetAuswahlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lernset_auswahl);

        Intent intent = getIntent();
        HashMap<String,List<Card>> hashmap = (HashMap<String,List<Card>>) getIntent().getSerializableExtra("lernset");
        Button button = new Button(this);

        hashmap.forEach((key,value) -> {
            
        });

        button.setText("Ume");
        LinearLayout ll = (LinearLayout)findViewById(R.id.BtnLayout);
        ll.addView(button);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}