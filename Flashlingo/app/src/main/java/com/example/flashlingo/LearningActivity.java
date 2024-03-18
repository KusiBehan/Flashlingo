package com.example.flashlingo;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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

public class LearningActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorMgr;
    private Sensor accelerometer;

    private static final float SHAKE_THRESHOLD = 8.0f;

    // Time interval between two shake events
    private static final int SHAKE_INTERVAL_TIME = 500;

    private long lastShakeTime = 0;



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

        sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null){
            sensorMgr.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        }
        else
        {
            //
        }

        Intent intent = getIntent();
        ArrayList<Card> cardlist = (ArrayList<Card>) intent.getSerializableExtra("learnsetCards");
        TextView card = findViewById(R.id.CardText);
        Button falseBtn = findViewById(R.id.FalseBtn);
        Button rightBtn = findViewById(R.id.RightBtn);

        Vibrator vibrator;
        int[] currentIndex = {0};

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

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
                    if (Build.VERSION.SDK_INT >= 26){
                        vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else {
                        vibrator.vibrate(500);
                    }
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
                    if (Build.VERSION.SDK_INT >= 26){
                        vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    else {
                        vibrator.vibrate(500);
                    }
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

    @Override
    protected void onResume(){
        super.onResume();
        if (accelerometer != null) {
            sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorMgr.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            if (acceleration > SHAKE_THRESHOLD && System.currentTimeMillis() - lastShakeTime > SHAKE_INTERVAL_TIME) {
                if (x > 2.0f) {
                    TextView view = findViewById(R.id.CardText);
                    view.setText("left");
                } else if (x < -2.0f) {
                    TextView view = findViewById(R.id.CardText);
                    view.setText("right");
                } else {
                    TextView view = findViewById(R.id.CardText);
                    view.setText("center");
                }
                lastShakeTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}