package cn.kevin.xml;

import cn.kevin.util.DomUtils;
import com.intellij.openapi.module.Module;
import com.intellij.psi.xml.XmlFile;
import com.intellij.util.xml.DomFileDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 对应mapper文件的描述，把xml和ElementAndAttributes.Root关联起来
 * created by yongkang.zhang
 * added at 2018/2/7
 */
public class MybatisDomFileDescription extends DomFileDescription<ElementAndAttributes.Root> {

    public MybatisDomFileDescription() {
        super(ElementAndAttributes.Root.class, "mapper");
    }

    @Override
    public boolean isMyFile(@NotNull XmlFile file, @Nullable Module module) {
        return DomUtils.isMybatisFile(file);
    }
}
