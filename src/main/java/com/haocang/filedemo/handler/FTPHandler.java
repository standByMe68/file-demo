package com.haocang.filedemo.handler;

import com.haocang.filedemo.config.DataResolverFactory;
import com.haocang.filedemo.config.FileConstant;
import com.haocang.filedemo.domian.BaseInfo;
import com.haocang.filedemo.domian.FTPInfo;
import com.haocang.filedemo.domian.mqtt.AlarmMqtt;
import com.haocang.filedemo.utils.DateUtil;
import com.haocang.filedemo.utils.FileUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.ftp.FtpClient;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class FTPHandler implements BaseFileHandler {

    private static Logger logger = LoggerFactory.getLogger(FTPHandler.class);

    private static String localCharset = "GBK";
    private static String serverCharset = "ISO-8859-1";
    private static final String OPTS_UTF8 = "OPTS UTF8";
    private static final String CHARSET_UTF8 = "UTF-8";

    @Override
    public void readFileAdditionalContent(BaseInfo baseInfo) throws IOException, FileNameWrongfulException, ResolverNoExistentExceptiom {
        FTPInfo ftpInfo = (FTPInfo) baseInfo;
        //记录当前是多少字节
        long fileCurrSize = 0;
        //获取文件名称
        String filePath = FileUtil.getFilePath(baseInfo.getFolderPath(), baseInfo.getFileSuffix());

        //获取当前文件读取长度
        Long historyFileSize = FileConstant.fileReadNumMap.get(filePath);
        if (historyFileSize == null) {
            historyFileSize = 0L;
        }
        // 获取数据解析器
        BaseResolver build = DataResolverFactory.build(baseInfo.getFileSuffix());
        // 获取FTP客户端对象
        FTPClient ftpClient = getClient(ftpInfo);
        // 判断是否连接成功
        if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            //设置文件获取模式为被动式模式
            ftpClient.enterLocalPassiveMode();
            // 判断当前目录路径是否存在并且有权限
            if (ftpClient.changeWorkingDirectory(changeEncoding(ftpInfo.getFolderPath(),ftpClient))) {
                // 简历输入流，根据指定名称获取指定文件
                filePath = changeEncoding(filePath,ftpClient);
                logger.info("当前文件路径:{}",filePath);
                InputStream inputStream = ftpClient.retrieveFileStream(filePath);
                if (Objects.nonNull(inputStream)) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, ftpInfo.getServerEncode()));
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        fileCurrSize++;
                        if (fileCurrSize > historyFileSize) {
                            logger.info("追加数据:{}", line);
                            AlarmMqtt resolver =(AlarmMqtt) build.resolver(line);
                            if (Objects.nonNull(resolver)&&Objects.nonNull(resolver.getData())) {
                                logger.info("解析数据:{}",resolver);
                            }
                        }
                    }
                    FileConstant.fileReadNumMap.put(filePath, fileCurrSize);
                /* // 设置编码格式
                byte buffer[] = new byte[1];
                List<Byte> result = new ArrayList<>();
                String line = null;
                StringBuilder stringBuilder = new StringBuilder(150);
                while ((inputStream.read(buffer)) != -1) {
                    fileCurrSize++;
                    if (fileCurrSize > historyFileSize) {
                        result.add(buffer[0]);
                    }
                }

                if (result.size() > 0) {
                    Byte[] bytes = new Byte[result.size()];
                    result.toArray(bytes);
                    byte[] bytes1 = ByteHandler.ByteArrayTobyte(bytes);
                    logger.info("追加数据:" + new String(bytes1));
                    FileConstant.fileReadNumMap.put(ftpInfo.getFileName(), fileCurrSize);
                }*/
                } else {
                    logger.error("无法获取文件流");
                }
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
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

        if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
            // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码(GBK).
            localCharset = CHARSET_UTF8;
        }
        ftpClient.setControlEncoding(localCharset);
        return ftpClient;
    }

    /**

     * FTP服务器路径编码转换

     *

     * @param ftpPath FTP服务器路径

     * @return String

     */

    private static String changeEncoding(String ftpPath, FTPClient ftpClient) {
        String directory = null;

        try {
            if (FTPReply.isPositiveCompletion(ftpClient.sendCommand(OPTS_UTF8, "ON"))) {
                localCharset = CHARSET_UTF8;
            }
            directory = new String(ftpPath.getBytes(localCharset), serverCharset);
        } catch (Exception e) {
            logger.error("路径编码转换失败", e);

        }

        return directory;
    }



}
