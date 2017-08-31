package in.springpebbles.gestureapplauncher;


//material design colors lighter=#5A5A5C darker=#303030

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    Button buttonstart;
    Button buttonstop;

    public List<String> appNames = new ArrayList<String>();
    public List<Drawable> icons = new ArrayList<Drawable>();
    public List<String> packageName = new ArrayList<String>();
    public ListAdapter adapter;

    String[] packageNameArray;
    Drawable[] iconsArray;
    String[] appNamesArray;

    public String dataStore = "dataStore";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        sharedPreferences = getSharedPreferences("dataStore", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();





        Log.d("FlashLightActivity", "onCreate()");
        setContentView(R.layout.activity_main);



        ImageButton imageButton = (ImageButton)findViewById(R.id.imagebutton);



        //adapter = new ArrayAdapter(this,R.layout.app_list,appNames);



        try {

            final PackageManager pm = getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo packageinfo : packages) {

                if((packageinfo.flags & ApplicationInfo.FLAG_SYSTEM)==0 || (packageinfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)!=0) {

                    packageName.add(packageinfo.packageName);
                    appNames.add((String)pm.getApplicationLabel(packageinfo));
                    icons.add(pm.getApplicationIcon(packageinfo));
                }
            }
        } catch (Exception e){
            Log.d("Tag.:",e.getMessage());
            showtoast(e.getMessage());

        }



        String initialIcon = sharedPreferences.getString("appName","in.springpebbles.gestureapplauncher");

        try {
            Drawable iconImage = getPackageManager().getApplicationIcon(initialIcon);
            imageButton.setImageDrawable(iconImage);
        } catch (PackageManager.NameNotFoundException e){
            showtoast("Package Not Found : "+e.getMessage());
        }


        appNamesArray = new String[appNames.size()];
        appNamesArray = appNames.toArray(appNamesArray);

        iconsArray = new Drawable[icons.size()];
        iconsArray = icons.toArray(iconsArray);

        packageNameArray = new String[packageName.size()];
        packageNameArray = packageName.toArray(packageNameArray);


        adapter = new customAdapter(MainActivity.this,appNamesArray,iconsArray,packageNameArray);


    }




    public void  disableStartButton(){

        Button buttonstart = (Button) findViewById(R.id.button2);
        Button buttonstop = (Button) findViewById(R.id.button);
        buttonstart.setEnabled(false);

    }


    public void  disableStopButton(){

        Button buttonstart = (Button) findViewById(R.id.button2);
        Button buttonstop = (Button) findViewById(R.id.button);
        buttonstart.setEnabled(false);

    }





    public void showtoast(String S) {

        Toast.makeText(getApplicationContext(), S, Toast.LENGTH_LONG).show();
    }







    private void checkForPermission(String permission) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

                Intent permissionIntent = new Intent(getApplicationContext(),permissions.class);
                startActivityForResult(permissionIntent,2);
        }
    }







    public void startService(View view) {

            startService(new Intent(getBaseContext(), MyService.class));
            Button buttonstart = (Button) findViewById(R.id.button2);
            Button buttonstop = (Button) findViewById(R.id.button);
            buttonstart.setEnabled(false);
            buttonstop.setEnabled(true);


            editor.putString("service","on");
            editor.commit();


    }




    // Method to stop the service
    public void stopService(View view) {

            stopService(new Intent(getBaseContext(), MyService.class));
            Button buttonstart = (Button) findViewById(R.id.button2);
            Button buttonstop = (Button) findViewById(R.id.button);
            buttonstart.setEnabled(true);
            buttonstop.setEnabled(false);

            editor.putString("service","off");
            editor.commit();

    }



    public void selectApplication(View view){


        try {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            //dialog.setTitle("The Dialogue");
            //dialog.setMessage("This is a dialog test");
            dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int i) {


                    editor.putString("appName",packageNameArray[i]);
                    editor.putString("AppLabel",appNamesArray[i]);
                    editor.commit();

                    ImageButton imageButton = (ImageButton)findViewById(R.id.imagebutton);
                    imageButton.setImageDrawable(iconsArray[i]);
                }
            });
            dialog.show();
        } catch (Exception e){
            Log.d("Error.:",e.getMessage());
        }


        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("whatever title");
        builder.setAdapter(appNames, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

            }
        });
        */


    }



}