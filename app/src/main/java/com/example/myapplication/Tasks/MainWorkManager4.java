package com.example.myapplication.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

public class MainWorkManager4 extends Worker {

    public final static String TAG = MainWorkManager4.class.getSimpleName();

    private Context mContext;
    private WorkerParameters workerParams;



    public MainWorkManager4(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
        this.workerParams = workerParams;
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {

        Log.d("wyh", "doWork:run4 ... ");




        return Result.success();


//        return Result.success();
    }
}
