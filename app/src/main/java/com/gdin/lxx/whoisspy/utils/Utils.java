package com.gdin.lxx.whoisspy.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Random;


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

    /**
     * 蓄水池算法获取产生卧底的数组
     */
    public static int[] getSpyInts(int choosePlayerNum, int chooseSpyNum) {
        int[] playerInts = new int[choosePlayerNum];
        for (int i = 0; i < choosePlayerNum; i++) {
            playerInts[i] = i;
        }

        int[] spyInts = new int[chooseSpyNum];
        for(int i = 0; i < chooseSpyNum; i++){
            spyInts[i] = i;
        }
        Random random = new Random();
        for(int i = chooseSpyNum; i < playerInts.length; i++ ){
            int num = random.nextInt(i + 1);
            if(num < chooseSpyNum){
                spyInts[num] = playerInts[i];
            }
        }
        return spyInts;
    }

}
