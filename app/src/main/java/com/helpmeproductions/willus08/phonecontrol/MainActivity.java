package com.helpmeproductions.willus08.phonecontrol;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ComponentName adminName;
    private static final int REQUEST_CODE_ENABLE_ADMIN = 1;
    boolean camCurrentlyDisabled = false;
    boolean mAdminActive = false;
    DevicePolicyManager dpm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminName = new ComponentName(this,DeviceAdminSampleReciver.class);
        dpm = (DevicePolicyManager) getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        setContentView(R.layout.activity_main);
    }

    public void changeCamSetting(View view) {

        camCurrentlyDisabled = !camCurrentlyDisabled;
        dpm.setCameraDisabled(adminName,camCurrentlyDisabled);
        Toast.makeText(this, "Camera Dissabled:" + camCurrentlyDisabled, Toast.LENGTH_SHORT).show();

    }


    public void lockScreen(View view) {
        dpm.lockNow();
    }

    public void changePassword(View view) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
        startActivity(intent);

    }

    public void enableAdmin(View view) {
        mAdminActive = !mAdminActive;
        if (!mAdminActive) {
            // Launch the activity to have the user enable our admin.
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    this.getString(R.string.add_admin_extra_app_text));
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN);
        } else {
            dpm.removeActiveAdmin(adminName);

        }
        Toast.makeText(this, "Admin Enabled: " + mAdminActive, Toast.LENGTH_SHORT).show();
    }
}

