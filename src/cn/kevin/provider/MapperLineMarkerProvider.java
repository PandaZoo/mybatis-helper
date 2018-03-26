package cn.kevin.provider;

import cn.kevin.dom.model.IdDomElement;
import cn.kevin.service.JavaService;
import cn.kevin.util.Icons;
import cn.kevin.util.JavaUtils;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.CommonProcessors;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        if (element instanceof PsiNameIdentifierOwner && JavaUtils.isElementWithinInterface(element)) {
            // Collect processor. pair mapper and java and add psiMethod or pisClass
            CommonProcessors.CollectProcessor<IdDomElement> processor = new CommonProcessors.CollectProcessor<>();
            JavaService.getInstance(element.getProject()).process(element, processor);
            Collection<IdDomElement> results = processor.getResults();
            if (!results.isEmpty()) {
                // add to LineMarkerInfo
                NavigationGutterIconBuilder<PsiElement> builder =
                        NavigationGutterIconBuilder.create(Icons.MAPPER_LINE_MARKER_ICON)
                                .setAlignment(GutterIconRenderer.Alignment.CENTER)
                                .setTargets(results.stream().map(xmlTagSuppllier).collect(Collectors.toList()))
                                .setTooltipText("Navigation to target in mapper xml - kev");
                result.add(builder.createLineMarkerInfo(Objects.requireNonNull(((PsiNameIdentifierOwner) element).getNameIdentifier())));
            }
        }

    }
}
