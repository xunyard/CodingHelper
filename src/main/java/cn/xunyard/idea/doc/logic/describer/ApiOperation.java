package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 * @see io.swagger.annotations.ApiOperation
 */
@Getter
public class ApiOperation {
    private static final String NAME = "io.swagger.annotations.ApiOperation";

    private final String value;
    private final String note;

    ApiOperation(String value, String note) {
        this.value = value;
        this.note = note;
    }

    static ApiOperation fromAnnotation(JavaAnnotation annotation) {
        if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
            return null;
        }

        AnnotationValue annotationValue = annotation.getProperty("value");
        if (annotationValue == null) {
            return null;
        }

        String value = annotationValue.toString();
        if (value.startsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        return new ApiOperation(value,
                ObjectUtils.takeIfNonNull(annotation.getProperty("note"), it -> ObjectUtils.smoothStr(it.toString())));
    }
}
