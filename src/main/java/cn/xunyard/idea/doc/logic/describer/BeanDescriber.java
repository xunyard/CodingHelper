package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
@Getter
public class BeanDescriber {

    private ApiModel apiModel;
    private Map<String, PropertyDescriber> propertyMap;

    public static BeanDescriber fromJavaClass(JavaClass javaClass, DocBuildingContext docBuildingContext) {
        return new BeanDescriber(javaClass, docBuildingContext);
    }

    private BeanDescriber(JavaClass javaClass, DocBuildingContext docBuildingContext) {
        resolveRoot(javaClass);
        propertyMap = new HashMap<>();
        for (JavaField field : javaClass.getFields()) {
            PropertyDescriber propertyDescriber = resolveField(field, javaClass, docBuildingContext);

            if (propertyDescriber != null) {
                propertyMap.put(field.getName(), propertyDescriber);
            }
        }
    }

    private void resolveRoot(JavaClass javaClass) {
        List<JavaAnnotation> annotationList = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            apiModel = ApiModel.fromAnnotation(annotation);

            if (apiModel != null) {
                return;
            }
        }

        if (apiModel == null) {
            String comment = javaClass.getComment();

            if (AssertUtils.isEmpty(comment)) {
                apiModel = new ApiModel(javaClass.getName());
                return;
            }
        }

//        ServiceResolver.setResolveFail();
        DocLogger.warn("对象: " + javaClass.getName() + " 未找到有效注释");
    }

    private PropertyDescriber resolveField(JavaField javaField, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        if (javaField.getModifiers().contains("final") ||
                javaField.getModifiers().contains("static")) {
            return null;
        }

        return PropertyDescriber.fromJavaField(javaField, javaClass, docBuildingContext);
    }
}
