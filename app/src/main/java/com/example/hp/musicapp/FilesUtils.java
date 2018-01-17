package com.example.hp.musicapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by SANTOSH on 07-Jan-18.
 */

public class FilesUtils extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    Activity context;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public FilesUtils(Activity context){
    this.context=context;
    }

    public Boolean CreateDir() {
        Boolean permGranted = isStoragePermissionGranted();

        if (permGranted) {
            Boolean isPresent = externalMemoryAvailable(context);
            if (isPresent) {
                //if card present
                //Toast.makeText(context, "card present", Toast.LENGTH_SHORT).show();

                File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "BhaktiMusic");
                if (mediaStorageDir.exists()) {
                    Log.d("App", "Directory presented");
                    return false;

                } else {
                    boolean val=mediaStorageDir.mkdir();
                    Toast.makeText(context, "value= "+val, Toast.LENGTH_SHORT).show();
                    Log.d("App", "created folder");
                    return true;
                }

            } else {
                //if card not present
                File mediaStorageDir = new File(Environment.getDataDirectory().getAbsolutePath().toString() + "/BhaktiMusic");
                if (mediaStorageDir.exists()) {
                    Log.d("App", "Directory already presented");
                    return false;
                }else {
                    Log.d("App", "created directory");
                    mediaStorageDir.mkdir();
                    return true;
                }
            }

        } else {
            //if perm not granted
            Toast.makeText(context, "permission not given", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code

            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
        return true;
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(context, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public boolean externalMemoryAvailable(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null) {
            Log.v(TAG,"Sd Card Present");
            return true;
        }
        else{
            Log.v(TAG,"Sd Card not Present");
            return false;
        }
    }

    public Boolean checkFolder(){
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "BhaktiMusic");

        if (folder.exists()) {
            Log.e("Found Dir", "Found Dir  " );
            return true;

        } else {
            Log.e("Not Found Dir", "Not Found Dir  ");
            return false;
        }
    }
}
