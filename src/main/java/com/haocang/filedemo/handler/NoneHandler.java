package com.haocang.filedemo.handler;

import com.haocang.filedemo.domian.BaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NoneHandler implements BaseFileHandler{
    private static Logger logger = LoggerFactory.getLogger(NoneHandler.class);

    @Override
    public void readFileAdditionalContent(BaseInfo baseInfo) throws IOException {
        logger.info("不进行任务处理");
    }

}
