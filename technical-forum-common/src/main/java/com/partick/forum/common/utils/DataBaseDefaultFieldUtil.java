package com.partick.forum.common.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.lang.reflect.Field;

/**
 * @author partick_peng
 */
public class DataBaseDefaultFieldUtil {
    /**
     * 创建默认属性
     * @param object
     * @return
     * @throws Exception
     */
    public static Object defaultCreateField(Object object) throws Exception {
        DateTime date = DateUtil.date(System.currentTimeMillis());
        setFieldByName(object, "isDeleted", 0);
        setFieldByName(object, "createTime", date);
        setFieldByName(object, "createBy", "admin");
        setFieldByName(object, "updateTime", date);
        setFieldByName(object, "updateBy", "admin");
        return object;
    }

    /**
     * 修改默认属性
     * @param object
     * @return
     * @throws Exception
     */
    public static Object defaultUpdateField(Object object) throws Exception {
        DateTime date = DateUtil.date(System.currentTimeMillis());
        setFieldByName(object, "updateTime", date);
        setFieldByName(object, "updateBy", "admin");
        return object;
    }

    private static void setFieldByName(Object object,String name,Object value) throws Exception{
        Field declaredField = object.getClass().getDeclaredField(name);
        declaredField.setAccessible(true);
        declaredField.set(object,value);
    }
}
