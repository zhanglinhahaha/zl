package com.miniapp.account;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.miniapp.account.activity.AccountConstants;
/**
 * Created by zl on 19-10-25.
 * 打印日志工具
 * 当level=VERBOSE，日志全部打印
 * 当level=NOTHING，日志全部不打印
 */
public class LogUtil {
    private static final String TAG = "zl_Account_LogUtil";

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    private static final SimpleDateFormat MY_LOG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String MY_LOG_FILE_NAME = "log.txt";
    private static final String MY_LOG_FILE_NAME_BACK = "log_back.txt";
    private static final int FILE_SIZE = 2 * 1024 * 1024;

    private static int mLevel = VERBOSE;

    public static void setLogLevel(int level) {
        mLevel = level;
    }

    public static void v(String msg) {
        v(TAG, msg);
    }
    public static void v(String tag, String msg) {
        if (mLevel <= VERBOSE) {
            writeLogToFile("VERBOSE", tag, msg);
            Log.v(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }
    public static void d(String tag, String msg) {
        if(mLevel <= DEBUG) {
            writeLogToFile("DEBUG", tag, msg);
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }
    public static void i(String tag, String msg) {
        if(mLevel <= INFO) {
            writeLogToFile("INFO", tag, msg);
            Log.i(tag, msg);
        }
    }

    public static void w(String msg) {
        w(TAG, msg);
    }
    public static void w(String tag, String msg) {
        writeLogToFile("WARNING", tag, msg);
        if(mLevel <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }
    public static void e(String tag, String msg) {
        writeLogToFile("ERROR", tag, msg);
        if(mLevel <= ERROR) {
            Log.e(tag, msg);
        }
    }

    private static void writeLogToFile(String mylogtype, String tag, String text) {
        Date nowTime = new Date();
        String needWriteMessage = MY_LOG_SDF.format(nowTime) + "    " + mylogtype + "    " + tag + "    " + text;
        File dirsFile = new File(AccountConstants.ACCOUNT_DIR_PATH);
        if (!dirsFile.exists()){
            dirsFile.mkdirs();
        }
        File file = new File(dirsFile.toString(),  MY_LOG_FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            long size = file.length();
            if(size >= FILE_SIZE) {
                File file1 = new File(dirsFile.toString(), MY_LOG_FILE_NAME_BACK);
                if(file1.exists()) file1.delete();
                file.renameTo(file1);
                file.delete();
            }
        }

        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}