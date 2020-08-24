package com.cslg.utils;

import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

public class ReadVideoTimeLong {

    public static String ReadVideoTime(String FileUrl) {
        File source=new File(FileUrl);
        String length = "";
        try {
            MultimediaObject instance = new MultimediaObject(source);
            MultimediaInfo result = instance.getInfo();
            long ls = result.getDuration() / 1000;
            int hour = (int) (ls / 3600);
            int minute = (int) (ls % 3600) / 60;
            int second = (int) (ls - hour * 3600 - minute * 60);
            length = hour + ":" + minute + ":" + second ;
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return length;
    }

    
    
    /**
     * 
     * @描述：获取视频大小 
     * @时间：2018年8月28日 上午10:30:17
     * @param source
     * @return
     */
    @SuppressWarnings({ "resource" })
    String ReadVideoSize(File source) {
        FileChannel fc = null;
        String size = "";
        try {
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2,RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

}