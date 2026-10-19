package com.example.laba2_dop_zadaniye;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        TextView textTitle = findViewById(R.id.detailCountryName);
        TextView textDesc = findViewById(R.id.detailCountryDesc);
        ImageView imageFlag = findViewById(R.id.detailFlagImage);
        Button buttonBack = findViewById(R.id.buttonBack);

        String countryName = getIntent().getStringExtra("COUNTRY_NAME");
        String countryDesc = getIntent().getStringExtra("COUNTRY_DESC");
        int flagResId = getIntent().getIntExtra("COUNTRY_FLAG", 0);

        if (countryName != null) {
            textTitle.setText(countryName);
        }

        if (countryDesc != null) {
            textDesc.setText(countryDesc);
        }

        if (flagResId != 0) {
            imageFlag.setImageResource(flagResId);
        } else {
            imageFlag.setVisibility(View.GONE);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}