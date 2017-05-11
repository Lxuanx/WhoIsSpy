package com.gdin.lxx.whoisspy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gdin.lxx.whoisspy.R;
import com.gdin.lxx.whoisspy.utils.FileUtils;
import com.gdin.lxx.whoisspy.utils.Keys;
import com.gdin.lxx.whoisspy.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 进去的主界面
 */
public class MainActivity extends Activity {
    private static final int REQUEST_TO_ROLE_ACTIVITY = 0;
    private Button btn_go_help;
    private Button btn_go_word_manager;
    private boolean[] booleanSpy;
    private int choosePlayerNum = 4;
    private int chooseSpyNum = 1;
    private int chooseWinnerNum = 2;
    private List<String> data = new ArrayList<>();
    private Button btn_start_game;
    private String[] roleArray;
    private TextView tv_player_count;
    private TextView tv_spy_count;
    private TextView tv_winner_count;
    private LinearLayout ll_player_count;
    private LinearLayout ll_spy_count;
    private LinearLayout ll_winner_count;
    private CheckBox cb_show_die_mode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        FileUtils.loadFile(this, data);

        initView();
        initListener();
        initData();
    }

    private void initData() {
        tv_player_count.setText("游戏人数:      " + choosePlayerNum + "人");
        tv_spy_count.setText("卧底人数:      " + chooseSpyNum + "人");
        tv_winner_count.setText("胜利条件:      " + chooseWinnerNum + "人");
    }

    private void initListener() {
        ll_player_count.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                openChooseGamerDialog(0);
            }
        });
        ll_spy_count.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                openChooseGamerDialog(1);
            }
        });
        ll_winner_count.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                openChooseGamerDialog(2);
            }
        });
        btn_start_game.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                booleanSpy = getSpy();
                roleArray = getAllCurrentGamePlayerStr(booleanSpy);
                enterRoleActivity();
            }
        });
        btn_go_word_manager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                enterWordManagerActivity();
            }
        });
        btn_go_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                enterHelpActivity();
            }
        });
    }

    private void initView() {
        tv_player_count = ((TextView) findViewById(R.id.tv_player_count));
        tv_spy_count = ((TextView) findViewById(R.id.tv_spy_count));
        tv_winner_count = ((TextView) findViewById(R.id.tv_winner_count));
        btn_start_game = ((Button) findViewById(R.id.btn_start_game));
        btn_go_word_manager = ((Button) findViewById(R.id.btn_go_word_manager));
        btn_go_help = ((Button) findViewById(R.id.btn_go_help));
        cb_show_die_mode = (CheckBox) findViewById(R.id.cb_show_die_mode);

        ll_player_count = (LinearLayout) findViewById(R.id.ll_player_count);
        ll_spy_count = (LinearLayout) findViewById(R.id.ll_spy_count);
        ll_winner_count = (LinearLayout) findViewById(R.id.ll_winner_count);
    }

    /**
     * 获取相应的词组
     *
     * @param booleanArraySpy 卧底真假数组
     * @return 获得该数组下的词语数组
     */
    private String[] getAllCurrentGamePlayerStr(boolean[] booleanArraySpy) {
        int i = getRandom(data.size());
        String[] arrayOfString = new String[booleanArraySpy.length];
        String[][] arrayOfString1 = getWord();
        for (int k = 0; k < booleanArraySpy.length; k++) {
            //如果是卧底, 则用二号位的词语, 否则用一号位的词语
            if (booleanArraySpy[k]) {
                arrayOfString[k] = arrayOfString1[i][1];
            } else {
                arrayOfString[k] = arrayOfString1[i][0];
            }
        }
        return arrayOfString;
    }

    /**
     * 弹出一个退出的dialog
     */
    private void exitOptionsDialog() {
        new AlertDialog.Builder(this).setTitle("退出").setMessage("确定要退出吗")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                }).show();
    }

    /**
     * 获得随机数， 区间为[0,max)
     *
     * @param max 最大数
     * @return 返回随机数
     */
    private int getRandom(int max) {
        return new Random().nextInt(max);
    }

    /**
     * 获得一个玩家的布尔数组, 如果返回为true的话, 即为卧底
     *
     * @return ture 为卧底
     */
    private boolean[] getSpy() {
        int choosePlayerNum = this.choosePlayerNum;
        int chooseSpyNum = this.chooseSpyNum;

        int[] intArray1 = new int[choosePlayerNum];
        int[] intArray2 = new int[choosePlayerNum];
        boolean[] booleanArray = new boolean[choosePlayerNum];
        //把布尔数组都初始化为false,并初始化两个int数组
        for (int k = 0; k < choosePlayerNum; k++) {
            intArray1[k] = getRandom(100);
            intArray2[k] = intArray1[k];
            booleanArray[k] = false;
        }
        Utils.sort(intArray1);
        for (int m = 0; m < chooseSpyNum; m++) {
            for (int n = 0; n <= choosePlayerNum; n++) {
                //最小的前m个数为卧底
                if (intArray1[m] == intArray2[n]) {
                    booleanArray[n] = true;
                    break;
                }
            }
        }

        return booleanArray;
    }

    /**
     * 获得一个全部匹配词语字符串二维数组
     * data.size()行 2列
     *
     * @return 获得全部匹配词语数组
     */
    private String[][] getWord() {
        String[][] arrayOfString = (String[][]) Array.newInstance(String.class, data.size(), 2);
        for (int i = 0; i < data.size(); i++) {
            String[] arrayOfString1 = data.get(i).split("-");
            arrayOfString[i][0] = arrayOfString1[0];
            arrayOfString[i][1] = arrayOfString1[1];
        }
        return arrayOfString;
    }

    /**
     * 选择游戏、卧底、胜利人数
     *
     * @param num 标记是打开那一个dialog
     */
    private void openChooseGamerDialog(int num) {
        if (num == 0) {
            new AlertDialog.Builder(this)
                    .setSingleChoiceItems(new String[]{"4", "5", "6", "7", "8", "9", "10", "11", "12"}, 0,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramDialogInterface, int which) {
                                    choosePlayerNum = (which + 4);
                                    tv_player_count.setText("游戏人数:      " + choosePlayerNum + "人");
                                    paramDialogInterface.dismiss();
                                }
                            }).show();
        } else if (num == 1) {
            new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"1", "2", "3", "4"}, 0,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int which) {
                            chooseSpyNum = (which + 1);
                            tv_spy_count.setText("卧底人数:      " + chooseSpyNum + "人");
                            paramDialogInterface.dismiss();
                        }
                    }).show();
        } else {
            new AlertDialog.Builder(this).setSingleChoiceItems(new String[]{"2", "3", "4", "5"}, 0,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int which) {
                            chooseWinnerNum = (which + 2);
                            tv_winner_count.setText("胜利条件:      " + chooseWinnerNum + "人");
                            paramDialogInterface.dismiss();
                        }
                    }).show();
        }
    }

    /**
     * 进入词汇管理的界面
     */
    private void enterWordManagerActivity() {
        Intent localIntent = new Intent(MainActivity.this, WordManagerActivity.class);
        startActivity(localIntent);
        finish();
    }

    /**
     * 进入角色展示的界面
     */
    private void enterRoleActivity() {
        Intent intent = new Intent(MainActivity.this, RoleActivity.class);
        intent.putExtra(Keys.GAME_ROLE_ARRAY, roleArray);
        startActivityForResult(intent, REQUEST_TO_ROLE_ACTIVITY);
    }

    /**
     * 如果是从RoleActivity回来, 则进入Battle界面
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TO_ROLE_ACTIVITY) {
            enterBattleActivity();
        }
    }

    /**
     * 进入Battle的界面
     */
    private void enterBattleActivity() {
        boolean checked = cb_show_die_mode.isChecked();

        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra(Keys.GAME_ROLE_ARRAY, roleArray);
        intent.putExtra(Keys.GAME_SPY_ARRAY, booleanSpy);
        intent.putExtra(Keys.GAME_SPY_NUM, chooseSpyNum);
        intent.putExtra(Keys.GAME_WIN_NUM, chooseWinnerNum);
        intent.putExtra(Keys.SHOW_DIE_MODE, checked);
        startActivity(intent);
        finish();
    }

    /**
     * 进入帮助的界面
     */
    private void enterHelpActivity() {
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 设置按下返回键的操作
     */
    @Override
    public void onBackPressed() {
        exitOptionsDialog();
    }

}