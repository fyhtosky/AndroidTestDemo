package com.example.administrator.androidtestdemo.manager;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

public class FileManager {

    public static final String TAG="FileManager";

    /**
     * 是否支持sdcard挂载
     * @return
     */
    public static boolean isSdcardAvailable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取Sdcard的全部容量
     * @return
     */
    public static long getSDAllSizeKB() {
        // get path of sdcard
        File path = Environment.getExternalStorageDirectory();
        //StatFs获取的都是以block为单位的   http://blog.csdn.net/pang3510726681/article/details/6969557
        StatFs sf = new StatFs(path.getPath());
        // get single block size(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        return (allBlocks * blockSize) / 1024; // KB
    }

    /**
     * 获取Sdcard的可用容量
     * @return
     */
    public static long getSDAvalibleSizeKB() {
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long avaliableSize = sf.getAvailableBlocks();
        return (avaliableSize * blockSize) / 1024;// KB
    }

    /**
     * 判断sdcard是否存在该文件
     * @param director 文件夹名称
     * @return
     */
    public static boolean isFileExist(String director) {
        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + director);
        return file.exists();
    }

    /**
     * 判断某个文件夹是否存在在SD卡中以及创建一个文件夹
     * create multiple director
     * @param director
     * @return
     */
    public static boolean createFile(String director) {
        if (isFileExist(director)) {
            return true;
        } else {
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + director);
            if (!file.mkdirs()) {
                return false;
            }
            return true;
        }
    }

    /**
     *将数据写入到sdcard内部的文件中
     * @param directory
     *            (you don't need to begin with
     *            Environment.getExternalStorageDirectory()+File.separator)
     * @param fileName
     * @param content
     * @param encoding
     *            (UTF-8...)
     * @param isAppend
     *            : Context.MODE_APPEND
     * @return
     */
    public static File writeToSDCardFile(String directory, String fileName,
                                         String content, String encoding, boolean isAppend) {
        // mobile SD card path +path
        File file = null;
        OutputStream os = null;
        try {
            if (!createFile(directory)) {
                return file;
            }
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + directory + File.separator + fileName);
            os = new FileOutputStream(file, isAppend);
            if (encoding.equals("")) {
                os.write(content.getBytes());
            } else {
                os.write(content.getBytes(encoding));
            }
            os.flush();
        } catch (IOException e) {
            Log.e(TAG, "writeToSDCardFile:" + e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 将数据从inputstream写入SDCard
     * @param directory
     * @param fileName
     * @param input
     * @return
     */
    public File writeToSDCardFromInput(String directory, String fileName, InputStream input) {
        File file = null;
        OutputStream os = null;
        try {
            if (createFile(directory)) {
                return file;
            }
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + directory + File.separator + fileName);
            os = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int length = -1;
            while ((length = input.read(data)) != -1) {
                os.write(data, 0, length);
            }
            // clear cache
            os.flush();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 从指定文件夹中读取信息
     * @param directory
     * @param fileName
     * @return
     */
    public static String ReadFromSDCardFile(String directory,String fileName){
        String res="";
        File file = null;
        file = new File(Environment.getExternalStorageDirectory()
                + File.separator + directory + File.separator + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            byte [] buffer = new byte[length];
            fis.read(buffer);
            //将字节按照编码格式转成字符串
            res = fis.toString();
            fis.close();
            return res;
        }catch (FileNotFoundException e) {
            Log.d(TAG, "FileNotFound");
            e.printStackTrace();
        }catch (Exception  e) {
            Log.d(TAG, "Can Not Open File");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 放在该应用数据目录下的文件读写
     * 写操作
     * @param context
     * @param fileName
     * @param writestr
     */
    public static void writeFile(Context context,String fileName, String writestr){
        try{
            FileOutputStream fout =context.openFileOutput(fileName,MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 放在该应用数据目录下的文件读写
     * 读操作
     * @param context
     * @param fileName
     * @return
     */
    public  static String readFile(Context context,String fileName){
        String res="";
        try{
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte [] buffer = new byte[length];
            fin.read(buffer);
            res = fin.toString();
            fin.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
}
