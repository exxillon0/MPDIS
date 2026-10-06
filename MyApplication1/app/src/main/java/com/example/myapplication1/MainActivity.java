package com.example.myapplication1;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Переменные для хранения состояния
    private int colorIndex = 0;
    private int sizeIndex = 0;
    private int styleIndex = 0;
    private int familyIndex = 0;

    // Массивы для переключения значений
    private final int[] colors = {Color.TRANSPARENT, Color.LTGRAY, Color.parseColor("#FFB3BA")};
    private final float[] sizes = {32f, 40f, 24f};
    private final int[] styles = {Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC};
    private final String[] families = {"serif", "sans", "monospace"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Находим элементы
        ScrollView scrollView = findViewById(R.id.scrollView);
        TextView tvLarge = findViewById(R.id.tvLarge);

        Button btnBgColor = findViewById(R.id.btnBgColor);
        Button btnFontSize = findViewById(R.id.btnFontSize);
        Button btnFontStyle = findViewById(R.id.btnFontStyle);
        Button btnFontFamily = findViewById(R.id.btnFontFamily);

        Button btnHorizontal1 = findViewById(R.id.btnHorizontal1);
        Button btnHorizontal2 = findViewById(R.id.btnHorizontal2);
        Button btnVertical1 = findViewById(R.id.btnVertical1);
        Button btnVertical2 = findViewById(R.id.btnVertical2);
        ImageButton btnImage = findViewById(R.id.btnImage);

        // 1. Изменение цвета экрана
        btnBgColor.setOnClickListener(v -> {
            colorIndex = (colorIndex + 1) % colors.length;
            scrollView.setBackgroundColor(colors[colorIndex]);
        });

        // 2. Изменение размера шрифта
        btnFontSize.setOnClickListener(v -> {
            sizeIndex = (sizeIndex + 1) % sizes.length;
            tvLarge.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizes[sizeIndex]);
        });

        // 3. Изменение жирности и наклона (с сохранением текущего типа шрифта)
        btnFontStyle.setOnClickListener(v -> {
            styleIndex = (styleIndex + 1) % styles.length;
            Typeface currentFamily = tvLarge.getTypeface();
            String familyName = "serif"; // по умолчанию
            if (currentFamily != null) {
                if (currentFamily.equals(Typeface.SANS_SERIF)) familyName = "sans";
                else if (currentFamily.equals(Typeface.MONOSPACE)) familyName = "monospace";
            }
            tvLarge.setTypeface(Typeface.create(familyName, styles[styleIndex]));
        });

        // 4. Изменение типа шрифта
        btnFontFamily.setOnClickListener(v -> {
            familyIndex = (familyIndex + 1) % families.length;
            tvLarge.setTypeface(Typeface.create(families[familyIndex], styles[styleIndex]));
        });

        // 5. Кнопки размещения (просто показывают всплывающее сообщение)
        btnHorizontal1.setOnClickListener(v -> Toast.makeText(this, "Нажата горизонтальная кнопка 1", Toast.LENGTH_SHORT).show());
        btnHorizontal2.setOnClickListener(v -> Toast.makeText(this, "Нажата горизонтальная кнопка 2", Toast.LENGTH_SHORT).show());
        btnVertical1.setOnClickListener(v -> Toast.makeText(this, "Нажата вертикальная кнопка 1", Toast.LENGTH_SHORT).show());
        btnVertical2.setOnClickListener(v -> Toast.makeText(this, "Нажата вертикальная кнопка 2", Toast.LENGTH_SHORT).show());
        btnImage.setOnClickListener(v -> Toast.makeText(this, "Нажата кнопка с картинкой!", Toast.LENGTH_SHORT).show());
    }
}