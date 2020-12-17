package cn.xunyard.idea.coding.i18n.logic

import com.intellij.openapi.project.Project

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-17
 */
interface TranslateFileEnhance {

    /**
     * 从磁盘重新加载
     */
    fun reloadFromDisk(project: Project)

    /**
     * 将所有翻译按照字母重新排序
     */
    fun sortByName(project: Project)

    /**
     * 对翻译进行内容检查
     */
    fun invalidCheck(project: Project)
}