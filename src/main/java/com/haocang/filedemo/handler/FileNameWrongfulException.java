package com.haocang.filedemo.handler;

public class FileNameWrongfulException extends Exception {

    public FileNameWrongfulException() {
        super("文件名不带有后缀不合法");
    }

}
