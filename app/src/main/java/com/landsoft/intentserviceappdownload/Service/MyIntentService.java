package com.landsoft.intentserviceappdownload.Service;

import android.app.Dialog;
import android.app.IntentService;

import android.content.Intent;

import android.os.Bundle;
import android.os.Environment;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.landsoft.intentserviceappdownload.Helper.Constants;
import com.landsoft.intentserviceappdownload.MainActivity;
import com.landsoft.intentserviceappdownload.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    // TODO: Rename parameters
    private Dialog dialog;
    private Boolean overWrite;
    private int result;


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
//        dung de khoi tao
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(Constants.BUNDLE_MAINACTIVITY_MYINTENTSERVICE);
            String url = bundle.getString(Constants.URL_DOWNLOAD);
            String fileName = getContentName(url);
//          tao đương dẫn lưu file
            File outPut = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName);
            overWrite = false;
            if ( outPut.exists()){
//                ShowDialog();
//                if (overWrite == true) {
//                    outPut.delete();
//                }
//                else
//                {
//                    outPut = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),fileName+"1");
//                }
//                minh co tinh de cai dialog vao de lua chon nhung ko the , chi co the dung  synchronized
//                synchronized (this)
//                {
//                    startActivity(new Intent(this,ActivityXYZ.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                }
                outPut.delete();
            }

            try {
                URL urlDownload = new URL(url);
                InputStream inputStream = urlDownload.openConnection().getInputStream();


                FileOutputStream outputStream = new FileOutputStream(outPut);
                copyStream(inputStream, outputStream);

                result = Constants.CODE_RESULT;
                inputStream.close();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActionReceiver(outPut.getAbsolutePath(),result);
        }

    }
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public void startActionReceiver(String absolutePath, int result) {
        Intent intent = new Intent(Constants.SET_ACTION);
//        tap lam bundle cho quen thoi
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.RESULT_RECEIVER,result);
        intent.putExtra(Constants.BUNDLE_MYINTENTSERVICE_RECEIVER,bundle);
        sendBroadcast(intent);
    }

    //    lay ten file can luu
    public static String getContentName(String url) {
        String result = null;
        if (url != null) {
            result = url;
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
