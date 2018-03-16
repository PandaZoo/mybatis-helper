package cn.kevin.dom.model;

import com.intellij.psi.PsiClass;
import com.intellij.util.xml.Attribute;
import com.intellij.util.xml.Convert;
import com.intellij.util.xml.GenericAttributeValue;
import com.seventh7.mybatis.dom.converter.AliasConverter;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public interface Select extends GroupTwo, ResultMapGroup{

  @NotNull
  @Attribute("resultType")
  @Convert(AliasConverter.class)
  public GenericAttributeValue<PsiClass> getResultType();
}
