package com.example.sqllearning.utils.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @program: sqlleaning
 * @description: 扫面注解
 * @author: hulei
 * @create: 2020-03-15 21:45:41
 */
@Slf4j
public class ScanAnnotation {

    /**
     * @description 扫描类中所有的RequiresPermissions注解值
     * @param scanClass 被扫描的类
     * @return java.util.List<java.lang.Object>
     * @author hulei
     * @date 2020-03-16 23:08:03
     */
    public static List<String> getValueByClass(Class scanClass) {
        List<String> allPermissionValue = new ArrayList<>();
        Method[] methods = scanClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequiresPermissions.class)) {
                Object permissionValue = method.getAnnotation(RequiresPermissions.class).value();
                for (String permissionItem : (String[]) permissionValue) {
                    allPermissionValue.add(permissionItem);
                }
            }
        }
        return allPermissionValue;
    }

}
