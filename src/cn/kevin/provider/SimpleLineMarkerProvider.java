package cn.kevin.provider;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * created by yongkang.zhang
 * added at 2018/3/16
 */
public abstract class SimpleLineMarkerProvider<F extends PsiElement, T> extends MarkerProviderAdaptor {
    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> collection) {
    }

    @Nullable
    @Override
    @SuppressWarnings(value = "unchecked")
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        if (!isTheElement(psiElement)) {
            return null;
        }

        Optional<T> processResult = apply((F) psiElement);
        return processResult.<LineMarkerInfo>map(t -> new LineMarkerInfo<>(
                (F) psiElement,
                psiElement.getTextRange(),
                getIcon(),
                Pass.UPDATE_ALL,
                getTooltipProvider(t),
                getNavigationHandler(t),
                GutterIconRenderer.Alignment.CENTER
        )).orElse(null);
    }


    private Function<F, String> getTooltipProvider(final T target) {
        return f -> getTooltip(f, target);
    }

    private GutterIconNavigationHandler<F> getNavigationHandler(final T target) {
        return (mouseEvent, from) -> getNavigatable(from, target).navigate(true);
    }

    public abstract boolean isTheElement(@NotNull PsiElement element);

    @NotNull
    public abstract Optional<T> apply(@NotNull F from);

    @NotNull
    public abstract Navigatable getNavigatable(@NotNull F from, @NotNull T target);

    @NotNull
    public abstract String getTooltip(@NotNull F from, @NotNull T target);

    @NotNull
    public abstract Icon getIcon();
}
