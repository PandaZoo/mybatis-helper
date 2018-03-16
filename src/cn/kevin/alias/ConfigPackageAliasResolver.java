package cn.kevin.alias;

import cn.kevin.util.MapperUtils;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author yongkang.zhang
 */
public class ConfigPackageAliasResolver extends PackageAliasResolver{

  public ConfigPackageAliasResolver(Project project) {
    super(project);
  }

  @NotNull @Override
  public Collection<String> getPackages(@Nullable PsiElement element) {
    final ArrayList<String> result = Lists.newArrayList();
    MapperUtils.processConfiguredPackage(project, pkg -> {
      result.add(pkg.getName().getStringValue());
      return true;
    });
    return result;
  }

}
