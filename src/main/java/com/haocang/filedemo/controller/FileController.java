package com.haocang.filedemo.controller;


import com.haocang.filedemo.config.FileConstant;
import com.haocang.filedemo.config.FileConfig;
import com.haocang.filedemo.config.FileHandlerFactory;
import com.haocang.filedemo.domian.BaseInfo;
import com.haocang.filedemo.domian.FTPInfo;
import com.haocang.filedemo.domian.SMBInfo;
import com.haocang.filedemo.handler.BaseFileHandler;
import com.haocang.filedemo.handler.FTPHandler;
import com.haocang.filedemo.handler.NoFindFileReadTypeException;
import com.haocang.filedemo.handler.SMBHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

@RestController
@RequestMapping("/file")
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    private static ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
    @Autowired
    private FileConfig fileConfig;

    /**
     * 读取配置文件中的追加文件
     * @param fileName
     * @return
     */
    @GetMapping("/read")
    public String readFile(@RequestParam(value = "path") String fileName) throws NoFindFileReadTypeException {

        if (FileConstant.fileReadNumMap.size() < 10) {
            //获取读取文件所需要的信息
            BaseInfo info = FileConstant.getInfo(fileConfig, fileName);
            logger.info("当前读取文件信息:{}",info);
            //获取文件处理器
            BaseFileHandler build = FileHandlerFactory.build(info.getType());
            //启动一个线程每10秒钟读取新增的日志信息
            exec.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        build.readFileAdditionalContent(info);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 10, TimeUnit.SECONDS);
        } else {
            return "最多只能读取10个文件";
        }
        return "正在进行读取,请在日志中查看！";
    }


}
