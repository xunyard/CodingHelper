package cn.xunyard.idea.coding.doc.process.model;

import cn.xunyard.idea.coding.util.AssertUtils;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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

    @Nullable
    public static ApiModel fromJavaClass(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            ApiModel apiModel = ApiModel.fromAnnotation(annotation);

            if (apiModel != null) {
                return apiModel;
            }
        }

        String comment = javaClass.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            return new ApiModel(ObjectUtils.smoothStr(comment));
        }

        return null;
    }

    private static ApiModel fromAnnotation(JavaAnnotation annotation) {
        if (!NAME.equals(annotation.getType().getFullyQualifiedName())) {
            return null;
        }

        AnnotationValue annotationValue = annotation.getProperty("value");
        if (annotationValue == null) {
            return null;
        }

        String value = ObjectUtils.removeQuotation(annotationValue.toString());
        return new ApiModel(value);
    }
}