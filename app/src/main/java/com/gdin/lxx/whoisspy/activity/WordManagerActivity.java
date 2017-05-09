package com.gdin.lxx.whoisspy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.gdin.lxx.whoisspy.R;
import com.gdin.lxx.whoisspy.utils.FileUtils;
import com.gdin.lxx.whoisspy.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class WordManagerActivity extends Activity {
    private Button btn_save;
    private Button btn_add;
    private Button btn_default;
    private Button btn_go_home;
    private ListView lv_word_manager;
    private List<String> data = new ArrayList<>();
    private ArrayAdapter<String> strAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_manager);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        FileUtils.loadFile(this, data);

        initView();
        initListener();
        initData();
    }

    private void initListener() {
        btn_add.setOnClickListener(addListener);
        btn_save.setOnClickListener(saveListener);
        btn_default.setOnClickListener(defaultListener);
        btn_go_home.setOnClickListener(quitListener);
    }

    private void initData() {
        btn_add.setText("添加词语");
        btn_save.setText("保存");
        btn_default.setText("恢复默认");
        btn_go_home.setText("返回游戏页面");

        strAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        lv_word_manager.setAdapter(strAdapter);
        lv_word_manager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int position, long id) {
                String str = data.get(position);
                openQueryDialog(str, position);
            }
        });
    }

    private void initView() {
        lv_word_manager = ((ListView) findViewById(R.id.lv_word_manager));
        btn_add = ((Button) findViewById(R.id.btn_add));
        btn_save = ((Button) findViewById(R.id.btn_save));
        btn_default = ((Button) findViewById(R.id.btn_default));
        btn_go_home = ((Button) findViewById(R.id.btn_go_home));
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

    /**
     * 点击弹出一个查看匹配词语Dialog的方法
     */
    public void openQueryDialog(String paramString, final int position) {
        try {
            View localView = LayoutInflater.from(this).inflate(R.layout.dialog_show_word, null);
            final EditText editView1 = ((EditText) localView.findViewById(R.id.et_first_word));
            final EditText editView2 = ((EditText) localView.findViewById(R.id.et_second_word));

            String[] arrayOfString = paramString.split("-");
            editView1.setText(arrayOfString[0]);
            editView2.setText(arrayOfString[1]);

            new AlertDialog.Builder(this).setView(localView).setTitle("查看")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            data.set(position,
                                    editView1.getText() + "-" + editView2.getText());
                            strAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        }
                    })
                    .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            if (data.size() != 1) {
                                data.remove(position);
                                strAdapter.notifyDataSetChanged();
                                return;
                            }
                            Utils.showToast(WordManagerActivity.this, "最少要有一组词");
                        }
                    }).show();
        } catch (Exception localException) {
            localException.printStackTrace();
            Utils.showToast(this, localException.getMessage());
        }
    }

    /**
     * 点击弹出一个添加匹配词语Dialog的方法
     */
    public void openAddDialog() {
        View localView = LayoutInflater.from(this).inflate(R.layout.dialog_show_word, null);
        final EditText editView1 = ((EditText) localView.findViewById(R.id.et_first_word));
        final EditText editView2 = ((EditText) localView.findViewById(R.id.et_second_word));
        new AlertDialog.Builder(this).setView(localView).setTitle("添加")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        data.add(editView1.getText() + "-" + editView2.getText());
                        strAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                }).show();
    }


    /**
     * 点击弹出一个恢复匹配词语Dialog的方法
     */
    private void openDefaultDialog() {
        new AlertDialog.Builder(WordManagerActivity.this).setTitle("确定要恢复默认词组吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                FileUtils.writeDefaultFile(WordManagerActivity.this);
                Utils.showToast(WordManagerActivity.this, "已恢复默认");
                FileUtils.readFile(WordManagerActivity.this, data);
                strAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        }).show();
    }

    View.OnClickListener defaultListener = new View.OnClickListener() {
        public void onClick(View paramView) {
            openDefaultDialog();
        }
    };
    View.OnClickListener addListener = new View.OnClickListener() {
        public void onClick(View paramView) {
            openAddDialog();
        }
    };
    View.OnClickListener saveListener = new View.OnClickListener() {
        public void onClick(View paramView) {
            FileUtils.writeFile(WordManagerActivity.this, data);
        }
    };
    View.OnClickListener quitListener = new View.OnClickListener() {
        public void onClick(View paramView) {
            Intent localIntent = new Intent(WordManagerActivity.this, MainActivity.class);
            startActivity(localIntent);
            finish();
        }
    };
}