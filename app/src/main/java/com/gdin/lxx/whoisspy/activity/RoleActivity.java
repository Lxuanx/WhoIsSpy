package com.gdin.lxx.whoisspy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.gdin.lxx.whoisspy.R;
import com.gdin.lxx.whoisspy.utils.Keys;
import com.gdin.lxx.whoisspy.utils.Utils;

/**
 * 每个人查看自己角色的界面
 */
public class RoleActivity extends Activity {

    private int currentNum = 0;
    private ImageView iv_splash;
    private Button btn_check_role;
    private String[] roleArray;
    private int max;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        roleArray = getIntent().getStringArrayExtra(Keys.GAME_ROLE_ARRAY);
        max = roleArray.length;

        iv_splash = (ImageView) findViewById(R.id.iv_splash);
        btn_check_role = ((Button) findViewById(R.id.btn_check_role));

        updateImageResource();
    }

    private void updateImageResource() {
        switch (currentNum) {
            case 0:
                iv_splash.setImageResource(R.drawable.num1);
                break;
            case 1:
                iv_splash.setImageResource(R.drawable.num2);
                break;
            case 2:
                iv_splash.setImageResource(R.drawable.num3);
                break;
            case 3:
                iv_splash.setImageResource(R.drawable.num4);
                break;
            case 4:
                iv_splash.setImageResource(R.drawable.num5);
                break;
            case 5:
                iv_splash.setImageResource(R.drawable.num6);
                break;
            case 6:
                iv_splash.setImageResource(R.drawable.num7);
                break;
            case 7:
                iv_splash.setImageResource(R.drawable.num8);
                break;
            case 8:
                iv_splash.setImageResource(R.drawable.num9);
                break;
            case 9:
                iv_splash.setImageResource(R.drawable.num10);
                break;
            case 10:
                iv_splash.setImageResource(R.drawable.num11);
                break;
            case 11:
                iv_splash.setImageResource(R.drawable.num12);
                break;
            default:
                break;
        }
        btn_check_role.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                openWordDialog();
            }
        });
    }

    /**
     * 展示是什么角色
     */
    private void openWordDialog() {
        new AlertDialog.Builder(this).setTitle("你是" + (currentNum + 1) + "号")
                .setMessage(roleArray[currentNum])
                .setPositiveButton("记住了", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        currentNum++;
                        if (currentNum < max) {
                            updateImageResource();
                        } else{
                            //展示结束就finished当前activity
                            finish();
                        }
                    }
                }).show();
    }

    /**
     * 禁止退出。。。
     */
    @Override
    public void onBackPressed() {
        Utils.showToast(this, "全部查看后才可以退出");
    }
}
