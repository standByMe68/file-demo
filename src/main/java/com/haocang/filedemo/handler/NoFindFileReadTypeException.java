package com.haocang.filedemo.handler;

public class NoFindFileReadTypeException extends Exception {

    public NoFindFileReadTypeException(String fileReadType) {
        super(fileReadType);
    }


}
