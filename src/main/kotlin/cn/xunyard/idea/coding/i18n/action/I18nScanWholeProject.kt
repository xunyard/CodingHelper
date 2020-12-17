package cn.xunyard.idea.coding.i18n.action

import cn.xunyard.idea.coding.i18n.TranslateProcessContext
import cn.xunyard.idea.coding.i18n.inspection.ErrorCodeWithoutTranslateInspection
import cn.xunyard.idea.coding.util.AssertUtils
import com.intellij.analysis.dialog.ProjectScopeItem
import com.intellij.codeInspection.InspectionManager
import com.intellij.codeInspection.InspectionProfile
import com.intellij.codeInspection.actions.RunInspectionIntention
import com.intellij.codeInspection.ex.InspectionManagerEx
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.ui.Messages
import com.intellij.profile.codeInspection.InspectionProjectProfileManager

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-17
 */
class I18nScanWholeProject : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val inspectionConfiguration = TranslateProcessContext.getInstance(e.project!!).inspectionConfiguration
        if (AssertUtils.isEmpty(inspectionConfiguration.all)) {
            DumbService.getInstance(e.project!!).smartInvokeLater {
                Messages.showMessageDialog("无翻译文件配置，请至 Tools | Coding Helper | I18n Settings添加翻译配置", "警告", Messages.getWarningIcon())
            }
            return
        }

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