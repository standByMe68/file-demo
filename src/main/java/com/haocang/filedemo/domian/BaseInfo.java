package com.haocang.filedemo.domian;

public class BaseInfo {

    public String host;

    public String username;

    public String password;

    public String type;

    public String folderPath;

    public String fileName;

    public String loginType;


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public BaseInfo(String host, String username, String password, String type, String folderPath, String fileName,String loginType) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.type = type;
        this.folderPath = folderPath;
        this.fileName = fileName;
        this.loginType = loginType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
