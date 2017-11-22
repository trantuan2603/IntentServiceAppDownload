package com.landsoft.intentserviceappdownload.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.landsoft.intentserviceappdownload.Helper.Constants;
import com.landsoft.intentserviceappdownload.MainActivity;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        if (intent != null ) {
            Bundle bundle = intent.getBundleExtra(Constants.BUNDLE_MYINTENTSERVICE_RECEIVER);
            int result = bundle.getInt(Constants.RESULT_RECEIVER);
            if (result == Constants.CODE_RESULT){
                Toast.makeText(context," download thanh cong", Toast.LENGTH_SHORT).show();
                MainActivity.tvStartus.setText("download thanh cong");
            }
        }
    }
}
