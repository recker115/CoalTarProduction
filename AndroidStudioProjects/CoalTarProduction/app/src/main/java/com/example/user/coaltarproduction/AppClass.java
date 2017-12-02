package com.example.user.coaltarproduction;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by User on 9/5/2017.
 */

public class AppClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        printHashkey();
    }
    public void printHashkey()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.user.coaltarproduction",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


    }
    }

