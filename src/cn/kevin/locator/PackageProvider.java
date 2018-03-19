package cn.kevin.locator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiPackage;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author yongkang.zhang
 */
public abstract class PackageProvider {

    @NotNull
    public abstract Set<PsiPackage> getPackages(@NotNull Project project);

}