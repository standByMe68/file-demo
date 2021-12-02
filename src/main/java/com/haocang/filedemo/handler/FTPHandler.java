package com.haocang.filedemo.handler;

import com.haocang.filedemo.config.FileConstant;
import com.haocang.filedemo.domian.BaseInfo;
import com.haocang.filedemo.domian.FTPInfo;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.ftp.FtpClient;

import java.io.*;

public class FTPHandler implements BaseFileHandler {

    private static Logger logger = LoggerFactory.getLogger(FTPHandler.class);


    @Override
    public void readFileAdditionalContent(BaseInfo baseInfo) throws IOException {
        FTPInfo ftpInfo = (FTPInfo) baseInfo;
        //记录当前是多少字节
        long fileCurrSize = 0;
        //获取当前文件读取长度
        Long historyFileSize = FileConstant.fileReadNumMap.get(ftpInfo.getFileName());
        if (historyFileSize == null) {
            historyFileSize = 0L;
        }
        // 获取FTP客户端对象
        FTPClient ftpClient = getClient(ftpInfo);
        // 判断是否连接成功
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            //设置文件获取模式为被动式模式
            ftpClient.enterLocalPassiveMode();
            // 判断当前目录路径是否存在并且有权限
            if (ftpClient.changeWorkingDirectory(ftpInfo.getFolderPath())) {
                // 简历输入流，根据指定名称获取指定文件
                InputStream inputStream = ftpClient.retrieveFileStream(ftpInfo.getFolderPath() + "/" + ftpInfo.getFileName());
                // 设置编码格式
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                String line = null;
                StringBuilder stringBuilder = new StringBuilder(150);
                while ((line = bufferedReader.readLine()) != null) {
                    fileCurrSize++;
                    if (fileCurrSize > historyFileSize) {
                        logger.info("追加数据:{}", line);
                        stringBuilder.append(line + "");
                    }
                }
                FileConstant.fileReadNumMap.put(ftpInfo.getFileName(), fileCurrSize);
            } else {
                logger.info("当前目录不存在");
            }

            ftpClient.disconnect();
        } else {
            logger.info("连接FTP服务器失败");
        }
    }


    public FTPClient getClient(FTPInfo ftpInfo) throws IOException {
        // 配置FTP客户端
        FTPClient ftpClient=new FTPClient();
        // 连接FTP服务器
        ftpClient.connect(ftpInfo.getHost(), ftpInfo.getPort());
        // 登录FTP服务器(非游客模式)
        if (FileConstant.LOGIN_USER.equals(ftpInfo.getLoginType())) {
            ftpClient.login(ftpInfo.getUsername(), ftpInfo.getPassword());
        }
        return ftpClient;
    }
}
