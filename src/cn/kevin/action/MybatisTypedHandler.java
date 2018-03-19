package cn.kevin.action;

import cn.kevin.util.DomUtils;
import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.completion.CodeCompletionHandlerBase;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.sql.psi.SqlFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public class MybatisTypedHandler extends TypedHandlerDelegate {

    /**
     * replace deprecated method
     *
     * @param project project
     * @param editor  editor
     */
    private static void autoPopupParameter(final Project project, final Editor editor) {
        AutoPopupController.runTransactionWithEverythingCommitted(project, () -> {
            if (PsiDocumentManager.getInstance(project).isCommitted(editor.getDocument())) {
                new CodeCompletionHandlerBase(CompletionType.BASIC).invokeCompletion(project, editor, 1);
            }
        });
    /*CompletionAutoPopupHandler.runLaterWithCommitted(project, editor.getDocument(), () -> {
      if (PsiDocumentManager.getInstance(project).isCommitted(editor.getDocument())) {
        new CodeCompletionHandlerBase(CompletionType.BASIC).invokeCompletion(project, editor, 1);
      }
    });*/
    }

    @Override
    public Result checkAutoPopup(char charTyped, final Project project, final Editor editor, PsiFile file) {
        if (charTyped == '.' && DomUtils.isMybatisFile(file)) {
            autoPopupParameter(project, editor);
            return Result.STOP;
        }
        return super.checkAutoPopup(charTyped, project, editor, file);
    }

    @Override
    public Result charTyped(char c, final Project project, @NotNull final Editor editor, @NotNull PsiFile file) {
        int index = editor.getCaretModel().getOffset() - 2;
        PsiFile topLevelFile = InjectedLanguageUtil.getTopLevelFile(file);
        boolean parameterCase = c == '{' &&
                index >= 0 &&
                editor.getDocument().getText().charAt(index) == '#' &&
                file instanceof SqlFile &&
                DomUtils.isMybatisFile(topLevelFile);
        if (parameterCase) {
            autoPopupParameter(project, editor);
            return Result.STOP;
        }
        return super.charTyped(c, project, editor, file);
    }

}