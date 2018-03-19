package cn.kevin.dom.model;

import com.intellij.util.xml.SubTagList;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface GroupThree extends GroupTwo {

    @SubTagList("selectKey")
    public List<SelectKey> getSelectKey();

}
