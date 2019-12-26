package cn.xunyard.idea.coding.util;

import com.intellij.openapi.project.Project;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ProjectUtils {
    public static ThreadLocal<Project> PROJECT = new ThreadLocal<>();

    public static Project getCurrentProject() {
        return PROJECT.get();
    }

}
