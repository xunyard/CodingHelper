package cn.xunyard.idea.coding.i18n.inspection

import cn.xunyard.idea.coding.i18n.logic.InspectionUtils
import cn.xunyard.idea.coding.i18n.logic.LanguageTranslateProvider
import cn.xunyard.idea.coding.util.AssertUtils
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.*

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-09
 */
class InspectionPsiElementVisitor constructor(
        private val problemHolder: ProblemsHolder,
        private val translateProvider: LanguageTranslateProvider
) : JavaElementVisitor() {


    override fun visitLiteralExpression(expression: PsiLiteralExpression) {
        super.visitLiteralExpression(expression)

        // 错误码应该包含符号'.'
        val text: String = expression.text
        if (AssertUtils.isEmpty(text) || !InspectionUtils.isErrorCode(text)) {
            return
        }

        // 仅处理方法中出现的字符串
        if (expression.context !is PsiExpressionList ||
                (expression.context!!.context) !is PsiMethodCallExpression) {
            return
        }
        val psiExpressionList = expression.context as PsiExpressionList

        // 左括号
        if (psiExpressionList.firstChild !is PsiJavaToken ||
                (psiExpressionList.firstChild as PsiJavaToken).tokenType != JavaTokenType.LPARENTH) {
            return
        }

        // 右括号
        if (psiExpressionList.lastChild !is PsiJavaToken ||
                (psiExpressionList.lastChild as PsiJavaToken).tokenType != JavaTokenType.RPARENTH) {
            return
        }
        tryRegisterProblem(expression)
    }

    override fun visitThrowStatement(statement: PsiThrowStatement) {
        super.visitThrowStatement(statement)
        val exception: PsiExpression? = statement.exception
        if (exception?.firstChild == null) {
            return
        }
        // 确保是throw new表达
        if (exception.firstChild is PsiKeyword && PsiKeyword.NEW == exception.firstChild.text) {
            val lastChild: PsiElement = exception.lastChild as? PsiExpressionList ?: return
            val context: PsiElement = lastChild.children.iterator().next() as? PsiLiteralExpression ?: return
            // 只处理直接丢出内容
            if (InspectionUtils.isErrorCode(context.text)) {
                tryRegisterProblem(context)
            }
        }
    }

    private fun tryRegisterProblem(psiElement: PsiElement) {
        val errorCode: String = InspectionUtils.getErrorCode(psiElement) ?: return
        if (translateProvider.missing(errorCode)) {
            problemHolder.registerProblem(psiElement, "$errorCode missing translate",
                    InspectionQuickFix.instance)
        }
    }
}