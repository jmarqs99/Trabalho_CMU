package com.example.myapplication;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NewQuestionsService extends IntentService {
    public NewQuestionsService() {
        super("NewQuestionsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "testeNot")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("TEste")
                .setContentText("testeee")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Teste";
            String description = "tete123";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("testeNot", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
