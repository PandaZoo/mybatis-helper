package cn.kevin.provider;

import cn.kevin.annotation.Annotation;
import cn.kevin.dom.model.Mapper;
import cn.kevin.util.Icons;
import cn.kevin.util.JavaUtils;
import cn.kevin.util.MapperUtils;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

/**
 * created by yongkang.zhang
 * added at 2018/3/16
 */
public class InjectionLineMarkerProvider extends RelatedItemLineMarkerProvider {

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        if (!(element instanceof PsiField)) {
            return;
        }
        PsiField field = (PsiField) element;
        if (!isTargetField(field)) {
            return;
        }

        PsiType type = field.getType();
        if (!(type instanceof PsiClassReferenceType)) {
            return;
        }

        Optional<PsiClass> clazz = JavaUtils.findClazz(element.getProject(), type.getCanonicalText());
        if (!clazz.isPresent()) {
            return;
        }

        PsiClass psiClass = clazz.get();
        Optional<Mapper> mapperOptional = MapperUtils.findFirstMapper(element.getProject(), psiClass);
        if (!mapperOptional.isPresent()) {
            return;
        }

        NavigationGutterIconBuilder<PsiElement> builder =
                NavigationGutterIconBuilder.create(Icons.SPRING_INJECTION_ICON)
                        .setAlignment(GutterIconRenderer.Alignment.CENTER)
                        .setTarget(psiClass)
                        .setTooltipText("Data access object found - " + psiClass.getQualifiedName());
        result.add(builder.createLineMarkerInfo(field.getNameIdentifier()));
    }


    private boolean isTargetField(PsiField field) {
        if (JavaUtils.isAnnotationPresent(field, Annotation.AUTOWIRED)) {
            return true;
        }

        Optional<PsiAnnotation> psiAnnotation = JavaUtils.getPsiAnnotation(field, Annotation.RESOURCE);
        if (psiAnnotation.isPresent()) {
            PsiAnnotationMemberValue nameValue = psiAnnotation.get().findAttributeValue("name");
            assert nameValue != null;
            String name = nameValue.getText().replaceAll("\"", "");
            return StringUtils.isBlank(name) || name.equalsIgnoreCase(field.getName());
        }
        return false;
    }
}
