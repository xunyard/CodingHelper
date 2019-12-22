package cn.xunyard.idea.doc.logic.describer;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 * @see io.swagger.annotations.Api
 */
@Getter
public class Api {
    private static final String NAME = "io.swagger.annotations.Api";

    private final String value;
    private final String note;

    Api(String value, String note) {
        this.value = value;
        this.note = note;
    }

    static Api fromAnnotation(JavaAnnotation annotation) {
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