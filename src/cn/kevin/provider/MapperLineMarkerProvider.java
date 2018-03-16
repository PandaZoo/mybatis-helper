package cn.kevin.provider;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomElement;
import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;

/**
 * mapper line marker provider
 * created by yongkang.zhang
 * added at 2018/3/16
 */
public class MapperLineMarkerProvider extends RelatedItemLineMarkerProvider {

    private static final Function<DomElement, XmlTag> xmlTagSuppllier =
            DomElement::getXmlTag;

    @Override
    protected void collectNavigationMarkers(@NotNull PsiElement element, @NotNull Collection<? super RelatedItemLineMarkerInfo> result) {
        //super.collectNavigationMarkers(elements, result);
        if (element instanceof PsiNameIdentifierOwner && )

    }
}
