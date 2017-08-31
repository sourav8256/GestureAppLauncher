package in.springpebbles.gestureapplauncher;

/**
 * Created by sourav9674 on 5/23/2017.
 */

// How to get the current GPS location programmatically in Android



        import android.Manifest;
        import android.app.Activity;
        import android.content.Context;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.widget.Button;
        import android.view.View;

        import android.content.Intent;
        import android.widget.Toast;

public class permissions extends Activity {



    private static final int REQUEST_CODE=200;
    //final Context context = MainActivity.this;


    boolean result;






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        // Button button = (Button)findViewById(R.id.mapview);
        // button.setVisibility(View.GONE);




        askForPermission(Manifest.permission.CAMERA,REQUEST_CODE);


        //Toast.makeText(getBaseContext(), "result is "+result, Toast.LENGTH_LONG).show();


    }





    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(permissions.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(permissions.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(permissions.this, new String[]{permission}, requestCode);


            } else {

                ActivityCompat.requestPermissions(permissions.this, new String[]{permission}, requestCode);

            }


        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }





    }




    public void onRequestPermissionsResult(int request_Code, String permissions[], int[] grantResults) {

        switch (request_Code){

            case REQUEST_CODE:
                if(grantResults.length>0)
                {
                  finish();
                }
                break;
        }
    }






}