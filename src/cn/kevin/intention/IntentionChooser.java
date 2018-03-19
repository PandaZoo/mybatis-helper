package cn.kevin.intention;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public interface IntentionChooser {

    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file);

}
