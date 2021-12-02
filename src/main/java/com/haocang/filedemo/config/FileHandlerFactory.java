package com.haocang.filedemo.config;

import com.haocang.filedemo.handler.*;

/**
 * 文件读取工厂
 * 可以配置多种读取协议
 */
public class FileHandlerFactory {

    public static BaseFileHandler build(String protocolType) throws NoFindFileReadTypeException {
        switch (protocolType) {
            case FileConstant.FILE_FTP:
                return new FTPHandler();
            case FileConstant.FILE_SMB:
                return new SMBHandler();
            default:
                throw new NoFindFileReadTypeException(protocolType);
        }
    }

}
