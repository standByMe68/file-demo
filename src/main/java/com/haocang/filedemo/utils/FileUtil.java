package com.haocang.filedemo.utils;

import com.haocang.filedemo.config.FileConstant;
import com.haocang.filedemo.handler.FileNameWrongfulException;

public class FileUtil {

    public static String getFilePath(String folderPath,String fileSuffix) {

        fileSuffix = fileSuffix.toUpperCase();
        String ymd = DateUtil.getYMD();
        if (FileConstant.TXT.equals(fileSuffix)) {
            return folderPath + "/" + ymd + "/" + ymd + FileConstant.ALARM_FILE;
        } else {
            return null;
        }
    }

    private static String getFileSuffix(String fileName) throws FileNameWrongfulException {
        Integer index = 0;
        if (( index =  fileName.indexOf(",")) < 0) {
            throw new FileNameWrongfulException();
        }
        return fileName.substring(index + 1).toUpperCase();
    }

}
