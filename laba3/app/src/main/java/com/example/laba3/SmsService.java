package com.example.laba3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

public class SmsService extends Service {

    private static final String CHANNEL_ID = "SmsServiceChannel";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LABA3_TAG", "Сервис создан");
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LABA3_TAG", "Сервис получил команду старт");

        // ОБЯЗАТЕЛЬНО для Foreground Service (Android 8.0+)
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Сервис SMS")
                .setContentText("Отправка сообщения...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        startForeground(1, builder.build());

        // Получаем данные из интента
        String phone = null;
        String message = null;
        if (intent != null) {
            phone = intent.getStringExtra("PHONE_EXTRA");
            message = intent.getStringExtra("MESSAGE_EXTRA");
        }

        Log.d("LABA3_TAG", "Сервис пытается отправить SMS на номер: " + phone);

        try {
            if (phone != null && message != null) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, message, null, null);
                Log.d("LABA3_TAG", "SMS успешно отправлено!");
                Toast.makeText(this, "SMS отправлено из Сервиса!", Toast.LENGTH_LONG).show();
            } else {
                Log.e("LABA3_TAG", "Ошибка: данные пустые");
            }
        } catch (Exception e) {
            Log.e("LABA3_TAG", "Ошибка отправки: " + e.getMessage());
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Останавливаем сервис после выполнения работы
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LABA3_TAG", "Сервис остановлен");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "SMS Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}