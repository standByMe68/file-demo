package com.haocang.filedemo.handler;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haocang.filedemo.domian.mqtt.AlarmMqtt;
import com.haocang.filedemo.domian.mqtt.Data;
import com.haocang.filedemo.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Date;

public class TxtResolver implements BaseResolver<AlarmMqtt> {

    @Override
    public AlarmMqtt resolver(String data) {

        AlarmMqtt alarmMqtt = new AlarmMqtt();
        if (StringUtils.isNotBlank(data)) {
            alarmMqtt = doResolver(data,alarmMqtt);
        }
        return alarmMqtt;
    }

    public  AlarmMqtt doResolver(String data,AlarmMqtt alarmMqtt) {

        String[] s = data.split(",");
        String hmsss = s[0].replaceAll("\\[|\\]", "");
        if (hmsss.length() == 2) {
            String hms = hmsss.substring(0, hmsss.length() - 4);
            String complateDate = DateUtil.getComplateDate(hms);
            alarmMqtt.setTime(complateDate);
            String[] s1 = s[1].trim().replaceAll("◇", "").split(" ");
            if (s1.length != 2) {
                return null;
            }
            String alarmCode = s1[1];
            String alarmName = s1[0];
            Data currDate = new Data(alarmName, "1");
            Data[] datas = new Data[1];
            datas[0] = currDate;
            alarmMqtt.setData(datas);
        }
        return alarmMqtt;
    }

    /*public static void main(String[] args) {
        AlarmMqtt alarmMqtt = new AlarmMqtt();
        doResolver("[19:44:59:551], ◇设备恢复运行", alarmMqtt);
        System.out.println("alarmMqtt = " + alarmMqtt);
    }*/

}
