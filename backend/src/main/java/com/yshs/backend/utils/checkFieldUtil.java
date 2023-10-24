package com.yshs.backend.utils;

import com.baomidou.mybatisplus.annotation.TableId;
import com.yshs.backend.entity.Employee;
import com.baomidou.mybatisplus.annotation.TableField;

import java.lang.reflect.Field;

public class checkFieldUtil {
    public static boolean isValidField(Class<?> clazz,String field) {
        try {
            Field declaredField = clazz.getDeclaredField(field);
            return declaredField.isAnnotationPresent(TableField.class) || declaredField.isAnnotationPresent(TableId.class);
        } catch (NoSuchFieldException e) {
            return false;
        }
    }
}
