package com.example.laba3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.laba3.SEND_SMS_ACTION".equals(intent.getAction())) {

            String phone = intent.getStringExtra("PHONE_EXTRA");
            String message = intent.getStringExtra("MESSAGE_EXTRA");

            Log.d("LABA3_TAG", "Ресивер поймал данные: " + phone + " / " + message);
            Toast.makeText(context, "Broadcast получен! Запускаем сервис...", Toast.LENGTH_SHORT).show();

            Intent serviceIntent = new Intent(context, SmsService.class);
            serviceIntent.putExtra("PHONE_EXTRA", phone);
            serviceIntent.putExtra("MESSAGE_EXTRA", message);

            // Для Android 8.0+ используем startForegroundService
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            } else {
                context.startService(serviceIntent);
            }
        }
    }
}