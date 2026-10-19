package com.example.countrylabapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String[] COUNTRIES = {
        "Россия", "США", "Германия", "Франция",
        "Япония", "Бразилия", "Италия", "Китай"
    };

    private static final int[] FLAGS = {
        R.drawable.flag_russia,
        R.drawable.flag_usa,
        R.drawable.flag_germany,
        R.drawable.flag_france,
        R.drawable.flag_japan,
        R.drawable.flag_brazil,
        R.drawable.flag_italy,
        R.drawable.flag_china
    };

    private static final int[] FLAG_ANIMS = {
        R.anim.flag_rotate,
        R.anim.flag_scale,
        R.anim.flag_slide_left,
        R.anim.flag_slide_right,
        R.anim.flag_flip,
        R.anim.flag_drop,
        R.anim.flag_zoom_rotate,
        R.anim.flag_fade_scale
    };

    private static final int[] TEXT_ANIMS = {
        R.anim.text_shake,
        R.anim.text_pulse,
        R.anim.text_slide_up,
        R.anim.text_flash,
        R.anim.text_bounce,
        R.anim.text_rotate_in,
        R.anim.text_scale_x,
        R.anim.text_zoom_fade
    };

    private static final int[] SOUNDS = {
        R.raw.sound1, R.raw.sound2, R.raw.sound3, R.raw.sound4,
        R.raw.sound5, R.raw.sound6, R.raw.sound7, R.raw.sound8
    };

    private ImageView flagImageView;
    private TextView countryNameText;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagImageView = findViewById(R.id.flagImageView);
        countryNameText = findViewById(R.id.countryNameText);
        ListView listView = findViewById(R.id.listView);

        CountryAdapter adapter = new CountryAdapter(this, COUNTRIES);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Анимация текста в элементе списка
            TextView tv = view.findViewById(R.id.countryText);
            if (tv != null) {
                Animation textAnim = AnimationUtils.loadAnimation(this, TEXT_ANIMS[position]);
                tv.startAnimation(textAnim);
            }

            // Показываем название страны
            countryNameText.setVisibility(View.VISIBLE);
            countryNameText.setText(COUNTRIES[position]);
            Animation nameAnim = AnimationUtils.loadAnimation(this, TEXT_ANIMS[position]);
            countryNameText.startAnimation(nameAnim);

            // Показываем флаг с анимацией
            flagImageView.setVisibility(View.VISIBLE);
            flagImageView.setImageResource(FLAGS[position]);
            Animation flagAnim = AnimationUtils.loadAnimation(this, FLAG_ANIMS[position]);
            flagImageView.startAnimation(flagAnim);

            // Воспроизводим звук
            playSound(position);
        });
    }

    private void playSound(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        try {
            mediaPlayer = MediaPlayer.create(this, SOUNDS[position]);
            if (mediaPlayer != null) {
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.release();
                    mediaPlayer = null;
                });
                mediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    static class CountryAdapter extends ArrayAdapter<String> {
        CountryAdapter(Context context, String[] countries) {
            super(context, R.layout.list_item, R.id.countryText, countries);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
            }
            TextView tv = convertView.findViewById(R.id.countryText);
            tv.setText(getItem(position));
            return convertView;
        }
    }
}