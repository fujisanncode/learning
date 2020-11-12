package ink.fujisann.learning.base.utils.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import java.io.File;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * 注解扫描
 *
 * @author hulei
 * @date 2020-11-03 00:16:35
 */
@Slf4j
@SuppressWarnings("unused")
public class ScanAnnotation {

    /**
     * 扫描类中所有的{@code RequiresPermissions}注解值
     *
     * @param scanClass 被扫描的类
     * @return 权限点名称列表
     */
    public static List<String> buildPermissionListByClass(Class<?> scanClass) {
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

    /**
     * 扫描包下所有shiro权限点
     *
     * @param packageName 包名
     * @return 权限点列表
     */
    public static List<String> buildPermissionListByPackage(String packageName) {
        ArrayList<String> result = new ArrayList<>();
        getClassesByPackage(packageName).forEach(aClass -> result.addAll(buildPermissionListByClass(aClass)));
        return result;
    }


    /**
     * 文件类型的资源
     */
    private static final String FILE_RESOURCE = "file";

    /**
     * Jar包类型的资源
     */
    private static final String JAR_RESOURCE = "jar";

    /**
     * 字节码文件的后缀
     */
    private static final String CLASS_SUFFIX = ".class";

    /**
     * 扫描指定包下全部shiro权限点注解<br/>
     * 参考：<a>https://www.iteye.com/blog/sauzny-2209337</a><br/>
     *
     * @param packageName 指定包名
     * @return 权限点注解列表
     */
    @SneakyThrows
    public static LinkedHashSet<Class<?>> getClassesByPackage(String packageName) {
        String packageDir = packageName.replace(".", "/");
        // 加载资源使用文件绝对路径，即加载packageDir这个文件，此文件是一个目录
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageDir);
        log.info("resources ======> {}", resources.hasMoreElements());
        LinkedHashSet<Class<?>> result = new LinkedHashSet<>();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            log.info("protocol ======> {}", protocol);
            log.info("path ======> {}", url.getPath());
            // jar包方式启动，protocol为jar
            if (protocol.equals(FILE_RESOURCE)) {
                // 获取资源绝对路径，后面使用绝对路径创建文件
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                filePath = filePath.substring(1);
                log.info("filePath ======> {}", filePath);
                File rootDir = new File(filePath);
                if (!rootDir.isDirectory()) {
                    return result;
                }
                // 遍历根目录下全部文件
                for (File file : rootDir.listFiles()) {
                    buildAllClasses(file.getAbsolutePath(), packageName, result);
                }
            } else if (protocol.equals(JAR_RESOURCE)) {
                JarURLConnection connection = (JarURLConnection) url.openConnection();
                JarFile jarFile = connection.getJarFile();
                Enumeration<JarEntry> entries = jarFile.entries();
                // entries会递归扫描jar中所有的文件
                while (entries.hasMoreElements()) {
                    JarEntry jarEntry = entries.nextElement();
                    String jarEntryName = jarEntry.getName();
                    log.info("jarName ======> {}", jarEntryName);
                    // 如果是目录或者不是.class文件，跳过
                    if (jarEntry.isDirectory() || !jarEntryName.endsWith(CLASS_SUFFIX)) {
                        continue;
                    }
                    // 如果当前遍历项不是目标包下的文件，则跳过
                    log.info("====> {}", packageDir + "/");
                    if (!jarEntryName.startsWith(packageDir + "/")) {
                        continue;
                    }
                    jarEntryName = jarEntryName.replace(".class", "")
                            .replaceAll("/", ".");
                    log.info("jarName replace =======> {}", jarEntryName);
                    Class<?> aClass = Class.forName(jarEntryName);
                    // 跳过注解、枚举、接口、基本类型
                    if (aClass.isAnnotation() || aClass.isEnum() || aClass.isInterface() || aClass.isPrimitive()) {
                        continue;
                    }
                    result.add(aClass);
                }
            }
        }
        return result;
    }


    public static void main(String[] args) {
        log.info("===> {}", "ink/fujisann/learning/code/controller/web/MapController.class ".startsWith("ink/fujisann/learning/code/controller/web" + "/"));
    }

    /**
     * 将path下所有的class找到并保存起来
     *
     * @param filePath    包下文件绝对路径
     * @param packageName 包名
     * @param result      保存扫描到的class
     */
    @SneakyThrows
    private static void buildAllClasses(String filePath, String packageName, LinkedHashSet<Class<?>> result) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            // 如果当前是目录，将当前路径名拼接为包名
            packageName = packageName + "." + file.getName();
            // 如果当前是目录，则对目录下文件进行递归
            File[] files = file.listFiles();
            for (File child : files) {
                String childPath = child.getAbsolutePath();
                buildAllClasses(childPath, packageName, result);
            }
        } else {
            // 如果当前是文件，并且是.class文件，则加载为类
            String fileName = file.getName();
            if (fileName.endsWith(CLASS_SUFFIX)) {
                int length = fileName.length();
                fileName = fileName.substring(0, length - 6);
                // 加载类使用包名
                String className = packageName + "." + fileName;
                Class<?> tmp = Thread.currentThread().getContextClassLoader().loadClass(className);
                result.add(tmp);
            }
        }
    }
    
}
