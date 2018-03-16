package cn.kevin.dom.converter;

import cn.kevin.dom.model.Mapper;
import cn.kevin.util.JavaUtils;
import cn.kevin.util.MapperUtils;
import com.intellij.psi.PsiMethod;
import com.intellij.util.xml.ConvertContext;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

/**
 * @author yongkang.zhang
 */
public class DaoMethodConverter extends ConverterAdaptor<PsiMethod> {

  @Nullable @Override
  public PsiMethod fromString(@Nullable @NonNls String id, ConvertContext context) {
    Mapper mapper = MapperUtils.getMapper(context.getInvocationElement());
    return JavaUtils.findMethod(context.getProject(), MapperUtils.getNamespace(mapper), id).orElse(null);
  }

}