package com.gdin.lxx.whoisspy.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    /**
     * 初始化匹配文本数据到data集合中
     */
    public static void loadFile(Context context, List<String> data) {
        if (!fileExist()) {
            writeDefaultFile(context);
        }
        readFile(context, data);
    }

    /**
     * 查询匹配词语文件是否存在
     *
     * @return true为存在
     */
    private static boolean fileExist() {
        return new File(Environment.getDataDirectory(), "/data/com.gdin.lxx.whoisspy/word.txt").exists();
    }

    /**
     * 写入默认的匹配词语文本文件
     *
     * @param context 上下文
     */
    public static void writeDefaultFile(Context context) {
        File localFile = new File(Environment.getDataDirectory(), "/data/com.gdin.lxx.whoisspy/word.txt");
        try {
            FileWriter localFileWriter = new FileWriter(localFile, false);
            localFileWriter.write("丰田-本田\r\n牛-羊\r\n眼睫毛-眉毛\r\n臭豆腐-榴莲\r\n发箍-发卡\r\n成吉思汗-努尔哈赤\r\n鱼缸-浴缸\r\n王菲-那英\r\n元芳-展昭\r\n麻雀-乌鸦\r\n胖子-肥肉\r\n眉毛-胡须\r\n何炅-维嘉\r\n状元-冠军\r\n饺子-包子\r\n端午节-中秋节\r\n摩托车-电动车\r\n高跟鞋-增高鞋\r\n汉堡包-肉夹馍\r\n小矮人-葫芦娃\r\n蜘蛛侠-蜘蛛精\r\n玫瑰-月季\r\n董永-许仙\r\n若曦-晴川\r\n谢娜-李湘\r\n孟非-乐嘉\r\n牛奶-豆浆\r\n保安-保镖\r\n白菜-生菜\r\n辣椒-芥末\r\n金庸-古龙\r\n赵敏-黄蓉\r\n海豚-海狮\r\n水盆-水桶\r\n唇膏-口红\r\n森马-以纯\r\n烤肉-涮肉\r\n气泡-水泡\r\n纸巾-手帕\r\n杭州-苏州\r\n香港-台湾\r\n首尔-东京\r\n橙子-橘子\r\n葡萄-提子\r\n太监-人妖\r\n蝴蝶-蜜蜂\r\n小品-话剧\r\n裸婚-闪婚\r\n新年-跨年\r\n吉他-琵琶\r\n公交-地铁\r\n剩女-御姐\r\n童话-神话\r\n作家-编剧\r\n警察-捕快\r\n结婚-订婚\r\n奖牌-金牌\r\n孟飞-乐嘉\r\n那英-韩红\r\n面包-蛋糕\r\n作文-论文\r\n油条-麻花\r\n壁纸-贴画\r\n枕头-抱枕\r\n手机-座机\r\n同学-同桌\r\n婚纱-礼服\r\n老佛爷-老天爷\r\n魔术师-魔法师\r\n鸭舌帽-遮阳帽\r\n双胞胎-龙凤胎\r\n情人节-光棍节\r\n丑小鸭-灰姑娘\r\n富二代-高富帅\r\n生活费-零花钱\r\n麦克风-扩音器\r\n郭德纲-周立波\r\n图书馆-图书店\r\n男朋友-前男友\r\n洗衣粉-皂角粉\r\n牛肉干-猪肉脯\r\n泡泡糖-棒棒糖\r\n土豆粉-酸辣粉\r\n蜘蛛侠-蝙蝠侠\r\n口香糖-木糖醇\r\n酸菜鱼-水煮鱼\r\n小笼包-灌汤包\r\n薰衣草-满天星\r\n张韶涵-王心凌\r\n刘诗诗-刘亦菲\r\n甄嬛传-红楼梦\r\n甄子丹-李连杰\r\n包青天-狄仁杰\r\n大白兔-金丝猴\r\n果粒橙-鲜橙多\r\n沐浴露-沐浴盐\r\n洗发露-护发素\r\n自行车-电动车\r\n班主任-辅导员\r\n过山车-碰碰车\r\n铁观音-碧螺春\r\n十面埋伏-四面楚歌\r\n成吉思汗-努尔哈赤\r\n福尔摩斯-工藤新一\r\n神雕侠侣-天龙八部\r\n天天向上-非诚勿扰\r\n勇往直前-全力以赴\r\n麻婆豆腐-皮蛋豆腐\r\n语无伦次-词不达意\r\n鼠目寸光-井底之蛙\r\n近视眼镜-隐形眼镜\r\n美人心计-倾世皇妃\r\n夏家三千金-爱情睡醒了\r\n降龙十八掌-九阴白骨爪\r\n红烧牛肉面-香辣牛肉面\r\n江南style-最炫民族风\r\n梁山伯与祝英台-罗密欧与朱丽叶\r\n");
            localFileWriter.close();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Utils.showToast(context, localIOException.getMessage());
        }
    }

    /**
     * 通过传入的data集合来写入的匹配词语文本文件
     *
     * @param context 上下文
     * @param data    写入文件的data集合
     */
    public static void writeFile(Context context, List<String> data) {
        StringBuilder builder = new StringBuilder("");
        File localFile = new File(Environment.getDataDirectory(), "/data/com.gdin.lxx.whoisspy/word.txt");
        try {
            FileWriter localFileWriter = new FileWriter(localFile, false);
            int i = 0;
            while (i < data.size()) {
                builder.append(data.get(i)).append("\r\n");
                i++;
            }
            localFileWriter.write(builder.toString());
            localFileWriter.close();
            Utils.showToast(context, "文件已保存在:" + localFile.getPath());
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Utils.showToast(context, localIOException.getMessage());
        }
    }

    /**
     * 读取匹配文本文件, 并添加到data集合中
     *
     * @param context 上下文
     * @param data    需要添加进去的data集合
     */
    public static void readFile(Context context, List<String> data) {
        data.clear();
        File localFile = new File(Environment.getDataDirectory(), "/data/com.gdin.lxx.whoisspy/word.txt");
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
            while (true) {
                String str = localBufferedReader.readLine();
                if (str == null) {
                    localBufferedReader.close();
                    return;
                }
                data.add(str);
            }
        } catch (FileNotFoundException localFileNotFoundException) {
            localFileNotFoundException.printStackTrace();
            Utils.showToast(context, "文件没有找到");
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Utils.showToast(context, localIOException.getMessage());
        }
    }


}
