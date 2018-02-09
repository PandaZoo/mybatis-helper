package cn.kevin.factory;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;

/**
 * 转换工厂类
 * created by yongkang.zhang
 * added at 2018/2/8
 */
public class ConvertorFacotry {

    public static String camlToUnderScore(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return null;
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

    public static String javaTypeToJdbcType(String type) {
        if (Strings.isNullOrEmpty(type)) {
            return null;
        }

        if (type.equalsIgnoreCase("string")) {
            return "VARCHAR";
        }

        if (type.equalsIgnoreCase("bigdecimal")) {
            return "DECIMAL";
        }

        if (type.equalsIgnoreCase("integer") || type.equalsIgnoreCase("int")) {
            return "INTEGER";
        }

        if (type.equalsIgnoreCase("long")) {
            return "BIGINT";
        }

        if (type.equalsIgnoreCase("date")) {
            return "TIMESTAMP";
        }

        if (type.equalsIgnoreCase("double")) {
            return "DOUBLE";
        }

        if (type.equalsIgnoreCase("float")) {
            return "DOUBLE";
        }

        throw new RuntimeException("未找到" + type + "对应的类型");
    }


    public static void main(String[] args) {
        System.out.println(camlToUnderScore("woDeTain"));
    }
}
