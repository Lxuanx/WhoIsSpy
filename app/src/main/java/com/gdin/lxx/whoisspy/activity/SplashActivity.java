package com.gdin.lxx.whoisspy.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gdin.lxx.whoisspy.R;


/**
 * 启动页面
 */
public class SplashActivity extends Activity {
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                enterMainActivity();
            }
        }, 2000);
    }

    private void enterMainActivity() {
        Intent localIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(localIntent);
        finish();
    }
}
