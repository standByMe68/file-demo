package com.haocang.filedemo.handler;

public class ResolverNoExistentExceptiom extends Exception {

    public ResolverNoExistentExceptiom(String fileReadType) {
        super(fileReadType);
    }
}
