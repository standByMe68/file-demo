package com.haocang.filedemo.domian;

public class FTPInfo extends BaseInfo{

    public Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public FTPInfo(String  port,String host, String username, String password, String type, String folderPath, String fileName,String loginType) {
        super(host,username,password,type,folderPath,fileName,loginType);
        this.port = Integer.valueOf(port);
    }
}
