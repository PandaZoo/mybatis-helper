package cn.kevin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.Required;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface Discriminator extends DomElement {

    @Required
    @SubTagList("case")
    public List<Case> getCases();

}
