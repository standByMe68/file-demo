package com.haocang.filedemo.config;

import com.haocang.filedemo.handler.CsvResolver;
import com.haocang.filedemo.handler.ResolverNoExistentExceptiom;
import com.haocang.filedemo.handler.TxtResolver;
import com.haocang.filedemo.handler.BaseResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataResolverFactory {
    private static Logger logger = LoggerFactory.getLogger(DataResolverFactory.class);

    public static BaseResolver build(String fileSuffix) throws ResolverNoExistentExceptiom {
        if (FileConstant.CSV.equals(fileSuffix)) {
            return new CsvResolver();
        } else if (FileConstant.TXT.equals(fileSuffix)) {
            return new TxtResolver();
        } else {
            throw new ResolverNoExistentExceptiom("不存在可以解析的文件后缀");
        }

    }


}
