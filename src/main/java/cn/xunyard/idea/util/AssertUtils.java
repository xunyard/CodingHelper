package cn.xunyard.idea.util;


import com.thoughtworks.qdox.model.JavaType;

import java.util.Collection;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class AssertUtils {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isBasicType(JavaType type) {
        switch (type.toString()) {
            case "java.lang.Boolean":
            case "java.lang.Byte":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.Char":
            case "java.lang.Object":
            case "java.lang.String":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.util.Date":
                return true;
            default:
                return false;
        }
    }
}
