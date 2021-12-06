package com.haocang.filedemo.handler;

import com.haocang.filedemo.domian.BaseInfo;

import java.io.IOException;

public interface BaseFileHandler {

    /**
     * 读取文件追加内容
     * @param baseInfo
     */
    public void readFileAdditionalContent(BaseInfo baseInfo) throws IOException, FileNameWrongfulException, ResolverNoExistentExceptiom;


}
