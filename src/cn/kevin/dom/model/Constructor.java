package cn.kevin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface Constructor extends DomElement {

  @SubTagList("arg")
  public List<Arg> getArgs();

  @SubTagList("idArg")
  public List<IdArg> getIdArgs();
}
