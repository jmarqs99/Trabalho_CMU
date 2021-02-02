package com.example.myapplication;

import android.app.ActivityManager;
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
import android.content.SharedPreferences;
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
    private Notification notification;
    private final int TIME_BETWEEN_QUESTIONS = 60;//90 * 60; // em segundos
    private static boolean running = false;
    private static int notificationId = 10;

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
    public void onCreate() {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification =
                new Notification.Builder(this, "testeNot")
                        .setContentTitle("FutQuiz")
                        .setContentText("Estamos á procura de um novo quiz para ti!")
                        .setSmallIcon(R.drawable.question_mark)
                        .setContentIntent(pendingIntent)
                        .setTicker("Ticker")
                        .build();

        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!running) {
            startForeground(1, notification);
            running = true;
            final Context context = this;
            execute = true;

            final SharedPreferences mPrefs = getSharedPreferences("lastQuestion", 0);
            new Thread() {
                @Override
                public void run() {
                    while (execute) {
                        Long lastTimestamp = mPrefs.getLong("timestamp", 0);
                        if (System.currentTimeMillis() / 1000 - lastTimestamp > TIME_BETWEEN_QUESTIONS) {
                            mPrefs.edit().putLong("timestamp", System.currentTimeMillis() / 1000).commit();

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "testeNot")
                                    .setSmallIcon(R.drawable.question_mark)
                                    .setContentTitle("FutQuiz")
                                    .setContentText("Novo Quiz á tua espera!")
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                            notificationManager.notify(notificationId, builder.build());
                            notificationId++;
                        }
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Teste";
            String description = "tete123";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("testeNot", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
