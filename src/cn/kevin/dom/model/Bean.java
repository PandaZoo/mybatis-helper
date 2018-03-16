package cn.kevin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface Bean extends DomElement {

  @NotNull
  @SubTagList("property")
  public List<BeanProperty> getBeanProperties();

}
