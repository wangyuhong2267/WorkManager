package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

public class MainWorkManager1 extends Worker {

    public final static String TAG = MainWorkManager1.class.getSimpleName();

    private Context mContext;
    private WorkerParameters workerParams;



    public MainWorkManager1(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.mContext = context;
        this.workerParams = workerParams;
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {

        Log.d("wyh", "doWork:run2 ... ");

        final String dataString = workerParams.getInputData().getString("Derry");

        Log.d("wyh", "doWork: 接收Activity传递过来的数据：" + dataString);


        //反馈数据给 MainActivity
        //把任务中的数据回传到activity中

//数据
        Data outputData = new Data.Builder().putString("Derry", "三分归元气").build();

        @SuppressLint("RestrictedApi") Result.Success success = new Result.Success(outputData);


        return success;


//        return Result.success();
    }
}
