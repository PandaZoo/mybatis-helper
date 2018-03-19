package cn.kevin.provider;

import cn.kevin.dom.model.*;
import cn.kevin.util.Icons;
import cn.kevin.util.JavaUtils;
import cn.kevin.util.MapperUtils;
import com.google.common.collect.ImmutableList;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;
import java.util.Optional;

/**
 * statement line markder
 * created by yongkang.zhang
 * added at 2018/3/16
 */
public class StatementLineMarkerProvider extends SimpleLineMarkerProvider<XmlTag, PsiMethod> {

    private static final ImmutableList<Class<? extends GroupTwo>> TARGET_TYPES = ImmutableList.of(
            Select.class,
            Update.class,
            Insert.class,
            Delete.class
    );

    @Override
    public boolean isTheElement(@NotNull PsiElement element) {
        return element instanceof XmlTag
                && MapperUtils.isElementWithinMybatisFile(element)
                && isTargetType(element);
    }

    @NotNull
    @Override
    public Optional<PsiMethod> apply(@NotNull XmlTag from) {
        DomElement domElement = DomUtil.getDomElement(from);
        if (domElement == null) {
            return Optional.empty();
        }

        return JavaUtils.findMethod(from.getProject(), (IdDomElement) domElement);
    }

    private boolean isTargetType(PsiElement element) {
        DomElement domElement = DomUtil.getDomElement(element);
        for (Class<?> clazz : TARGET_TYPES) {
            if (clazz.isInstance(domElement)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    @Override
    public Navigatable getNavigatable(@NotNull XmlTag from, @NotNull PsiMethod target) {
        return (Navigatable) target.getNavigationElement();
    }

    @NotNull
    @Override
    public String getTooltip(@NotNull XmlTag from, @NotNull PsiMethod target) {
        return "Data access object found - " + Objects.requireNonNull(target.getContainingClass()).getQualifiedName();
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return Icons.STATEMENT_LINE_MARKER_ICON;
    }
}
