package cn.kevin.dom.model;

import cn.kevin.dom.converter.AliasConverter;
import cn.kevin.dom.converter.DaoMethodConverter;
import cn.kevin.dom.converter.ParameterMapConverter;
import com.intellij.psi.PsiClass;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface GroupTwo extends GroupOne, IdDomElement {

    @SubTagList("bind")
    public List<Bind> getBinds();

    @NotNull
    @Attribute("parameterMap")
    @Convert(ParameterMapConverter.class)
    public GenericAttributeValue<XmlTag> getParameterMap();

    @Attribute("id")
    @Convert(DaoMethodConverter.class)
    public GenericAttributeValue<String> getId();

    @NotNull
    @Attribute("parameterType")
    @Convert(AliasConverter.class)
    public GenericAttributeValue<PsiClass> getParameterType();
}
