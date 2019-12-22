package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.DocBuildingContext;
import cn.xunyard.idea.doc.logic.ServiceResolver;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaType;
import com.thoughtworks.qdox.model.impl.DefaultJavaType;
import lombok.Getter;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-22
 */
@Getter
public class PropertyDescriber {

    private final JavaField javaField;
    private ApiModelProperty apiModelProperty;
    private BeanDescriber extendBean;

    public static PropertyDescriber fromJavaField(JavaField javaField, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        return new PropertyDescriber(javaField, javaClass, docBuildingContext);
    }

    private void resolveExtend(JavaType javaType, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        if (AssertUtils.isBasicType(javaType)) {
            return;
        }

        // 枚举跳过，防止重复解析
        if (javaType instanceof DefaultJavaType && ((DefaultJavaType)javaType).isEnum()) {
            return;
        }

        extendBean = BeanDescriberManager.getInstance().load(javaType, javaClass, docBuildingContext);
    }

    private PropertyDescriber(JavaField javaField, JavaClass javaClass, DocBuildingContext docBuildingContext) {
        this.javaField = javaField;
        resolveExtend(javaField.getType(), javaClass, docBuildingContext);

        for (JavaAnnotation annotation : javaField.getAnnotations()) {
            apiModelProperty = ApiModelProperty.fromAnnotation(annotation);

            if (apiModelProperty != null) {
                return;
            }
        }

        String comment = javaField.getComment();

        if (!AssertUtils.isEmpty(comment)) {
            apiModelProperty = new ApiModelProperty(ObjectUtils.smoothStr(comment), null, false);
            return;
        }

        ServiceResolver.setResolveFail();
        apiModelProperty = new ApiModelProperty(null, null, false);

        if (docBuildingContext.getLogUnresolved()) {
            DocLogger.error("[注释缺失] 属性: " + javaClass.getName() + "#" + javaField.getName() + " 未找到有效注解");
        }
    }
}
