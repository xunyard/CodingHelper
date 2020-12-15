package cn.xunyard.idea.coding.i18n.inspection

import cn.xunyard.idea.coding.i18n.logic.TranslateProcessContext
import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-08
 */
class ErrorCodeWithoutTranslateInspection : AbstractBaseJavaLocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        return InspectionPsiElementVisitor(holder, TranslateProcessContext.getInstance(holder.project).getTranslateProvider())
    }
}