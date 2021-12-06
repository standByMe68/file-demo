package com.haocang.filedemo.config;

import com.haocang.filedemo.domian.BaseInfo;
import com.haocang.filedemo.domian.FTPInfo;
import com.haocang.filedemo.domian.SMBInfo;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class FileConstant {

    //文件协议
    public static final String FILE_SMB = "SMB";
    public static final String FILE_FTP = "FTP";


    //登录模式
    public static final String LOGIN_USER = "USER";
    public static final String LOGIN_GUEST = "GUEST";

    //文件后缀名
    public static final String CSV = "CSV";
    public static final String TXT = "TXT";

    //文件名
    public static final String ALARM_FILE = "_RcamRunErrorLog.txt";

    // 文件名与文件已读取大小(FTP:d读取行数,SMB:字节大小)映射关系
    public static ConcurrentHashMap<String, Long> fileReadNumMap = new ConcurrentHashMap<>();

    /**
     * 根据文件名称获取文件已读大小
     * @param fileName
     * @return
     */
    public static Long  chackFileRead(String fileName) {
        Long readNum = FileConstant.fileReadNumMap.get(fileName);
        if (Objects.isNull(readNum)) {
            FileConstant.fileReadNumMap.put(fileName, 0L);
            return 0L;
        } else {
            return FileConstant.fileReadNumMap.get(fileName);
        }
    }


    public static BaseInfo getInfo(FileConfig fileConfig,String fileType) {

        String folderPath = fileConfig.getFolderPath();
        String host = fileConfig.getHost();
        String password = fileConfig.getPassword();
        String username = fileConfig.getUsername();
        String port = fileConfig.getPort();
        String type = fileConfig.getType();
        String loginType = fileConfig.getLoginType();
        String serverEncode = fileConfig.getServerEncode();

        if (FileConstant.FILE_FTP.equals(type)) {
            return new FTPInfo(port, host, username, password, type, folderPath, fileType,loginType,serverEncode);
        } else if (FileConstant.FILE_SMB.equals(type)) {
            return new SMBInfo(host, username, password, type, folderPath, fileType, loginType,serverEncode);
        }
        return null;
    }
}
