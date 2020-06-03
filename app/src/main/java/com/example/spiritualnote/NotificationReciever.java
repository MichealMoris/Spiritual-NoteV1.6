package com.example.spiritualnote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class NotificationReciever extends BroadcastReceiver {
    private String[] notificationVerses;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationVerses = context.getResources().getStringArray(R.array.notification_verses);
        int randomIndex = new Random().nextInt(notificationVerses.length);
        String randomVerse = notificationVerses[randomIndex];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("NotificationChannel", "Spiritual Note", importance);
            channel.setDescription("Your Way To God...");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent2 = new Intent(context, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationChannel")
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle("النــوتــة الــروحيــة")
                .setContentText(randomVerse)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());

    }
}

