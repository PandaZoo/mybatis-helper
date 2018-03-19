package cn.kevin.dom.model;

import cn.kevin.dom.converter.AliasConverter;
import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public interface Select extends GroupTwo, ResultMapGroup {

    @NotNull
    @Attribute("resultType")
    @Convert(AliasConverter.class)
    public GenericAttributeValue<PsiClass> getResultType();
}
