package cn.xunyard.idea.coding.i18n.inspection

import cn.xunyard.idea.coding.i18n.TranslateProcessContext
import cn.xunyard.idea.coding.i18n.dialog.AddTranslateDialogWrapper
import cn.xunyard.idea.coding.i18n.utils.InspectionUtils
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiLiteralExpression
import org.jetbrains.annotations.Nls

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-09
 */
class InspectionQuickFix constructor() : LocalQuickFix {

    @Nls(capitalization = Nls.Capitalization.Sentence)
    override fun getFamilyName(): String {
        return "Set error code translate"
    }

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        try {
            val expression: PsiLiteralExpression = descriptor.getPsiElement() as PsiLiteralExpression
            val errorCode: String? = InspectionUtils.getErrorCode(expression)
            if (errorCode != null) {
                DumbService.getInstance(project).smartInvokeLater {
                    AddTranslateDialogWrapper(project, errorCode,
                        TranslateProcessContext.getInstance(project).getTranslateProvider())
                        .showAndGet()
                }
            }
        } catch (e: Exception) {
            log.error(e)
        }
    }

    companion object {
        private val log = Logger.getInstance(InspectionQuickFix::class.java)

        var instance: InspectionQuickFix? = null
            get() {
                if (field == null) {
                    field = InspectionQuickFix()
                }
                return field
            }
    }
}