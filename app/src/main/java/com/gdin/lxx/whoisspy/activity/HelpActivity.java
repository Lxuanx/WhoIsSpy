package com.gdin.lxx.whoisspy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.gdin.lxx.whoisspy.R;


public class HelpActivity extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Button btn_go_home = ((Button) findViewById(R.id.btn_go_home));
        btn_go_home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent intent = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent paramKeyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            openQuitDialog();
            return true;
        }
        return false;
    }

    /**
     * 点击弹出一个退出Dialog的方法
     */
    private void openQuitDialog() {
        new AlertDialog.Builder(this).setMessage("确认退出吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                }).show();
    }
}
