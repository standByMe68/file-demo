package com.haocang.filedemo.domian;

public class FTPInfo extends BaseInfo{

    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public FTPInfo(String  port,String host, String username, String password, String type, String folderPath, String fileType,String loginType,String serverEncode) {
        super(host,username,password,type,folderPath,fileType,loginType,serverEncode);
        this.port = Integer.valueOf(port);
    }
}
