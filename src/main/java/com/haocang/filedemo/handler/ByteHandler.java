package com.haocang.filedemo.handler;

public class ByteHandler {

    public static byte[] ByteArrayTobyte(Byte[] bytes) {
        byte[] byteArray = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            byteArray[i] = bytes[i];
        }
        return byteArray;
    }
}
