package com.elephantgroup.one.util;

import android.support.v4.app.FragmentActivity;

import java.util.LinkedList;
import java.util.List;

public class ActivityManager {

    private ActivityManager() {

    }

    private static ActivityManager S_INST = new ActivityManager();
    private static List<FragmentActivity> activityStack = new LinkedList<>();


    public static ActivityManager getInatance() {
        if (S_INST == null) {
            synchronized (ActivityManager.class) {
                if (S_INST == null) {
                    S_INST = new ActivityManager();
                }
            }
        }
        return S_INST;
    }

    public void addActivity(FragmentActivity aty) {
        activityStack.add(aty);
    }

    public void removeOtherActivity(FragmentActivity aty) {
        if (activityStack != null && activityStack.size() > 0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    if (activityStack.get(i) != aty) {
                        activityStack.get(i).finish();
                    }
                }
            }
            activityStack.clear();
        }
    }

    public void removeActivity(FragmentActivity aty) {
        activityStack.remove(aty);
    }

    public void finishAllActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }

    }
}
