package com.miao.common.utils;

/**
 * 电影枚举类
 *
 * @author miao
 */

public enum MovieEnum {
    SUPERHERO("superhero", "超英热门"),
    TRAILER("trailer", "热门");
    /**
     * 类型
     */
    private final String type;
    /**
     * 值
     */
    private final String value;

    MovieEnum(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
