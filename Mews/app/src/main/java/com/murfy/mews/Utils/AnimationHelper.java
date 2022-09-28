package com.murfy.mews.Utils;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class AnimationHelper {

    private static AnimationHelper animationHelper;
    public AnimationHelper(){
    }

    public static AnimationHelper getInstance(){
        if(animationHelper == null) {
            animationHelper = new AnimationHelper();
        }
        return animationHelper;
    }

    public AnimationHelper slideFromLeftToRight(View v, int duration){
        float x = v.getX();
        v.setX(-1000);
        v.animate().x(x).setDuration(duration);

        return getInstance();
    }

    public AnimationHelper fadeIn(View v, int duration){
        v.setAlpha(0);
        v.animate().alpha(1).setDuration(duration);

        return  getInstance();
    }

    public AnimationHelper fadeOut(View v, int duration){
        v.setAlpha(1);
        v.animate().alpha(0).setDuration(duration);

        return  getInstance();
    }
}
