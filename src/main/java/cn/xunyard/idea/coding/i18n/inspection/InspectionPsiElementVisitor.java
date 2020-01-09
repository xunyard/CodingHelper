package cn.xunyard.idea.coding.i18n.inspection;

import cn.xunyard.idea.coding.util.AssertUtils;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.*;
import lombok.RequiredArgsConstructor;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-09
 */
@RequiredArgsConstructor
public final class InspectionPsiElementVisitor extends JavaElementVisitor {
    private static final Logger log = Logger.getInstance(InspectionPsiElementVisitor.class);

    private final ProblemsHolder problemHolder;

    private boolean isErrorCode(String str) {
        if (!str.startsWith("\"") || !str.endsWith("\"")) {
            return false;
        }

        boolean containsDot = false;
        char[] chars = str.toCharArray();
        for (int i = 1; i < chars.length - 2; i++) {
            char c = chars[i];

            if (c == '.') {
                containsDot = true;
                continue;
            }

            if (c > 'a' - 1 && c < 'z' + 1) {
                continue;
            }

            if (c > 'A' - 1 && c < 'Z' + 1) {
                continue;
            }

            return false;
        }

        return containsDot;
    }

    @Override
    public void visitLiteralExpression(PsiLiteralExpression expression) {
        super.visitLiteralExpression(expression);
        try {
            // 错误码应该包含符号'.'
            String text = expression.getText();
            if (AssertUtils.isEmpty(text) || !isErrorCode(text)) {
                return;
            }

            // 仅处理方法中出现的字符串
            if (!(expression.getContext() instanceof PsiExpressionList) ||
                    !(expression.getContext().getContext() instanceof PsiMethodCallExpression)) {
                return;
            }

            PsiExpressionList psiExpressionList = (PsiExpressionList) expression.getContext();

            // 左括号
            if (!(psiExpressionList.getFirstChild() instanceof PsiJavaToken) ||
                    !(((PsiJavaToken) psiExpressionList.getFirstChild()).getTokenType() == JavaTokenType.LPARENTH)) {
                return;
            }

            // 右括号
            if (!(psiExpressionList.getLastChild() instanceof PsiJavaToken) ||
                    !(((PsiJavaToken) psiExpressionList.getLastChild()).getTokenType() == JavaTokenType.RPARENTH)) {
                return;
            }

            System.out.println(expression.getText());
            problemHolder.registerProblem(expression, "This is error code.", InspectionQuickFix.getInstance());
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void visitThrowStatement(PsiThrowStatement statement) {
        super.visitThrowStatement(statement);
        PsiExpression exception = statement.getException();
        if (exception == null || exception.getFirstChild() == null) {
            return;
        }
        // 确保是throw new表达
        if (exception.getFirstChild() instanceof PsiKeyword && PsiKeyword.NEW.equals(exception.getFirstChild().getText())) {
            PsiElement lastChild = exception.getLastChild();

            if (!(lastChild instanceof PsiExpressionList)) {
                return;
            }

            PsiElement context = lastChild.getChildren()[1];
            // 只处理直接丢出内容
            if (!(context instanceof PsiLiteralExpression)) {
                return;
            }

            String error = context.getText();
            System.out.println(error);
        }
    }

    private void check(String errorCode) {

    }
}
