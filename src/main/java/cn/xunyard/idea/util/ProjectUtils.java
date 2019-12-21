package cn.xunyard.idea.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ProjectUtils {
    private static final String JAVA_SRC_ROOT = "/src/main/java/";
    public static ThreadLocal<Project> PROJECT = new ThreadLocal<>();

    public static boolean isSrcClass(String fullPath) {
        return !AssertUtils.isEmpty(fullPath) && fullPath.contains(JAVA_SRC_ROOT);
    }

    public static String resolveSrcPackagePath(String fullPath) {
        if (!isSrcClass(fullPath)) {
            return null;
        }

        return fullPath.substring(fullPath.lastIndexOf(JAVA_SRC_ROOT) + JAVA_SRC_ROOT.length(), fullPath.lastIndexOf("/"));
    }
}
