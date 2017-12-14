package com.bean.enums;

/**
 * Created by Administrator on 2017/12/14.
 */
public enum TASK_STATE {
    NONE("NONE","未知"),
    NORMAL("NORMAL", "正常运行"),
    PAUSED("PAUSED", "暂停状态"),
    COMPLETE("COMPLETE",""),
    ERROR("ERROR","错误状态"),
    BLOCKED("BLOCKED","锁定状态");

    private TASK_STATE(String index,String name) {
        this.name = name;
        this.index = index;
    }
    private String index;
    private String name;
    public String getName() {
        return name;
    }
    public String getIndex() {
        return index;
    }
}
