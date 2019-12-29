package cn.xunyard.idea.coding.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ProjectUtils {
    public static Project currentProject;

    public static void switchProject(Project project) {
        currentProject = project;
    }

    public static Project getCurrentProject() {
        if (currentProject != null) {
            return currentProject;
        }
        return ProjectUtil.guessCurrentProject(null);
    }

}
