package cn.kevin.dom.converter;

import cn.kevin.dom.model.IdDomElement;
import cn.kevin.dom.model.Mapper;
import cn.kevin.dom.model.ResultMap;
import cn.kevin.util.MapperUtils;
import com.intellij.util.xml.ConvertContext;
import com.intellij.util.xml.DomElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yongkang.zhang
 */
public class ResultMapConverter extends IdBasedTagConverter {

    @NotNull
    @Override
    public Collection<? extends IdDomElement> getComparisons(@Nullable Mapper mapper, ConvertContext context) {
        DomElement invocationElement = context.getInvocationElement();
        if (isContextElementOfResultMap(mapper, invocationElement)) {
            return doFilterResultMapItself(mapper, (ResultMap) invocationElement.getParent());
        } else {
            return mapper.getResultMaps();
        }
    }

    private boolean isContextElementOfResultMap(Mapper mapper, DomElement invocationElement) {
        return MapperUtils.isMapperWithSameNamespace(MapperUtils.getMapper(invocationElement), mapper)
                && invocationElement.getParent() instanceof ResultMap;
    }

    private Collection<? extends IdDomElement> doFilterResultMapItself(Mapper mapper, final ResultMap resultMap) {
        return mapper.getResultMaps()
                .stream()
                .filter(input -> !Objects.equals(MapperUtils.getId(input), MapperUtils.getId(resultMap)))
                .collect(Collectors.toList());
    }

}
