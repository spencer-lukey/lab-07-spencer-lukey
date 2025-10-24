package com.example.androiduitesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayCityActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity);

        // Get the text view to print the text of the selected city
        TextView receivedTextView = findViewById(R.id.received_text_view);

        Intent intent = getIntent();
        if (intent != null) {
            String receivedString = intent.getStringExtra("city_string");

            receivedTextView.setText(receivedString);
        }


        // Set back button onClick listener
        Button backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}


