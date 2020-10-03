package cn.xunyard.idea.coding.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessCurrentProject

/**
 * @author <a herf="mailto:xunyard@gmail.com">xunyard</a>
 * @date 2019-12-15
 */
object ProjectUtils {
    private var currentProject: Project? = null
    fun switchProject(project: Project?) {
        currentProject = project
    }

    fun getCurrentProject(): Project {
        return currentProject?: guessCurrentProject(null)
    }
}