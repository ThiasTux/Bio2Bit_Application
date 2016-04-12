package com.st.bio2bit.controller;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;

import com.st.BlueSTSDK.Manager;
import com.st.BlueSTSDK.Node;
import com.st.bio2bit.R;
import com.st.bio2bit.uicontroller.activities.DataActivity;
import com.st.bio2bit.utilities.Const;

public class CommunicationService extends Service {

    private Manager mManager;
    private Node mConnectedNode;
    private PendingIntent pstartRecordingIntent;
    private PendingIntent ppauseRecordingIntent;
    private PendingIntent pstopRecordingIntent;
    private Bitmap icon;
    private boolean isSessionRecording = false;
    private PendingIntent pendingIntent;
    private NotificationManager mNotificationManager;

    public CommunicationService() {

    }

    @Override
    public void onCreate(){
        super.onCreate();
        mManager = Manager.getSharedInstance();

        Intent startRecordingIntent = new Intent(this, CommunicationService.class);
        startRecordingIntent.setAction(Const.START_RECORDING);
        pstartRecordingIntent = PendingIntent.getService(this, 0, startRecordingIntent, 0);

        Intent pauseRecordingIntent = new Intent(this, CommunicationService.class);
        pauseRecordingIntent.setAction(Const.PAUSE_RECORDING);
        ppauseRecordingIntent = PendingIntent.getService(this, 0, pauseRecordingIntent, 0);

        Intent stopRecordingIntent = new Intent(this, CommunicationService.class);
        stopRecordingIntent.setAction(Const.STOP_RECORDING);
        pstopRecordingIntent = PendingIntent.getService(this, 0, stopRecordingIntent, 0);

        icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int starId){
        if(intent.getAction().equals(Const.START_SERVICE)){
            isSessionRecording = true;

            Intent notificationIntent = new Intent(this, DataActivity.class);
            notificationIntent.setAction(Const.NEW_RECORDING);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        } else if (intent.getAction().equals(Const.START_RECORDING)) {
            startRecording();
        } else if (intent.getAction().equals(Const.PAUSE_RECORDING)) {
            pauseRecording();
        } else if (intent.getAction().equals(Const.STOP_RECORDING)) {
            stopRecording();
        }

        return START_STICKY;
    }

    private void startRecording() {

    }

    private void pauseRecording() {

    }

    private void stopRecording() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
