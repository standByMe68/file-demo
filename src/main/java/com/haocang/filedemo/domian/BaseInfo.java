package com.haocang.filedemo.domian;

public class BaseInfo {

    private String host;

    private String username;

    private String password;

    private String agreementType;

    private String folderPath;

    private String fileSuffix;

    private String loginType;

    private String serverEncode;

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public BaseInfo(String host, String username, String password, String agreementType, String folderPath, String fileSuffix,String loginType,String serverEncode) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.agreementType = agreementType;
        this.folderPath = folderPath;
        this.fileSuffix = fileSuffix;
        this.loginType = loginType;
        this.serverEncode = serverEncode;
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



    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getServerEncode() {
        return serverEncode;
    }

    public void setServerEncode(String serverEncode) {
        this.serverEncode = serverEncode;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", agreementType='" + agreementType + '\'' +
                ", folderPath='" + folderPath + '\'' +
                ", fileSuffix='" + fileSuffix + '\'' +
                ", loginType='" + loginType + '\'' +
                '}';
    }
}
