package cn.kevin.service;

import cn.kevin.annotation.Annotation;
import cn.kevin.util.JavaUtils;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public class AnnotationService {

    private Project project;

    public AnnotationService(Project project) {
        this.project = project;
    }

    public static AnnotationService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, AnnotationService.class);
    }

    /**
     *
     * @param parameter PsiModifierListOwner which has a list of modifiers and annotations
     * @param annotation specific annotation
     */
    public void addAnnotation(@NotNull PsiModifierListOwner parameter, @NotNull Annotation annotation) {
        // check annotation is present or no modifier
        PsiModifierList modifierList = parameter.getModifierList();
        if (JavaUtils.isAnnotationPresent(parameter, annotation) || null == modifierList) {
            return;
        }

        // add anotation
        JavaService.getInstance(parameter.getProject()).importClazz((PsiJavaFile) parameter.getContainingFile(), annotation.getQualifiedName());
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiAnnotation psiAnnotation = elementFactory.createAnnotationFromText(annotation.toString(), parameter);
        modifierList.add(psiAnnotation);

        // format
        JavaCodeStyleManager.getInstance(project).shortenClassReferences(psiAnnotation.getParent());
    }

    public void addAnnotationWithParameterNameForMethodParameters(@NotNull PsiMethod method) {
        PsiParameterList parameterList = method.getParameterList();
        if (null == parameterList) {
            return;
        }
        PsiParameter[] parameters = parameterList.getParameters();
        for (PsiParameter param : parameters) {
            addAnnotationWithParameterName(param);
        }
    }

    public void addAnnotationWithParameterName(@NotNull PsiParameter parameter) {
        String name = parameter.getName();
        if (null != name) {
            AnnotationService.getInstance(parameter.getProject()).addAnnotation(parameter, Annotation.PARAM.withValue(new Annotation.StringValue(name)));
        }
    }
}
