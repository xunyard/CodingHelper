package cn.xunyard.idea.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ProjectUtils {

    public static Project getCurrentProject() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        return openProjects.length == 0 ? null : openProjects[0];
    }
}
