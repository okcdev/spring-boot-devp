package com.rbs.cn.tools.uuid;

import java.util.UUID;

/**
 * UUID的工具类，用于生成新的UUID
 * @author
 */
public class UUIDUtils {

    /**
     * 新创建一个UUID
     * @return 新的UUID
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
