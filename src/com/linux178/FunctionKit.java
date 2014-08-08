package com.linux178;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class FunctionKit {

    /**
     * 日期的格式 类似于下面的：
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd
     * */
    public static String getDate(String date_format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(date_format);
        return dateFormat.format(new Date());
    }

    /**
     * 把ArrayList转换为字符串
     * */
    public static String listToString(ArrayList<String> arrayList){
        StringBuilder result = new StringBuilder();
        for (String item : arrayList){
            result.append(item);
            result.append(" ");
        }
        return result.toString();
    }

    /**
     * 检查文件大小是否为0字节
     * */
    public static boolean checkFileSizeIsZero(String filename){
        File file = new File(filename);
        return file.exists() && file.length() > 0;
    }
}
