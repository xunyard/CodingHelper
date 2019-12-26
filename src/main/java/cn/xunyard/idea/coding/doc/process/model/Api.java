package cn.xunyard.idea.coding.doc.process.model;

import cn.xunyard.idea.coding.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 * @see io.swagger.annotations.Api
 */
@Getter
@RequiredArgsConstructor
public class Api {
    private static final String NAME = "io.swagger.annotations.Api";

    private final String value;
    private final String note;

    @Nullable
    public static Api fromJavaClass(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            Api api = fromAnnotation(annotation);

            if (api != null) {
                return api;
            }
        }

        String comment = javaClass.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            return new Api(comment, null);
        }

        return null;
    }

    private static Api fromAnnotation(JavaAnnotation annotation) {
        if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
            return null;
        }

        // 字符串可能被包了引号(")，需要处理下
        AnnotationValue annotationValue = annotation.getProperty("value");
        if (annotationValue == null) {
            return null;
        }

        String value = annotationValue.toString();
        if (value.startsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        return new Api(value, null);
    }
}