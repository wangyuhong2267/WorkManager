package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myapplication.Tasks.MainWorkManager2;
import com.example.myapplication.Tasks.MainWorkManager3;
import com.example.myapplication.Tasks.MainWorkManager4;
import com.example.myapplication.Tasks.MainWorkManager5;
import com.example.myapplication.Tasks.UploadWorker2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .setRequiresBatteryNotLow(true).build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(UploadWorker2.class,300, TimeUnit.MILLISECONDS)
                .setConstraints(constraints).build();

        OneTimeWorkRequest workRequest1 = new OneTimeWorkRequest.Builder(UploadWorker2.class).setConstraints(constraints)
                .setInitialDelay(200,TimeUnit.MILLISECONDS)
                .build();

        OneTimeWorkRequest workRequest2 = new OneTimeWorkRequest.Builder(UploadWorker2.class).setConstraints(constraints)
                .setInitialDelay(400,TimeUnit.MILLISECONDS)
                .build();

        OneTimeWorkRequest workRequest3 = new OneTimeWorkRequest.Builder(UploadWorker2.class).setConstraints(constraints)
                .setInitialDelay(400,TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(this).beginWith(workRequest1).then(workRequest2).then(workRequest3).then(workRequest3).enqueue();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.e("workmanager","onchanged");
                    }
                });

    }

    //单个任务
    public void test1(View view) {
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();

        WorkManager.getInstance((getApplicationContext())).enqueue(oneTimeWorkRequest);
    }

    //任务传递数据 activity到任务
    public void test2(View view) {

        //传递数据到任务
        //我必须传递 Derry key

        //数据
        Data sendData = new Data.Builder().putString("Derry", "九阳神功").build();

        //请求对象初始化
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorkManager1.class)
                .setInputData(sendData)//数据的携带
                .build();

        //请求对象加入到队列
        WorkManager.getInstance((getApplicationContext())).enqueue(oneTimeWorkRequest);
    }

    //任务传递数据 任务到activity
    public void test3(View view) {

        //传递数据到任务
        //我必须传递 Derry key


        //数据
        Data sendData = new Data.Builder().putString("Derry", "九阳神功").build();


        //请求对象初始化
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorkManager1.class)
                .setInputData(sendData)//数据的携带
                .build();


        //如何获取 任务的回馈数据
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {


                        //状态机
                        Log.d("wyh", "状态：" + workInfo.getState().name());

                        if (workInfo.getState().isFinished()) {
                            Log.d("wyh", "状态：isFinished=true 后台任务已经完成。。。" );
                            Log.d("wyh", "Activity取到了任务回传的数据：" + workInfo.getOutputData().getString("Derry"));
                        }
                    }
                });

        //请求对象加入到队列
        WorkManager.getInstance((getApplicationContext())).enqueue(oneTimeWorkRequest);


    }

    //多个任务
    public void test4(View view) {

        OneTimeWorkRequest oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(MainWorkManager2.class).build();
        OneTimeWorkRequest oneTimeWorkRequest3 = new OneTimeWorkRequest.Builder(MainWorkManager3.class).build();
        OneTimeWorkRequest oneTimeWorkRequest4 = new OneTimeWorkRequest.Builder(MainWorkManager4.class).build();
        OneTimeWorkRequest oneTimeWorkRequest5 = new OneTimeWorkRequest.Builder(MainWorkManager5.class).build();

        List<OneTimeWorkRequest> oneTimeWorkRequestLists = new ArrayList<>();
        oneTimeWorkRequestLists.add(oneTimeWorkRequest2);
        oneTimeWorkRequestLists.add(oneTimeWorkRequest3);
        oneTimeWorkRequestLists.add(oneTimeWorkRequest5);
        WorkManager.getInstance(this).beginWith(oneTimeWorkRequestLists)
                .then(oneTimeWorkRequest4)
                .enqueue();
    }

    // TODO 约束条件
    public void test5(View view) {

        //NoeTimeWorkRequest 上面我们用到的 单一Request
        //轮训 多Request

        //重复的任务 多次 循环 最少循环 15分钟
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MainWorkManager2.class,10, TimeUnit.SECONDS).build();

        //如何获取 任务的回馈数据
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(periodicWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {


                        //状态机
                        Log.d("wyh", "状态：" + workInfo.getState().name());

                        if (workInfo.getState().isFinished()) {
                            Log.d("wyh", "状态：isFinished=true 后台任务已经完成。。。" );
                            Log.d("wyh", "Activity取到了任务回传的数据：" + workInfo.getOutputData().getString("Derry"));
                        }
                    }
                });

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);


    }

    //约束条件2
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test6(View view) {
        Log.d("wyh", "111 ");
        //约束条件， 必须满足我的条件，才能执行后台任务 （在连接网络，插入电源 并且 处于空闲时） 内部做了电量优化（Android app 不耗电）
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)//充电中
//                .setRequiresDeviceIdle(true)//空闲时。。。（没有玩游戏）

                .build();


        /*
        * 除了上面的约束外，WorkManager还提供了以下的约束作为Work执行条件
        * setRequiredNetworkType  网络连接设置
        * setRequiresBatteryNotLow 是否为低电量时运行 默认 false】
        * setRequiresCharging 是否接入电源 默认false
        * setRequiresDeviceIdle 设备是否空闲 false
        * setRequiresStorageNotLow 设备可用存储是否低于临界阀值、
        *
        *
        * */

        //请求对象初始化
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MainWorkManager2.class)
                .setConstraints(constraints)//
                .build();

        //加入队列
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    //
}