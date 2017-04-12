package com.yan.campusbbs.module;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.yan.campusbbs.R;


/**
 * Created by yan on 2017/4/12.
 */

public class CampusBBSService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initNotification();

    }


    public void initNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.campus_logo)
                .setLargeIcon(BitmapFactory
                        .decodeResource(getApplication().getResources(), R.drawable.campus_logo))
                .setContentTitle("校园论坛")
                .setSubText("校园论坛正在后台运行")
                .setContentIntent(pIntent);
        startForeground(1, builder.build());
    }
}
