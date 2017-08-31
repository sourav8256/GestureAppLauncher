package in.springpebbles.gestureapplauncher;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Handler;

import java.sql.Time;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class MyService extends Service implements SensorEventListener {




    private CameraManager mCameraManager;
    private String mCameraId;
    private ImageButton mTorchOnOffButton;
    private Boolean isTorchOn;
    private MediaPlayer mp;


    private SensorManager sensorManager;
    private long lastUpdate;



    //camera controllers
    public String safety = null;
    public long offtime;
    public long ontime;



    public String appToLaunch;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.

        shownotification("Ready To Launch Selected App");

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);


        //shownotification("Shake To Turn ON/OFF");


        Toast.makeText(this, "Quick Launch Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);

        Toast.makeText(this, "Quick Launch Stopped", Toast.LENGTH_SHORT).show();
    }


    public void shownotification(String flashStatus) {
        Intent notificationIntent = new Intent(this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getText(R.string.notification_title))
                .setContentText(flashStatus)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ticker_text))
                .build();

        startForeground(1, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis() - 2000;

    }


    public void showtoast(String S) {

        Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //showtoast("inside sensor check");

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //showtoast("inside sensor check");

            float[] values = event.values;
            // Movement
            float x = values[0];
            float y = values[1];
            float z = values[2];

            float accelationSquareRoot = (x * x + y * y + z * z)
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualTime = System.currentTimeMillis();
            if (accelationSquareRoot >= 2) //
            {


                if ((actualTime - lastUpdate) >= 1400) {


                    SharedPreferences sharedPreferences = getSharedPreferences("dataStore", Context.MODE_PRIVATE);
                    appToLaunch = sharedPreferences.getString("appName","in.springpebbles.gestureapplauncher");
                    String appToLaunchLabel = sharedPreferences.getString("AppLabel","Gesture Launcher");


                    lastUpdate = actualTime;

                    AlertDialog alertDialogStores;



                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage(appToLaunch);
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (launchIntent != null){
                        showtoast("Opening : "+ appToLaunchLabel);
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }


                    //showtoast("inside 1400 check");


                }

            }
        }
    }


        @Override
        public void onTaskRemoved (Intent rootIntent){
            super.onTaskRemoved(rootIntent);

        }

        @Override
        public void onAccuracyChanged (Sensor sensor,int accuracy){

        }


    }
