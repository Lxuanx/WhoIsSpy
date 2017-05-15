package com.gdin.lxx.whoisspy.utils;

import android.util.Log;

public class Logger {

    /**
     * 控制是否输入 Log
     */
    private static boolean isShowLog = false;

    public static void i(Object objTag, String msg) {
        if (!isShowLog) {
            return;
        }

        String tag;
        // 如果objTag它是String，则直接使用，如果是其它对象，则获取类名
        if (objTag instanceof String) {
            tag = (String) objTag;
        } else if (objTag instanceof Class) {
            tag = ((Class) objTag).getSimpleName();        // 获取类名
        } else {
            tag = objTag.getClass().getSimpleName();    // 获取类名
        }
        Log.i(tag, msg);
    }

}
