package cn.kevin.dom.model;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.SubTagList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author yongkang.zhang
 */
public interface TypeAliases extends DomElement {

    @NotNull
    @SubTagList("typeAlias")
    public List<TypeAlias> getTypeAlias();

    @NotNull
    @SubTagList("package")
    public List<Package> getPackages();

}
