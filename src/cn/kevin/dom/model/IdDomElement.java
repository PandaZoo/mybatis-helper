package cn.kevin.dom.model;

import com.intellij.util.xml.*;

/**
 * @author yongkang.zhang
 */
public interface IdDomElement extends DomElement {

    @Required
    @NameValue
    @Attribute("id")
    GenericAttributeValue<String> getId();

    void setValue(String content);

    @TagValue
    String getValue();

}
