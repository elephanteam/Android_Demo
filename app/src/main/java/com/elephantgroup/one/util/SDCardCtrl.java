package com.elephantgroup.one.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Folder control class
 */
public class SDCardCtrl {


    /**
     * ROOTPATH
     */
    public static String ROOTPATH = "/.elephant";

    /**
     * UPLOADPATH
     */
    public static String UPLOADPATH = "/upload";


    /**
     * Wallet path
     * */
    public static String WALLERPATH = "/ethereum/keystore";


    /**
     * ERRORLOGPATH
     */
    public static String ERRORLOGPATH = "/ErrorLog";

    /**
     * OFFLINE
     */
    public static String FILE = "/file";

    /**
     * CHAT_FILE
     */
    public static String CHAT_FILE_PATH = "/file";


    public static String getWalletPath() {
        return WALLERPATH;
    }

    /**
     * @return ROOTPATH
     */
    public static String getCtrlCPath() {
        return ROOTPATH;
    }

    public static String getErrorLogPath() {
        return ERRORLOGPATH;
    }

    /**
     * @return Is or not exist SD card
     */
    public static boolean sdCardIsExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * <Build data file for this application >
     */
    public static void initPath(Context context) {
        String ROOT;
        if (sdCardIsExist()) {
            ROOT = Environment.getExternalStorageDirectory().getPath();
        } else {
            ROOT = "/mnt/sdcard";
        }
        if (ROOTPATH.equals("/.limitone")) {
            ROOTPATH = ROOT + ROOTPATH;
            ERRORLOGPATH = ROOTPATH + ERRORLOGPATH;
            UPLOADPATH = ROOTPATH + UPLOADPATH;
            FILE = ROOTPATH + FILE;
        }
        SDFileUtils.getInstance().createDir(ROOTPATH);
        SDFileUtils.getInstance().createDir(ERRORLOGPATH);
        SDFileUtils.getInstance().createDir(UPLOADPATH);
        SDFileUtils.getInstance().createDir(CHAT_FILE_PATH);
        SDFileUtils.getInstance().createDir(context.getFilesDir().getAbsolutePath() + WALLERPATH);
    }

    /**
     * Check the SDcard useful space
     *
     * @return
     */
    public static boolean checkSpace() {
        String path = Environment.getExternalStorageDirectory().getPath();
        StatFs statFs = new StatFs(path);
        int blockSize = statFs.getBlockSize() / 1024;
        int blockCount = statFs.getBlockCount();
        int usedBlocks = statFs.getAvailableBlocks() / 1024;
        int totalSpace = blockCount * blockSize / 1024;
        int avaliableSpace = totalSpace - usedBlocks;
        return avaliableSpace >= 64;
    }

    /**
     * to copy a single file
     * @ param oldPath String the original file path Such as: c: / FQF. TXT
     * @ param newPath String copied after the path Such as: f: / FQF. TXT
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {  //File exists
                InputStream inStream = new FileInputStream(oldPath);  //Read the original file
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <Save the App crash info to sdcard>
     */
    public static void saveCrashInfoToFile(String excepMsg) {
        if (TextUtils.isEmpty(excepMsg)) {
            return;
        }
        String errorLog = getErrorLogPath();
        FileWriter fw = null;
        PrintWriter pw = null;
        File logFile = null;
        try {
            String logSb = "crashLog" + "(" + DateFormatUtil.getSimpleDate() + ")" + ".txt";
            logFile = new File(errorLog, logSb);
            if (!logFile.exists()) {
                boolean newFile = logFile.createNewFile();
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.write(excepMsg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
