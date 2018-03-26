package cn.kevin.reference;

import cn.kevin.dom.model.IdDomElement;
import cn.kevin.dom.model.Mapper;
import cn.kevin.util.JavaUtils;
import cn.kevin.util.MapperUtils;
import com.intellij.codeInsight.generation.OverrideImplementUtil;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.lang.ContextAwareActionHandler;
import com.intellij.lang.LanguageCodeInsightActionHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * search mapper interface implementations
 * created by yongkang.zhang
 * added at 2018/3/26
 */
public class MapperMethodImplementationSearchHandler implements ContextAwareActionHandler, LanguageCodeInsightActionHandler {

    @Override
    public boolean isValidFor(Editor editor, PsiFile file) {
        // is JavaFile && is Interfacce && is with Mapper xml file
        return file instanceof PsiJavaFile && JavaUtils.isElementWithinInterface(file)
                && MapperUtils.findFirstMapper(file.getProject(), file.getClass().getSimpleName()).isPresent();
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        PsiClass aClass = getMapperFileElement(project, editor, file);
        if (aClass == null) {
            return;
        }
        OverrideImplementUtil.chooseAndImplementMethods(project, editor, aClass);
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }

    @Override
    public boolean isAvailableForQuickList(@NotNull Editor editor, @NotNull PsiFile file, @NotNull DataContext dataContext) {
        PsiClass aClass = getMapperFileElement(file.getProject(), editor, file);
        return aClass != null && !OverrideImplementUtil.getMethodSignaturesToImplement(aClass).isEmpty();
    }

    private PsiClass getMapperFileElement(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        // find current method
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = file.findElementAt(offset);
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        if (method == null) {
            return null;
        }

        // find mapper xml file
        Optional<Mapper> optionalMapper = MapperUtils.findFirstMapper(project, file.getClass().getSimpleName());
        if (!optionalMapper.isPresent()) {
            return null;
        }

        Optional<IdDomElement> optionalIdDomElement = optionalMapper.get()
                .getDaoElements()
                .stream()
                .filter(e -> Objects.equals(e.getId().getRawText(), method.getName()))
                .findFirst();
        if (!optionalIdDomElement.isPresent()) {
            return null;
        }
        return (PsiClass) optionalIdDomElement.get().getXmlElement();
    }

}
