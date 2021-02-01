package com.example.myapplication;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NewQuestionsService extends Service {
    private boolean execute;

    public NewQuestionsService() {
        super();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification =
                new Notification.Builder(this, "testeNot")
                        .setContentTitle("FutQuiz")
                        .setContentText("Estamos á procura de um novo quiz para ti!")
                        .setSmallIcon(R.drawable.question_mark)
                        .setContentIntent(pendingIntent)
                        .setTicker("Ticker")
                        .build();

        startForeground(1, notification);

        final Context context = this;
        execute = true;
        new Thread() {
            @Override
            public void run() {
                while (execute) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "testeNot")
                            .setSmallIcon(R.drawable.question_mark)
                            .setContentTitle("FutQuiz")
                            .setContentText("Novo Quiz á tua espera!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    notificationManager.notify(0, builder.build());
                    try {
                        Thread.sleep(90 * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        execute = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        execute = false;
        super.onDestroy();
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
