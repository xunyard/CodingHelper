package cn.xunyard.idea.coding.i18n.logic

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralExpression

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
object InspectionUtils {
    fun isErrorCode(str: String): Boolean {
        if (!str.startsWith("\"") || !str.endsWith("\"")) {
            return false
        }
        var containsDot = false
        val chars = str.toCharArray()
        for (i in 1 until chars.size - 2) {
            val c = chars[i]
            if (c == '.') {
                containsDot = true
                continue
            }
            if (c.toInt() > 'a'.toInt() - 1 && c.toInt() < 'z'.toInt() + 1) {
                continue
            }
            if (c.toInt() > 'A'.toInt() - 1 && c.toInt() < 'Z'.toInt() + 1) {
                continue
            }
            return false
        }
        return containsDot
    }

    fun getErrorCode(psiElement: PsiElement): String? {
        if (psiElement !is PsiLiteralExpression) {
            return null
        }
        val text = psiElement.getText()
        return if (text.length < 3) {
            null
        } else text.substring(1, text.length - 1)
    }
}