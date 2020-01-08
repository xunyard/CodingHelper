package cn.xunyard.idea.coding.i18n.inspection;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-08
 */
public class ErrorCodeWithoutTranslateInspection extends AbstractBaseJavaLocalInspectionTool {

    private PsiElementVisitor psiElementVisitor;

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if (psiElementVisitor == null) {
            psiElementVisitor = new InspectionPsiElementVisitor();
        }

        return psiElementVisitor;
    }

    private static final class InspectionPsiElementVisitor extends JavaElementVisitor {

        @Override
        public void visitBinaryExpression(PsiBinaryExpression expression) {
            super.visitBinaryExpression(expression);


        }

        @Override
        public void visitStatement(PsiStatement statement) {
            super.visitStatement(statement);
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
                if (!(context instanceof PsiLiteralExpression)) {
                    return;
                }

                String error = context.getText();
                System.out.println(error);
            }
        }

        @Override
        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);
        }
    }
}
