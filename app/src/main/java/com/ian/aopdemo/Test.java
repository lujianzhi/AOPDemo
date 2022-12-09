package com.ian.aopdemo;

import android.util.Log;

/**
 * USER：lujianzhi
 * DATE：2022/12/9
 */
public class Test {
    public void time() {
        long enterTime = System.currentTimeMillis();
        Log.d("ian", "method enter time :" + enterTime);

        long exitTime = System.currentTimeMillis();
        Log.d("ian", "method duration time :" + (exitTime - enterTime));
    }
}
