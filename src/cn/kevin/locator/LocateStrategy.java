package cn.kevin.locator;

import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

/**
 * locate strategy
 * @author yongkang.zhang
 */
public abstract class LocateStrategy {

    public abstract boolean apply(@NotNull PsiClass clazz);

}
