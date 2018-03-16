package cn.kevin.alias;

import cn.kevin.util.MapperUtils;
import com.google.common.collect.Sets;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author yongkang.zhang
 */
public class SingleAliasResolver extends AliasResolver{

  public SingleAliasResolver(Project project) {
    super(project);
  }

  @NotNull @Override
  public Set<AliasDesc> getClassAliasDescriptions(@Nullable PsiElement element) {
    final Set<AliasDesc> result = Sets.newHashSet();
    MapperUtils.processConfiguredTypeAliases(project, typeAlias -> {
      addAliasDesc(result, typeAlias.getType().getValue(), typeAlias.getAlias().getStringValue());
      return true;
    });
    return result;
  }

}
