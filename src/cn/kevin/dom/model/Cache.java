package cn.kevin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface Cache extends DomElement {

    @SubTagList("property")
    public List<Property> getProperties();

}
