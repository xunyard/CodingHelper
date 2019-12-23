package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.util.AssertUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaType;
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
    private final JavaType javaType;
    private ApiModel apiModel;
    private Map<String, PropertyDescriber> propertyMap;
    private BeanDescriber derive;

    public boolean isBasicType() {
        return AssertUtils.isBasicType(javaType);
    }

    public static BeanDescriber fromJavaClass(JavaClass beanClass, DocBuildingContext docBuildingContext) {
        return new BeanDescriber(beanClass, docBuildingContext);
    }

    public static BeanDescriber fromBasicType(JavaType javaType) {
        return new BeanDescriber(javaType);
    }

    private BeanDescriber(JavaType javaType) {
        this.javaType = javaType;
    }

    private BeanDescriber(JavaClass beanClass, DocBuildingContext docBuildingContext) {
        this.javaType = beanClass;
        resolveRoot(beanClass);
        propertyMap = new HashMap<>();
        for (JavaField field : beanClass.getFields()) {
            PropertyDescriber propertyDescriber = resolveField(field, beanClass, docBuildingContext);

            if (propertyDescriber != null) {
                propertyMap.put(field.getName(), propertyDescriber);
            }
        }

        resolveSuperClass(beanClass, docBuildingContext);
    }

    private void resolveSuperClass(JavaClass beanClass, DocBuildingContext docBuildingContext) {
        JavaType superClass = beanClass.getSuperClass();
        if (superClass == null || AssertUtils.isBasicType(superClass)) {
            return;
        }

        this.derive = BeanDescriberManager.getInstance().load(superClass, beanClass, docBuildingContext);
    }

    private void resolveRoot(JavaClass beanClass) {
        List<JavaAnnotation> annotationList = beanClass.getAnnotations();
        for (JavaAnnotation annotation : annotationList) {
            apiModel = ApiModel.fromAnnotation(annotation);

            if (apiModel != null) {
                return;
            }
        }

        if (apiModel == null) {
            String comment = beanClass.getComment();

            if (AssertUtils.isEmpty(comment)) {
                apiModel = new ApiModel(beanClass.getName());
                return;
            }
        }

//        ServiceResolver.setResolveFail();
        DocLogger.warn("对象: " + beanClass.getName() + " 未找到有效注释");
    }

    private PropertyDescriber resolveField(JavaField javaField, JavaClass beanClass, DocBuildingContext docBuildingContext) {
        if (javaField.getModifiers().contains("final") ||
                javaField.getModifiers().contains("static")) {
            return null;
        }

        return PropertyDescriber.fromJavaField(javaField, beanClass, docBuildingContext);
    }
}
