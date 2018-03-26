package cn.kevin.reference;

import cn.kevin.dom.model.Mapper;
import cn.kevin.util.MapperUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.xml.XmlElement;
import com.intellij.util.Processor;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 *
 * created by yongkang.zhang
 * added at 2018/3/26
 */
public class MapperMethodImplementationSearch extends QueryExecutorBase<XmlElement, PsiElement> {

    @Override
    public void processQuery(@NotNull PsiElement psiElement, @NotNull Processor<XmlElement> consumer) {
        Processor<DomElement> domElementProcessor = domElement -> consumer.process(domElement.getXmlElement());

        if (psiElement instanceof PsiClass) {
            ApplicationManager.getApplication()
                    .runReadAction(() -> {
                        Optional<Mapper> optionalMapper = MapperUtils.findFirstMapper(psiElement.getProject(), (PsiClass) psiElement);
                        optionalMapper.ifPresent(domElementProcessor::process);
                    });
        } else if (psiElement instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) psiElement;
            ApplicationManager.getApplication()
                    .runReadAction(() -> {
                        Optional<Mapper> optionalMapper = MapperUtils.findFirstMapper(psiElement.getProject(), (PsiMethod) psiElement);
                        optionalMapper.ifPresent(m -> {
                            m.getDaoElements()
                                    .stream()
                                    .filter(e -> Objects.equals(e.getId().getRawText(),
                                             method.getName()))
                                    .forEach(domElementProcessor::process);
                        });
                    });
        }
    }


}
