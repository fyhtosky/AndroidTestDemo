package com.example.administrator.diycode.audioView.common;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper {

    public static final String SDCARD = Environment
            .getExternalStorageDirectory().getPath();

    public static String getSDcardPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return SDCARD + "/";
        }
        return null;
    }

    /**
     * 获得SD卡总空间字节数 -1为SD卡不可用
     *
     * @return
     */
    public static long getSDcardTotalSpace() {
        String sdPath = getSDcardPath();
        if (sdPath != null) {
            StatFs fs = new StatFs(sdPath);
            return fs.getBlockSize() * fs.getBlockCount();
        } else {
            return -1L;
        }
    }

    /**
     * 获得SD卡可用字节数
     *
     * @return
     */
    public static long getSDcardFreeSpace() {
        final String sdPath = getSDcardPath();
        if (sdPath != null) {
            final StatFs fs = new StatFs(sdPath);
            return 1L * fs.getBlockSize() * fs.getAvailableBlocks();
        } else {
            return -1L;
        }
    }


    /**
     * delete a file/folder
     *
     * @param file the file object want to be deleted
     * @return true-->deleted success, false-->deleted fail.
     */
    public static boolean deleteFile(File file) {
        if (file == null) {
            throw new NullPointerException("file can not be null!");
        }
        boolean isDeleted = false;
        try {
            if (file.exists()) {// file exist
                if (file.isDirectory()) {// is a folder
                    File[] files = file.listFiles();
                    if (files == null || files.length == 0) {// empty folder
                        isDeleted = file.delete();
                        files = null;
                    } else {// contain folder or file
                        for (File item : files) {
                            if (!deleteFile(item)) {
                                item = null;
                                break;
                            }
                            item = null;
                        }
                        files = null;
                        isDeleted = file.delete();
                        file = null;
                        return isDeleted;
                    }
                } else {// is a file
                    if (file.canWrite()) {
                        isDeleted = file.delete();
                    }
                }
            } else {// file not exist!
                isDeleted = true;
            }
        } catch (SecurityException e) {
            Log.e("FileDao","--->deleteFile():"+ e.getMessage());
            isDeleted = false;
        }
        return isDeleted;
    }

    public byte[] readFile(File file, int buffSize) {
        byte[] data = null;
        if (file == null) {
            throw new NullPointerException("File can not be null..........");
        }
        if (buffSize <= 0) {
            throw new IllegalArgumentException("buffSize can not less than zero");
        }
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[buffSize];
            int actualSize = 0;
            while ((actualSize = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, actualSize);
            }
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }

    public boolean writeFile(File target, byte[] data) {
        boolean isSuccessed = false;
        if (target == null || data == null) {
            throw new NullPointerException("params can not be null........");
        }
        if (target.isDirectory()) {
            throw new IllegalArgumentException("target can only be file!...........");
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(target);
            fos.write(data);
            isSuccessed = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccessed;
    }

    public static boolean copyFile(File source, File target) {
        boolean isSuccessed = false;
        if (source == null || target == null) {
            throw new NullPointerException("source file and target file can not be null!......");
        }
        if (source.isDirectory() || target.isDirectory()) {
            throw new IllegalArgumentException("source and target can only be file..........");
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            int actualSize = 0;
            byte[] buffer = new byte[getBufferSizeByFileSize(source.length())];
            while ((actualSize = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, actualSize);
            }
            buffer = null;
            isSuccessed = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccessed;
    }


    public static int getBufferSizeByFileSize(long length) {
        if (length < 0) {
            throw new IllegalArgumentException("length can not less than zero.........");
        }
        if (length == 0) {
            return 0;
        }
        if (length > 104857600) {// 100MB
            return 1048576;// 1MB
        }
        if (length > 1048576) {// 1MB
            return 327680;// 32K
        }
        return 4096;// 4K
    }


    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
