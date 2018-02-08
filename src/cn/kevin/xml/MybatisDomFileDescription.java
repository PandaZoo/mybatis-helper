package cn.kevin.xml;

import com.intellij.util.xml.DomFileDescription;

/**
 * 对应mapper文件的描述，把xml和ElementAndAttributes.Root关联起来
 * created by yongkang.zhang
 * added at 2018/2/7
 */
public class MybatisDomFileDescription extends DomFileDescription {

    public MybatisDomFileDescription() {
        super(ElementAndAttributes.Root.class, "mapper");
    }
}
