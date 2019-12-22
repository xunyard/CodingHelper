package cn.xunyard.idea.util;

import java.util.function.Function;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 */
public class ObjectUtils {

    public static <T> T firstNonNull(T first, T other) {
        if (first != null) {
            return first;
        }

        if (other == null) {
            throw new IllegalArgumentException("both.argument.is.null");
        }

        return other;
    }

    public static <T, R> R takeIfNonNull(T source, Function<T, R> take) {
        if (source == null) {
            return null;
        }

        return take.apply(source);
    }

    public static boolean takeBoolean(Object obj) {
        return obj != null && Boolean.parseBoolean(obj.toString());
    }

    public static String smoothStr(String s) {
        if (s == null) {
            return null;
        }

        String[] splits = s.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String split : splits) {
            sb.append(split);
        }
        return sb.toString();
    }
}
