package cn.kevin.dom.converter;

import cn.kevin.dom.model.IdDomElement;
import cn.kevin.dom.model.Mapper;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author yongkang.zhang
 */
public class ParameterMapConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper,
                                                             ConvertContext context) {
        return mapper.getParameterMaps();
    }

}
