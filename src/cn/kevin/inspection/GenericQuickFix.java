package cn.kevin.inspection;

import com.intellij.codeInspection.LocalQuickFix;
import org.jetbrains.annotations.NotNull;

/**
 * @author yongkang.zhang
 */
public abstract class GenericQuickFix implements LocalQuickFix {

    @NotNull
    @Override
    public String getFamilyName() {
        return getName();
    }

}
