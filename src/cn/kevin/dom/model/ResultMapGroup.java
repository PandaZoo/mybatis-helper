package cn.kevin.dom.model;

import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import com.seventh7.mybatis.dom.converter.ResultMapConverter;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public interface ResultMapGroup extends DomElement {

  @NotNull
  @Attribute("resultMap")
  @Convert(ResultMapConverter.class)
  public GenericAttributeValue<XmlTag> getResultMap();
}
