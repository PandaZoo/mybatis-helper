package cn.kevin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface Beans extends DomElement {

    @NotNull
    @SubTagList("bean")
    public List<Bean> getBeans();

}
