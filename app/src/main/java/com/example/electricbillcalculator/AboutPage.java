package com.example.electricbillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.myphoto);

        TextView textView = findViewById(R.id.textView);
        textView.setText("Siti Nurzulaikha binti Tajuddin");

        TextView websiteLink = findViewById(R.id.websiteLink);
        websiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the website URL in a browser
                String url = "https://github.com/snzlkha/Electricity-Bill.git";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}