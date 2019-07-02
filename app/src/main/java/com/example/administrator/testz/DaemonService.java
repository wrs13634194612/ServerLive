package com.example.administrator.testz;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by wrs on 2019/7/2,15:28
 * projectName: Testz
 * packageName: com.example.administrator.testz
 */
public class DaemonService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程优先级 让此服务在前台运行，在此状态下向用户提供正在进行的通知
        startForeground(1, new Notification());
        bindService(new Intent(this, WorkService.class), mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IProcessConnection.Stub() {
        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(DaemonService.this, WorkService.class));
            bindService(new Intent(DaemonService.this, WorkService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
