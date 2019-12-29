package cn.xunyard.idea.coding.doc.logic.model;

import cn.xunyard.idea.coding.util.AssertUtils;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 * @see io.swagger.annotations.ApiModelProperty
 */
@Getter
@RequiredArgsConstructor
public class ApiModelProperty {
    private static final String NAME = "io.swagger.annotations.ApiModelProperty";

    private final String value;
    private final String note;
    private final Boolean required;

    @Nullable
    public static ApiModelProperty fromJavaField(JavaField javaField) {
        for (JavaAnnotation annotation : javaField.getAnnotations()) {
            ApiModelProperty apiModelProperty = fromAnnotation(annotation);

            if (apiModelProperty != null) {
                return apiModelProperty;
            }
        }

        String comment = javaField.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            return new ApiModelProperty(ObjectUtils.smoothStr(comment), null, false);
        }

        return null;
    }

    private static ApiModelProperty fromAnnotation(JavaAnnotation annotation) {
        if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
            return null;
        }

        AnnotationValue annotationValue = annotation.getProperty("value");
        if (annotationValue == null) {
            return null;
        }

        String value = ObjectUtils.removeQuotation(annotationValue.toString());

        return new ApiModelProperty(value,
                ObjectUtils.takeIfNonNull(annotation.getProperty("notes"), it -> ObjectUtils.removeQuotation(it.toString())),
                ObjectUtils.takeBoolean(annotation.getProperty("required")));
    }
}
