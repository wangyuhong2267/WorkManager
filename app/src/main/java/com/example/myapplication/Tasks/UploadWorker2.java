package com.example.myapplication.Tasks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class UploadWorker2 extends Worker {
    public UploadWorker2(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        Log.e("workmanager","UploadWorker dowork");


        NotificationChannel channel = new NotificationChannel("1","study", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setPackage("com.example.myapplication");
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Icon icon = Icon.createWithResource(getApplicationContext(), R.mipmap.ic_launcher);

        Notification.Action action = new Notification.Action.Builder(icon,"学习",pendingIntent)
                .build();
        Notification.Builder notification = new Notification.Builder(getApplicationContext(),"1").setSmallIcon(icon)
                .addAction(action);
        manager.notify(1,notification.build());
        return Result.success();
    }
}

