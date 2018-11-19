package com.elephantgroup.one.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.ChangeBounds;
import android.view.View;

public class OptionsUtil {

    public static final String OPTION_INFO = "optionInfo";

    public static void StartOptionsActivity(Context activity, Class<?> cls, View transitionView) {
        Intent intent = new Intent(activity, cls);
        // 这里指定了共享的视图元素
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) activity, transitionView, OPTION_INFO);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }


    public static void setSharedElementEnterTransition(Activity activity){
        if (activity == null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ChangeBounds changeBounds  = new ChangeBounds();
            changeBounds.setDuration(100);
            activity.getWindow().setSharedElementEnterTransition(changeBounds);
        }
    }

}
