package cn.kevin.xml;

import com.intellij.util.xml.*;

import java.util.List;

/**
 * 元素 和 属性
 * created by yongkang.zhang
 * added at 2018/2/7
 */
public interface ElementAndAttributes {

    interface Root extends com.intellij.util.xml.DomElement {
        @SubTagList(value = "resultMap")
        List<ResultMap> getResultMaps();

        @SubTagList(value = "sql")
        List<Sql> getSqls();

        @SubTagList(value = "insert")
        List<Insert> getInsertTags();
    }

    interface ResultMap extends com.intellij.util.xml.DomElement {
        @Attribute("type")
        GenericAttributeValue<String> getType();

        @SubTagList("result")
        List<Result> getResults();
    }

    interface Result extends com.intellij.util.xml.DomElement {
        @Attribute("column")
        GenericAttributeValue<String> getColumn();

        @Attribute("jdbcType")
        GenericAttributeValue<String> getJdbcType();

        @Attribute("property")
        GenericAttributeValue<String> getProperty();
    }

    interface Sql extends com.intellij.util.xml.DomElement {
        @Attribute("id")
        GenericAttributeValue<String> getId();

        @SubTag(value = "where")
        Where getWhere();

        @SubTag(value = "set")
        SetTag getSetTag();
    }

    interface SetTag extends com.intellij.util.xml.DomElement {
        @SubTagList(value = "if")
        List<IfTag> getIfTags();
    }

    interface Where extends com.intellij.util.xml.DomElement {
        @SubTagList(value = "if")
        List<IfTag> getIfTags();
    }

    interface IfTag extends com.intellij.util.xml.DomElement {
        @Attribute(value = "test")
        GenericAttributeValue<String> getTestCondition();

        @TagValue
        String getValue();
    }

    interface Insert extends com.intellij.util.xml.DomElement {
        @TagValue
        String getValue();

        @TagValue
        void setTagValue(String s);
    }

}
