package com.haocang.filedemo.domian.mqtt;

import java.util.Arrays;
import java.util.List;

public class AlarmMqtt {

    private String time;

    private Data[] data;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AlarmMqtt{" +
                "time='" + time + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
