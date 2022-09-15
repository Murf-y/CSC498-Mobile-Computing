package com.murfy.convertpad;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Callable;

public class Utils {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isUser(String email, String password){
        return email.equals("user.leb") && password.equals("123456");
    }

    public static void showAndHideView(View v){
        // we can make duration as a overloaded func param but not needed
        v.setAlpha(1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.animate().alpha(0).setDuration(1000);
            }
        }, 3000);
    }

    public static void hideAndShowView(View v,Callable<Void> func){
        // Hide view, and show it after a duration, and call a callback while showing it
        // we can make duration as a overloaded func param but not needed
        v.animate().alpha(0).setDuration(200);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.animate().alpha(1).setDuration(200);
                try {
                    func.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 200);
    }


}
