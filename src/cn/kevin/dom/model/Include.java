package cn.kevin.dom.model;

import cn.kevin.dom.converter.SqlConverter;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;

/**
 * @author yongkang.zhang
 */
public interface Include extends DomElement {

    @Attribute("refid")
    @Convert(SqlConverter.class)
    public GenericAttributeValue<XmlTag> getRefId();

}
