package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import static androidx.core.content.ContextCompat.startActivity;

public class UploadWorker extends Worker {
    public UploadWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        try {
            Thread.sleep(6000);
           /* Intent intent = new Intent();
            intent.setClassName("com.example.myapplication", "MainActivity");
            startActivity(intent);*/
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }finally {
            Log.d("wyh", "doWork:run ... ");
        }
        return Result.success();
    }
}
