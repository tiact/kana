package com.tiact.websocket.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.Map;

/**
 * 全局工具类
 *
 * @author Tia_ct
 */
public class CommonUtils {

    /**
     * 判断对象是否Empty
     *
     * @param object 待检查对象
     * @return boolean
     */
    public static boolean isEmpty(Object object) {

        if (object == null) {
            return true;
        }
        if (object == "") {
            return true;
        }
        if (object instanceof String) {
            if (((String) object).trim().length() == 0) {
                return true;
            }
        }
        else if (object instanceof Collection) {
            if (((Collection) object).size() == 0) {
                return true;
            }
        }
        else if (object instanceof Map) {
            if (((Map) object).size() == 0) {
                return true;
            }
        }

        return false;

    }

    /**
     * 判断对象是否为NotEmpty
     *
     * @param object 待检查对象
     * @return boolean
     */
    public static boolean isNotEmpty(Object object) {

        if (object == null) {
            return false;
        }
        if (object == "") {
            return false;
        }
        if (object instanceof String) {
            if (((String) object).trim().length() == 0) {
                return false;
            }
        }
        else if (object instanceof Collection) {
            if (((Collection) object).size() == 0) {
                return false;
            }
        }
        else if (object instanceof Map) {
            if (((Map) object).size() == 0) {
                return false;
            }
        }

        return true;

    }

    /**
     * 读取文本
     * @param file
     * @return
     */
    public static String txt2String(File file){
        String result = "";
        try{
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            //使用readLine方法，一次读一行
            while((s = br.readLine())!=null){
                result = result +s;
                /* result = result + "\n" +s; */
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.trim();
    }



}
