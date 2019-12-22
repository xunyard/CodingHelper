package cn.xunyard.idea.doc.logic.describer;

import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;


/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 * @see io.swagger.annotations.ApiModel
 */
@Getter
public class ApiModel {
    private static final String NAME = "io.swagger.annotations.ApiModel";

    private final String value;

    ApiModel(String value) {
        this.value = value;
    }

    static ApiModel fromAnnotation(JavaAnnotation annotation) {
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
        return new ApiModel(value);
    }
}