package com.itheima.utils;

import java.util.UUID;

/**
 * 生成一个随机数
 */
public class UUIDUtils {
    /**
     * 生成30位全大写随机码
     * 
     * @return
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 生成令牌
     * 
     * @return
     */
    public static String getCode() {
        return getId();
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }
}
