package ink.fujisann.learning.base.utils.common;

import javax.swing.filechooser.FileSystemView;

/**
 * 系统工具
 *
 * @author hulei
 * @date 2020-10-23 21:24:21:24
 */
@SuppressWarnings("unused")
public class SystemUtil {


    /**
     * 操作系统名称
     */
    public static final String OS_NAME = "os.name";

    /**
     * windows系统
     */
    public static final String WINDOWS = "windows";

    /**
     * 判断当前是否为windows系统
     *
     * @return 是windows系统返回true，否则返回false
     */
    public static boolean isWindows() {
        return System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS);
    }

    /**
     * 获取windows系统下，当前用户的桌面路径
     *
     * @return 桌面的绝对路径
     */
    public static String getDesktopPath() {
        return FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    }
}
