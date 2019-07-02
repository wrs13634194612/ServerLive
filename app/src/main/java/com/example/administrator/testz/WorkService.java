package com.example.administrator.testz;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by wrs on 2019/7/2,15:28
 * projectName: Testz
 * packageName: com.example.administrator.testz
 */
public class WorkService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ToastUtils.showShort("保持心跳:");
                    Log.e("TAG", "保持心跳");
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(1, new Notification());
        bindService(new Intent(this, DaemonService.class), mServiceConnection, Context.BIND_IMPORTANT);
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
            //在建立到服务的连接时调用，该连接使用到服务的通信通道的IBinder。
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //当与服务的连接丢失时调用。
            startService(new Intent(WorkService.this, DaemonService.class));
            bindService(new Intent(WorkService.this, DaemonService.class), mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
