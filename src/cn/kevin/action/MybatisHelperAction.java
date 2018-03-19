package cn.kevin.action;

import cn.kevin.dialog.MybatisHelperDialog;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;

import java.util.Objects;

/**
 * mybatis helper
 * created by yongkang.zhang
 * added at 2018/1/31
 */
public class MybatisHelperAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        // 获取action的信息
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        assert editor != null;
        assert project != null;
        PsiFile currentEditorFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        FileType fileType = currentEditorFile.getFileType();
        if (!Objects.equals(fileType.getName(), "JAVA")) {
            HintManager.getInstance().showErrorHint(editor, "非JAVA文件不可以调用");
        }

        MybatisHelperDialog dialog = new MybatisHelperDialog(project);
        dialog.show();
    }


}
