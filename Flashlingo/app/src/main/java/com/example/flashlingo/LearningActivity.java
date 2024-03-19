package com.example.flashlingo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LearningActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private static final float SHAKE_THRESHOLD = 8.0f;

    Dialog dialog;
    private static final int SHAKE_INTERVAL_TIME = 2000;

    private long lastShakeTime = 0;

    Integer falseCounter = 0;
    Float falsePercentage;
    Float rightPercentage;

    public void calculateResult(ArrayList<Card> cards) {
        Integer cardSize = cards.size();
        Float cardSizePercentage = 100F;
        falsePercentage = (cardSizePercentage / cardSize * falseCounter);
        rightPercentage = 100F - falsePercentage;
    }

    private void saveCardList(ArrayList<Card> cardList) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cardList);
        editor.putString("cardList", json);
        editor.apply();
    }

    private ArrayList<Card> loadCardList() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cardList", null);
        Type type = new TypeToken<ArrayList<Card>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learning);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }

        Intent intent = getIntent();
        ArrayList<Card> cardList = (ArrayList<Card>) intent.getSerializableExtra("learnsetCards");
        saveCardList(cardList);
        ArrayList<Card> loadedCardList = loadCardList();

        if (loadedCardList != null) {
            cardList = loadedCardList;
        }

        TextView cardTextView = findViewById(R.id.CardText);
        Button falseButton = findViewById(R.id.FalseBtn);
        Button rightButton = findViewById(R.id.RightBtn);

        Vibrator vibrator;
        int[] currentIndex = {0};

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        ArrayList<Card> finalCardList = cardList;
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardTextView.getText().toString().isEmpty() && !(falseCounter > finalCardList.size())) {
                    falseCounter++;
                }
                currentIndex[0]++;
                if (currentIndex[0] < finalCardList.size()) {
                    Card nextCard = finalCardList.get(currentIndex[0]);
                    cardTextView.setText(nextCard.cardWord);
                } else {
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                    calculateResult(finalCardList);
                    dialog.show();
                    TextView dialogText = dialog.findViewById(R.id.ResultTextView);
                    if (falsePercentage > rightPercentage) {
                        dialogText.setText("I would study again " + falsePercentage.toString() + "% of the cards are wrong");
                    } else {
                        dialogText.setText("Not bad only " + falsePercentage.toString() + "% of the cards are wrong");
                    }
                }
            }
        });

        ArrayList<Card> finalCardList1 = cardList;
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentIndex[0]++;
                if (currentIndex[0] < finalCardList1.size()) {
                    Card nextCard = finalCardList1.get(currentIndex[0]);
                    cardTextView.setText(nextCard.cardWord);
                } else {
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                    calculateResult(finalCardList1);
                    TextView dialogText = dialog.findViewById(R.id.ResultTextView);
                    if (falsePercentage > rightPercentage) {
                        dialogText.setText("I would study again " + falsePercentage.toString() + " of the cards are wrong");
                    } else {
                        dialogText.setText("Not bad only " + falsePercentage.toString() + " of the cards are wrong");
                    }
                    dialog.show();
                }
            }
        });

        for (Card cardElem : cardList) {

            cardTextView.setText(cardElem.cardWord);

            cardTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cardTextView.getText().toString().equals(cardElem.cardWord)) {
                        cardTextView.setText(cardElem.cardDefinition);
                    } else {
                        cardTextView.setText(cardElem.cardWord);
                    }
                }
            });
        }

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_custom_dialog);

        Button okayButton = dialog.findViewById(R.id.btn_okay);
        Button cancelButton = dialog.findViewById(R.id.btn_cancel);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            long currentTime = System.currentTimeMillis();

            if (acceleration > SHAKE_THRESHOLD && currentTime - lastShakeTime > SHAKE_INTERVAL_TIME) {
                lastShakeTime = currentTime;

                if (x > 4.0f) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView cardTextView = findViewById(R.id.CardText);
                            animateTextViewToLeft(cardTextView);
                            Button falseButton = findViewById(R.id.FalseBtn);
                            falseButton.performClick();
                        }
                    });
                } else if (x < -4.0f) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView cardTextView = findViewById(R.id.CardText);
                            animateTextViewToRight(cardTextView);
                            Button rightButton = findViewById(R.id.RightBtn);
                            rightButton.performClick();
                        }
                    });
                }
            }
        }
    }

    private void animateTextViewToLeft(final TextView textView) {
        final int originalTranslationX = 0; // Store the original translationX value

        Animation animation = new TranslateAnimation(0, -1000, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.VISIBLE);
                textView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        textView.startAnimation(animation);
    }

    private void animateTextViewToRight(final TextView textView) {
        final int originalTranslationX = 0; // Store the original translationX value

        Animation animation = new TranslateAnimation(0, 1000, 0, 0);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.VISIBLE);
                textView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        textView.startAnimation(animation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
