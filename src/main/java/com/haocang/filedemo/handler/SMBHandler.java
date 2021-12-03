package com.haocang.filedemo.handler;

import com.haocang.filedemo.config.FileConstant;

import com.haocang.filedemo.domian.BaseInfo;
import com.haocang.filedemo.domian.SMBInfo;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SMBHandler implements BaseFileHandler{

    private static Logger logger = LoggerFactory.getLogger(SMBHandler.class);
    private static long lastTimeFileSize = 0;

    /**
     * 实时读取指定文件的内容
     * @param logFile
     * @throws FileNotFoundException
     */
    public static void realtimeShowLog(File logFile) throws FileNotFoundException {
        //指定文件可读可写
        final RandomAccessFile randomAccessFile = new RandomAccessFile(logFile, "rw");

        //启动一个线程每10秒钟读取新增的日志信息
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

        exec.scheduleWithFixedDelay(new Runnable() {
            @Override

            public void run() {
                try {
                    //获得变化部分的
                    randomAccessFile.seek(lastTimeFileSize);
                    String tmp = "";
                    while ( (tmp = randomAccessFile.readLine()) != null) {
                        // 输出文件内容
                        logger.info(new String(tmp.getBytes("ISO8859-1")));
                        System.out.println(new String(tmp.getBytes("ISO8859-1")));
                        lastTimeFileSize = randomAccessFile.length();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void readFileAdditionalContent(BaseInfo baseInfo) throws IOException {

        SMBInfo smbInfo = (SMBInfo) baseInfo;
        //判断文件是否已经被读取过
        Long readNum = FileConstant.chackFileRead(smbInfo.getFileName());
        SMBClient client = new SMBClient();
        try (Connection connection = client.connect(smbInfo.getHost())) {

            AuthenticationContext ac = null;
            if (FileConstant.LOGIN_GUEST.equals(smbInfo.getLoginType())) {
                ac = AuthenticationContext.guest();
            } else {
                ac = new AuthenticationContext(smbInfo.getUsername(), smbInfo.getPassword().toCharArray(), smbInfo.getHost());
            }
            Session session = connection.authenticate(ac);
            // Connect to Share
            try (DiskShare share = (DiskShare) session.connectShare(smbInfo.getFolderPath())) {
                com.hierynomus.smbj.share.File file = share.openFile(smbInfo.getFileName(), EnumSet.of(AccessMask.GENERIC_READ), null, SMB2ShareAccess.ALL, SMB2CreateDisposition.FILE_OPEN, null);
                InputStream inputStream = file.getInputStream();
                byte buffer[] = new byte[1];
                List<Byte> result = new ArrayList<>();
                Long currNum = 0L;
                while ((inputStream.read(buffer)) != -1) {
                    currNum++;
                    if (currNum > readNum) {
                        result.add(buffer[0]);
                        readNum++;
                    }
                }
                if (result.size() > 0) {
                    Byte[] bytes = new Byte[result.size()];
                    result.toArray(bytes);
                    byte[] bytes1 = ByteHandler.ByteArrayTobyte(bytes);
                    logger.info("追加数据:" + new String(bytes1));
                    FileConstant.fileReadNumMap.put(smbInfo.getFileName(), readNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
