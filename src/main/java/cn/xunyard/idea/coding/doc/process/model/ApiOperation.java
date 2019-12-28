package cn.xunyard.idea.coding.doc.process.model;

import cn.xunyard.idea.coding.util.AssertUtils;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 * @see io.swagger.annotations.ApiOperation
 */
@Getter
@RequiredArgsConstructor
public class ApiOperation {
    private static final String NAME = "io.swagger.annotations.ApiOperation";

    private final String value;
    private final String note;

    @Nullable
    public static ApiOperation fromJavaMethod(JavaMethod javaMethod) {
        for (JavaAnnotation annotation : javaMethod.getAnnotations()) {
            ApiOperation apiOperation = ApiOperation.fromAnnotation(annotation);

            if (apiOperation != null) {
                return apiOperation;
            }
        }

        String comment = javaMethod.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            return new ApiOperation(ObjectUtils.smoothStr(comment), null);
        }

        return null;
    }

    private static ApiOperation fromAnnotation(JavaAnnotation annotation) {
        if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
            return null;
        }

        AnnotationValue annotationValue = annotation.getProperty("value");
        if (annotationValue == null) {
            return null;
        }

        String value = ObjectUtils.removeQuotation(annotationValue.toString());

        return new ApiOperation(value,
                ObjectUtils.takeIfNonNull(annotation.getProperty("notes"), it -> ObjectUtils.smoothStr(it.toString())));
    }
}
