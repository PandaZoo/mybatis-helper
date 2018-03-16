package cn.kevin.alias;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.xml.XmlAttributeValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

/**
 * @author yongkang.zhang
 */
public class AliasClassReference extends PsiReferenceBase<XmlAttributeValue> {

  private Function<AliasDesc, String> function = AliasDesc::getAlias;

  public AliasClassReference(@NotNull XmlAttributeValue element) {
    super(element, true);
  }

  @Nullable @Override
  public PsiElement resolve() {
    XmlAttributeValue attributeValue = getElement();
    return AliasFacade.getInstance(attributeValue.getProject()).findPsiClass(attributeValue, Objects.requireNonNull(attributeValue.getValue())).orNull();
  }

  @NotNull @Override
  public Object[] getVariants() {
    AliasFacade aliasFacade = AliasFacade.getInstance(getElement().getProject());
    Collection<String> result = Collections2.transform(aliasFacade.getAliasDescs(getElement()), function);
    return result.toArray(new String[result.size()]);
  }

}
