package cn.kevin.dom.model;

import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.GenericAttributeValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public interface Package extends DomElement {

    @NotNull
    @Attribute("name")
    GenericAttributeValue<String> getName();

}
