package com.gdin.lxx.whoisspy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gdin.lxx.whoisspy.R;
import com.gdin.lxx.whoisspy.utils.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * 猜卧底的页面
 */
public class BattleActivity extends Activity {

    private int spyWinCount;
    private int spyTotalCount;
    private boolean[] booleanSpy;
    private String[] roleArray;
    private int playerTotalCount;
    private int spyLiveCount;
    private int playerLiveCount;

    //存放item列表的话
    private List<String> playlist = new ArrayList<>();
    private TextView tv_top;
    private TextView tv_middle;
    private TextView tv_bottom;
    private ListView lv_battle;
    private ArrayAdapter localArrayAdapter;
    private boolean isFinished;
    private boolean isShowHowDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initGameData();

        initView();
        loadData();
    }

    private void loadData() {
        playlist.clear();
        for (int i = 0; i < playerLiveCount; i++) {
            playlist.add(String.valueOf(i + 1));
        }

        showBottomText();

        localArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, playlist);
        lv_battle.setAdapter(localArrayAdapter);
        lv_battle.setOnItemClickListener(itemClickListener);
    }

    private void initListener() {
        findViewById(R.id.btn_go_home).setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                enterMainActivity();
            }
        });
    }

    private void initView() {
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_middle = (TextView) findViewById(R.id.tv_middle);
        tv_bottom = (TextView) findViewById(R.id.tv_bottom);
        lv_battle = ((ListView) findViewById(R.id.lv_battle));
        initListener();
    }

    private void initGameData() {
        Intent intent = getIntent();
        booleanSpy = intent.getBooleanArrayExtra(Keys.GAME_SPY_ARRAY);
        roleArray = intent.getStringArrayExtra(Keys.GAME_ROLE_ARRAY);
        spyWinCount = intent.getIntExtra(Keys.GAME_WIN_NUM, -1);
        spyTotalCount = intent.getIntExtra(Keys.GAME_SPY_NUM, -1);
        isShowHowDie = intent.getBooleanExtra(Keys.SHOW_DIE_MODE, false);

        spyLiveCount = spyTotalCount;
        playerTotalCount = roleArray.length;
        playerLiveCount = playerTotalCount;

    }

    /**判断是否是卧底
     * @param which 选择的是哪个
     */
    private void judgeSpy(int which) {
        //如果点击的是卧底
        if (booleanSpy[which]) {
            spyLiveCount--;
            //找到卧底但卧底未全部找出来, 走if语句的内容
            if (spyLiveCount != 0) {
                continueGame(true, which);
                return;
            }
            finishDisplay(false);
        }else {
            //如果玩家人数还是大于卧底胜利人数则走下面流程
            if (playerLiveCount > 1 + spyWinCount) {
                continueGame(false, which);
                return;
            }
            finishDisplay(true);
        }
    }

    /**
     * 继续游戏展示
     */
    private void continueGame(boolean isSpy, int which) {
        if(isShowHowDie) {
            if (isSpy) {
                playlist.set(which, 1 + which + ":卧底");
                tv_middle.setText(1 + which + "号是卧底，" + "卧底还有" + spyLiveCount + "人。");
            } else {
                playlist.set(which, 1 + which + ":冤死");
                tv_middle.setText(1 + which + "号被冤死,游戏继续");
            }
        }else {
                playlist.set(which, 1 + which + ":已死");
                tv_middle.setText("游戏继续");
        }
        tv_middle.setTextColor(Color.RED);
        localArrayAdapter.notifyDataSetChanged();

        playerLiveCount--;
        showBottomText();
    }

    /**
     * 游戏结束展示
     */
    private void finishDisplay(boolean isSpyWin) {
        isFinished = true;
        if (isSpyWin) {
            tv_top.setText("卧底胜利！！");
            tv_middle.setText("卧底胜利！！");
        } else {
            tv_top.setText("胜利！！！已找出所有卧底。");
            tv_middle.setText("胜利！！！已找出所有卧底。");
        }
        tv_middle.setTextColor(Color.BLUE);
        for (int i = 0; i < playlist.size(); i++) {
            playlist.set(i, i + 1 + ":" + roleArray[i]);
        }
        localArrayAdapter.notifyDataSetChanged();

        playerLiveCount--;
        showBottomText();
    }

    /**
     * 展示底部TextView
     */
    private void showBottomText() {
        if (isShowHowDie) {
            tv_bottom.setText("游戏人数:共" + playerTotalCount + "人   剩余:" + playerLiveCount + "人\n" + "卧底人数:共" + spyTotalCount + "人   剩余:" + spyLiveCount + "人\n" + "卧底胜利条件:游戏人数仅剩" + spyWinCount + "人");
        } else {
            tv_bottom.setText("游戏人数:共" + playerTotalCount + "人   剩余:" + playerLiveCount + "人\n" + "卧底胜利条件:游戏人数仅剩" + spyWinCount + "人");
        }
    }

    /**
     * 进入主Activity的界面
     */
    private void enterMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, final int which, long id) {
            if (((playlist.get(which)).indexOf(":") == -1) && !isFinished)
                new AlertDialog.Builder(BattleActivity.this)
                        .setTitle("确认")
                        .setMessage("确认选择" + (which + 1) + "号是卧底？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                judgeSpy(which);
                            }
                        })
                        .show();
        }
    };

}
