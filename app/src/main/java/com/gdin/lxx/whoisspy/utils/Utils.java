package com.gdin.lxx.whoisspy.utils;

import android.content.Context;
import android.widget.Toast;


public class Utils {

    /**
     * 默认一个短时间的Tosat
     *
     * @param context 上下文
     * @param msg     弹出的消息内容
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 冒泡排序
     *
     * @param paramArrayOfInt 排序后变成从小到大的数组
     */
    public static void sort(int[] paramArrayOfInt) {
        for (int i = 0; i < paramArrayOfInt.length - 1; i++) {
            boolean isChange = false;
            for (int j = paramArrayOfInt.length - 1; j > i; j++) {
                if (paramArrayOfInt[j - 1] > paramArrayOfInt[j]) {
                    isChange = true;
                    swap(paramArrayOfInt, j);
                }
            }
            if (!isChange) {
                break;
            }
        }

    }

    /**
     * 交换数据内 j 位置和 j-1 位置的元素
     */
    private static void swap(int[] paramArrayOfInt, int j) {
        int temp = paramArrayOfInt[j - 1];
        paramArrayOfInt[j - 1] = paramArrayOfInt[j];
        paramArrayOfInt[j] = temp;
    }
}
