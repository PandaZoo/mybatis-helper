package cn.kevin.inspection;

import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.highlighting.BasicDomElementsInspection;
import com.intellij.util.xml.highlighting.DomElementAnnotationHolder;
import com.intellij.util.xml.highlighting.DomHighlightingHelper;

/**
 * @author yongkang.zhang
 */
public class MapperXmlInspection extends BasicDomElementsInspection<DomElement> {

    public MapperXmlInspection() {
        super(DomElement.class);
    }

    @Override
    protected void checkDomElement(DomElement element, DomElementAnnotationHolder holder, DomHighlightingHelper helper) {
        super.checkDomElement(element, holder, helper);
    }

}
