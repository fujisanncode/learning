package ink.fujisann.learning.base.utils.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 注解扫描
 *
 * @author hulei
 * @date 2020-11-03 00:16:35
 */
@Slf4j
public class ScanAnnotation {

    /**
     * 扫描类中所有的{@code RequiresPermissions}注解值
     *
     * @param scanClass 被扫描的类
     * @return 权限点名称列表
     */
    public static List<String> getValueByClass(Class<?> scanClass) {
        List<String> allPermissionValue = new ArrayList<>();
        Method[] methods = scanClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequiresPermissions.class)) {
                Object permissionValue = method.getAnnotation(RequiresPermissions.class).value();
                allPermissionValue.addAll(Arrays.asList((String[]) permissionValue));
            }
        }
        return allPermissionValue;
    }

}
