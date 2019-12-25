package cn.xunyard.idea.util;

import com.intellij.openapi.project.Project;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaPackage;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ProjectUtils {
    public static final String JAVA_SRC_ROOT = "/src/main/java/";
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

    public static String getPackage(JavaClass javaClass) {
        if (javaClass.isPrimitive()) {
            return "";
        }

        JavaPackage pkg = javaClass.getPackage();

        if (pkg == null) {
            String fullClassName = javaClass.toString();

            if (fullClassName.contains(".")) {
                return fullClassName.substring(0, fullClassName.lastIndexOf("."));
            } else {
                return "";
            }
        }

        return pkg.getName();
    }

    public static String getSimpleName(JavaClass javaClass) {
        return javaClass.getSimpleName();
    }
}
