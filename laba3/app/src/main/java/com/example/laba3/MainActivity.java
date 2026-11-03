package com.example.laba3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private EditText editTextPhone;
    private EditText editTextMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSendBroadcast = findViewById(R.id.buttonSendBroadcast);

        // Запрашиваем разрешения при старте приложения
        requestPermissions();

        buttonSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LABA3_TAG", "Кнопка нажата!");

                String phone = editTextPhone.getText().toString().trim();
                String message = editTextMessage.getText().toString().trim();

                Log.d("LABA3_TAG", "Данные из полей: " + phone + " / " + message);

                if (phone.isEmpty() || message.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Заполните номер и текст!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Отправляем Broadcast
                Intent intent = new Intent("com.example.laba3.SEND_SMS_ACTION");
                intent.putExtra("PHONE_EXTRA", phone);
                intent.putExtra("MESSAGE_EXTRA", message);

                sendBroadcast(intent);
                Log.d("LABA3_TAG", "Отправлен Broadcast с данными: " + phone + " / " + message);
                Toast.makeText(MainActivity.this, "Broadcast отправлен. Ждем ресивер...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Метод запроса разрешений (SMS и Уведомления)
    private void requestPermissions() {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{Manifest.permission.SEND_SMS, Manifest.permission.POST_NOTIFICATIONS};
        } else {
            permissions = new String[]{Manifest.permission.SEND_SMS};
        }

        boolean needRequest = false;
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                needRequest = true;
                break;
            }
        }

        if (needRequest) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("LABA3_TAG", "Разрешения получены");
            } else {
                Toast.makeText(this, "Без разрешений отправка SMS невозможна!", Toast.LENGTH_LONG).show();
            }
        }
    }
}