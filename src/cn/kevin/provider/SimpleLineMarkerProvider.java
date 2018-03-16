package cn.kevin.provider;

import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 *
 * created by yongkang.zhang
 * added at 2018/3/16
 */
public class SimpleLineMarkerProvider<F extends PsiElement, T> extends MarkerProviderAdaptor {
    @Override
    public void collectSlowLineMarkers(@NotNull List<PsiElement> list, @NotNull Collection<LineMarkerInfo> collection) {
        //super.collectSlowLineMarkers(list, collection);
    }

    @Nullable
    @Override
    public LineMarkerInfo getLineMarkerInfo(@NotNull PsiElement psiElement) {
        return super.getLineMarkerInfo(psiElement);
    }
}
