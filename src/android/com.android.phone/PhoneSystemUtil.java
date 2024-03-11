package com.android.phone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PhoneSystemUtil {

    private static final String TAG = PhoneSystemUtil.class.getSimpleName();

    // 华为
    public final static String HUAWEI = "huawei";

    // 荣耀
    public final static String HONOR = "honor";

    // 华为 NOVA
    public final static String NOVA = "nova";

    // 小米
    public final static String XIAOMI = "xiaomi";

    // vivo
    public final static String VIVO = "vivo";

    // 魅族
    public final static String MEIZU = "meizu";

    // 索尼
    public final static String SONY = "sony";

    // 三星
    public final static String SAMSUNG = "samsung";

    // OPPO
    public final static String OPPO = "oppo";

    // 乐视
    public final static String Letv = "letv";

    // 一加
    public final static String OnePlus = "oneplus";

    // 锤子
    public final static String SMARTISAN = "smartisan";

    // 联想
    public final static String LENOVO = "lenovo";

    // LG
    public final static String LG = "lg";

    // HTC
    public final static String HTC = "htc";

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND.toLowerCase();
    }

    /**
     * 检查小米手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkXiaomiRecord(Context context) {
        try {
            int key = Settings.System.getInt(context.getContentResolver(), "button_auto_record_call");
            Log.d(TAG, "Xiaomi key:" + key);
            //0是未开启,1是开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查OPPO手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkOppoRecord(Context context) {
        try {
            int key = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 ? Settings.Global.getInt(context.getContentResolver(), "oppo_all_call_audio_record") : 0;
            Log.d(TAG, "Oppo key:" + key);
            //0代表OPPO自动录音未开启,1代表OPPO自动录音已开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查VIVO自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkVivoRecord(Context context) {
        try {
            int key = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 ? Settings.Global.getInt(context.getContentResolver(), "call_record_state_global") : 0;
            Log.d(TAG, "Vivo key:" + key);
            //0代表VIVO自动录音未开启,1代表VIVO所有通话自动录音已开启,2代表指定号码自动录音
            return key == 1;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查华为手机自动录音功能是否开启，true已开启  false未开启
     *
     * @return
     */
    public static boolean checkHuaweiRecord(Context context) {
        try {
            int key = Settings.Secure.getInt(context.getContentResolver(), "enable_record_auto_key");
            Log.d(TAG, "Huawei key:" + key);
            //0代表华为自动录音未开启,1代表华为自动录音已开启
            return key != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 跳转到VIVO开启通话自动录音功能页面
     */
    public static void startVivoRecord(Context context) {
        ComponentName componentName = new ComponentName("com.android.incallui", "com.android.incallui.record.CallRecordSetting");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast.makeText(context, "请打开VIVO通话自动录音功能", Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转到小米开启通话自动录音功能页面
     */
    public static void startXiaomiRecord(Context context) {
        ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.settings.CallRecordSetting");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast.makeText(context, "请打开小米通话自动录音功能", Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转到华为开启通话自动录音功能页面
     */
    public static void startHuaweiRecord(Context context) {
        ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.MSimCallFeaturesSetting");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast.makeText(context, "请打开华为通话自动录音功能", Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转到OPPO开启通话自动录音功能页面
     */
    public static void startOppoRecord(Context context) {
        ComponentName componentName = new ComponentName("com.android.phone", "com.android.phone.OppoCallFeaturesSetting");
        Intent intent = new Intent();
        intent.setComponent(componentName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast.makeText(context, "请打开OPPO通话自动录音功能", Toast.LENGTH_LONG).show();
    }

    /**
     * 打开无障碍设置页面
     * @param context
     */
    public static void startAccessibilitySetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        Toast.makeText(context, "请开启无障碍功能", Toast.LENGTH_LONG).show();
    }

    /**
     * /Settings下的 System 、Secure 、Global下的key和value遍历打印查看
     *
     * @param context
     */
    public static void findSettingKeyValue(Context context) {
        //1.Secure
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Cursor cursor = context.getContentResolver().query(Settings.Secure.CONTENT_URI, null, null, null);
            String[] columnNames = cursor.getColumnNames();
            StringBuilder builder = new StringBuilder("Secure:");
            while (cursor.moveToNext()) {
                for (String columnName : columnNames) {
                    int index = cursor.getColumnIndex(columnName);
                    if (index > -1) {
                        String string = cursor.getString(index);
                        builder.append(columnName).append(":").append(string).append("\n");
                    }
                }
            }
            Log.e(TAG, builder.toString());
        }

        //2.Global
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Cursor cursor = context.getContentResolver().query(Settings.Global.CONTENT_URI, null, null, null);
            String[] columnNames = cursor.getColumnNames();
            StringBuilder builder = new StringBuilder("Global:");
            while (cursor.moveToNext()) {
                for (String columnName : columnNames) {
                    int index = cursor.getColumnIndex(columnName);
                    if (index > -1) {
                        String string = cursor.getString(index);
                        builder.append(columnName).append(":").append(string).append("\n");
                    }
                }
            }
            Log.e(TAG, builder.toString());
        }

        //3.System
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Cursor cursor = context.getContentResolver().query(Settings.System.CONTENT_URI, null, null, null);
            String[] columnNames = cursor.getColumnNames();
            StringBuilder builder = new StringBuilder("System:");
            while (cursor.moveToNext()) {
                for (String columnName : columnNames) {
                    int index = cursor.getColumnIndex(columnName);
                    if (index > -1) {
                        String string = cursor.getString(index);
                        builder.append(columnName).append(":").append(string).append("\n");
                    }
                }
            }
            Log.e(TAG, builder.toString());
        }
    }

    static List<String> pathList = new ArrayList<>();

    public static File pathFile() {
        String parentPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e("cdq", parentPath);
        File childFile = null;
        String brand = getDeviceBrand();
        switch (brand) {
            case HUAWEI:
            case HONOR:
                if (isExist(parentPath + "/Sounds/CallRecord")) {
                    childFile = new File(parentPath + "/Sounds/CallRecord");
                } else if (isExist(parentPath + "/record")) {
                    childFile = new File(parentPath + "record");
                } else if (isExist(parentPath + "/Record")) {
                    childFile = new File(parentPath + "/Record");
                } else {
                    childFile = new File("");
                }

                break;
            case XIAOMI:
                childFile = new File(parentPath + "/MIUI/sound_recorder/call_rec/");
                break;
            case MEIZU:
                childFile = new File(parentPath + "/Recorder");
                break;
            case OPPO:
                if (isExist(parentPath + "/Recordings")) {
                    childFile = new File(parentPath + "/Recordings");
                } else if (isExist(parentPath + "Recorder")) {
                    childFile = new File(parentPath + "Recorder");
                } else if (isExist(parentPath + "Call Recordings")) {
                    childFile = new File(parentPath + "Call Recordings");
                } else {
                    childFile = new File("");
                }
                break;
            case VIVO:
                childFile = new File(parentPath + "/Record/Call");
                break;
            case SAMSUNG:
                childFile = new File(parentPath + "/Sounds");
                break;
            default:
                childFile = new File("");
                break;
        }
        return childFile;
    }

    /*传入拨打的电话号码做匹配*/
    public static List<String> getPathList(String phone) {
        ArrayList<String> result = new ArrayList<>();
        File[] files = pathFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    String fileName = file.getName();
                    if (fileName.trim().toLowerCase().contains(phone)) {
                        result.add(fileName);
                    }
                }
            }
        }
        return result;
    }

    /*传入拨打的电话号码做匹配*/
    public static List<File> getFileList(String phone) {
        ArrayList<File> result = new ArrayList<>();
        File[] files = pathFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    String fileName = file.getName();
                    if (fileName.trim().toLowerCase().contains(phone)) {
                        result.add(file);
                    }
                }
            }
        }
        return result;
    }

    public static List<File> getAllFileList() {
        ArrayList<File> result = new ArrayList<>();
        File[] files = pathFile().listFiles();
        Log.e("cdq", files == null ? "null" : files.length + "");
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    result.add(file);
                }
            }
        }
        return result;
    }

    /*传入拨打的电话号码做匹配*/
    public static File getLastFile(String phone) {
        File[] files = pathFile().listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    String fileName = file.getName();
                    if (fileName.trim().toLowerCase().contains(phone)) {
                        return file;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isExist(String path) {
        File file = new File(path);
        return file.exists() && file.isDirectory();
    }
}
