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

    public HashMap<String, Float> calculateResult(ArrayList<Card> cards) {
        HashMap<String, Float> result = new HashMap<>();
        Integer cardSize = cards.size();
        Float cardSizePercentage = 100F;
        Float falsePercentage = (cardSizePercentage / cardSize * falseCounter);
        Float rightPercentage = 100F - falsePercentage;
        result.put("right", rightPercentage);
        result.put("false", falsePercentage);
        return result;
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


        cardlist.forEach((n) -> {
            card.setText(n.cardWord);

            falseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(card.getText().toString().isEmpty())) {
                        falseCounter += 1;
                        return;
                    }
                }
            });
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    card.setText(n.cardDefinition);
                }
            });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}