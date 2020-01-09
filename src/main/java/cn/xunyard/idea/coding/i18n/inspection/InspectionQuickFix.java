package cn.xunyard.idea.coding.i18n.inspection;

import cn.xunyard.idea.coding.i18n.dialog.AddLanguageDialog;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-09
 */
class InspectionQuickFix implements LocalQuickFix {
    private static final Logger log = Logger.getInstance(InspectionQuickFix.class);
    private static final InspectionQuickFix instance = new InspectionQuickFix();

    public static InspectionQuickFix getInstance() {
        return instance;
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @NotNull
    @Override
    public String getFamilyName() {
        return "QuickFixBundle.message()";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
        try {
            PsiLiteralExpression expression = (PsiLiteralExpression)descriptor.getPsiElement();
            PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
            AddLanguageDialog addLanguageDialog = new AddLanguageDialog();
            addLanguageDialog.pack();
            addLanguageDialog.setVisible(true);


            PsiLiteralExpression replace = (PsiLiteralExpression)factory.createExpressionFromText("\"转换翻译\"", expression.getContext());
            expression.replace(replace);
        } catch (Exception e) {
            log.error(e);
        }
    }
}
