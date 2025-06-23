package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HelpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.helpCenter), (v, insets) -> {
            WindowInsetsCompat insetsCompat = insets;
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insetsCompat.getInsets(systemBars).left,
                    insetsCompat.getInsets(systemBars).top,
                    insetsCompat.getInsets(systemBars).right,
                    insetsCompat.getInsets(systemBars).bottom);
            return insets;
        });

        TextView answerText = findViewById(R.id.answerText);
        ImageView toggleButton = findViewById(R.id.toggleButton);
        ImageButton backButton = findViewById(R.id.backButton);

        toggleButton.setOnClickListener(v -> {
            if (answerText.getVisibility() == View.VISIBLE) {
                answerText.setVisibility(View.GONE);
                toggleButton.setImageResource(R.drawable.down_arrow);
            } else {
                answerText.setVisibility(View.VISIBLE);
            }
        });

        backButton.setOnClickListener(v -> finish());
    }
}
