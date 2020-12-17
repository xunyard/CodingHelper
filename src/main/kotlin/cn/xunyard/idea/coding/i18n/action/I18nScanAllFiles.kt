package cn.xunyard.idea.coding.i18n.action

import cn.xunyard.idea.coding.i18n.inspection.ErrorCodeWithoutTranslateInspection
import com.intellij.analysis.dialog.ProjectScopeItem
import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.InspectionProfile
import com.intellij.codeInspection.actions.RunInspectionIntention
import com.intellij.codeInspection.ex.InspectionManagerEx
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbService
import com.intellij.profile.codeInspection.InspectionProjectProfileManager

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-17
 */
class I18nScanAllFiles : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val currentProfile: InspectionProfile = InspectionProjectProfileManager.getInstance(e.project!!).currentProfile
        val toolWrapper =
            currentProfile.getInspectionTool(ErrorCodeWithoutTranslateInspection.displayName, e.project!!)
                ?: throw RuntimeException("")
        val managerEx = InspectionManager.getInstance(e.project!!) as InspectionManagerEx
        val projectScopeItem = ProjectScopeItem(e.project!!)

        DumbService.getInstance(e.project!!).smartInvokeLater {
            RunInspectionIntention.rerunInspection(
                toolWrapper,
                managerEx,
                projectScopeItem.scope,
                null
            )
        }
    }
}