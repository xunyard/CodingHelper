package cn.xunyard.idea.doc.process;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.ServiceResolver;
import cn.xunyard.idea.doc.process.describer.ClassDescriber;
import cn.xunyard.idea.doc.process.describer.FieldDescriber;
import cn.xunyard.idea.doc.process.model.ApiModelProperty;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaParameterizedType;
import com.thoughtworks.qdox.model.JavaType;
import com.thoughtworks.qdox.model.impl.DefaultJavaParameterizedType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-24
 */
@RequiredArgsConstructor
public class ClassDescriberMaker {

    private final Map<String, ClassDescriber> classDescriberMap = new HashMap<>();
    private final Set<String> knownParameterizedTypeSet = new HashSet<>();
    private final ProcessContext processContext;

    public ClassDescriber fromClass(@NotNull JavaClass javaClass) {
        if (AssertUtils.isBasicType(javaClass)) {
            return getOrLoadBasicClass(javaClass);
        }

        if (isParameterizedClass(javaClass)) {
            return getOrLoadParameterizedClass((DefaultJavaParameterizedType) javaClass);
        }

        return getOrLoadGeneralClass(javaClass);
    }

    private static boolean isParameterizedClass(JavaClass javaClass) {
        return javaClass instanceof JavaParameterizedType &&
                !AssertUtils.isEmpty(((JavaParameterizedType) javaClass).getActualTypeArguments());
    }


    private ClassDescriber getOrLoadBasicClass(JavaType javaType) {
        String name = javaType.getValue();

        ClassDescriber classDescriber = classDescriberMap.get(name);

        if (classDescriber == null) {
            String fullName = javaType.getValue();
            classDescriber = new BasicTypeClassDescriber(fullName.substring(0, fullName.lastIndexOf(".")),
                    fullName.substring(fullName.lastIndexOf(".") + 1));
            classDescriberMap.put(fullName, classDescriber);
        }

        return classDescriber;
    }

    private ClassDescriber getOrLoadGeneralClass(JavaClass javaClass) {
        FieldAssociated fa = buildClassFields(javaClass);

        return new GeneralClassDescriber(javaClass, fa.getFields(), fa.getExtendSet());
    }

    private FieldAssociated buildClassFields(JavaClass javaClass) {
        return buildClassFieldsCore(javaClass, null);
    }

    private FieldAssociated buildClassFieldsCore(JavaClass javaClass, FieldAssociated fieldAssociated) {
        fieldAssociated = ObjectUtils.firstNonNull(fieldAssociated, FieldAssociated::new);

        if (AssertUtils.isBasicType(javaClass)) {
            return fieldAssociated;
        }

        for (JavaField field : javaClass.getFields()) {
            if (field.getModifiers().contains("final") || field.getModifiers().contains("static")) {
                continue;
            }

            ApiModelProperty apiModelProperty = ApiModelProperty.fromJavaField(field);

            if (apiModelProperty.getValue() == null) {
                ServiceResolver.setResolveFail();
                if (processContext.getLogUnresolved()) {
                    DocLogger.error("[注释缺失] 属性: " + javaClass.getName() + "#" + field.getName() + " 未找到有效注解");
                }
            }

            JavaClass fieldType = field.getType();
            FieldDescriber fieldDescriber = new DefaultFieldDescriber(apiModelProperty.getRequired(),
                    apiModelProperty.getValue(), apiModelProperty.getNote(), fieldType);
            fieldAssociated.addField(fieldDescriber);

            if (!fieldDescriber.isBasicType()) {
                fieldAssociated.addExtend(fromClass(fieldType));
            }
        }

        JavaType superClassType = javaClass.getSuperClass();
        if (superClassType == null) {
            return fieldAssociated;
        }

        if (AssertUtils.isBasicType(superClassType)) {
            return fieldAssociated;
        }

        JavaClass superClass = processContext.getSourceClassLoader().find(superClassType.getValue());
        if (superClass != null) {
            return buildClassFieldsCore(superClass, fieldAssociated);
        } else {
            DocLogger.warn(String.format("无法解析的类: %s，可能不是源码!", superClassType.getBinaryName()));
            return fieldAssociated;
        }
    }

    private ClassDescriber getOrLoadParameterizedClass(DefaultJavaParameterizedType javaClass) {
        String classFullName = javaClass.toString();

        if (knownParameterizedTypeSet.contains(classFullName)) {
            List<JavaType> actualTypeArguments = javaClass.getActualTypeArguments();

            if (actualTypeArguments.size() > 1) {
                throw new IllegalArgumentException("customized parameterized type allow only one parameter");
            }

            String fullName = actualTypeArguments.get(0).getValue();
            ClassDescriber classDescriber = classDescriberMap.get(fullName);

            if (classDescriber == null) {
                JavaClass typeClass = processContext.getSourceClassLoader().find(fullName);
                classDescriber = fromClass(typeClass);
                classDescriberMap.putIfAbsent(fullName, classDescriber);
            }

            return classDescriber;
        }

        List<ClassDescriber> parametrizedList = new LinkedList<>();
        for (JavaType typeArgument : javaClass.getActualTypeArguments()) {
            JavaClass typeClass = processContext.getSourceClassLoader().find(typeArgument.toString());

            if (typeClass == null) {
                // TODO 非源代码引用，处理不来
            } else {
                parametrizedList.add(fromClass(typeClass));
            }
        }

        FieldAssociated fa = buildClassFields(javaClass);
        return new ParameterizedClassDescriber(javaClass, fa.getFields(), fa.getExtendSet(), parametrizedList);
    }

    public static void main(String[] args) throws IOException {
        ProcessContext processContext = new ProcessContext("", "", "", "");
        processContext.init();
        processContext.getSourceClassLoader().loadClass("/Users/xunyard/terminus/item-center/itemcenter-component/ic-item/item-api/src/main/java/io/terminus/parana/item/item/api/bean/response/item/ItemInfo.java");
        List<JavaClass> javaClasses = processContext.getSourceClassLoader().loadClass("/Users/xunyard/terminus/item-center/itemcenter-component/ic-item/item-api/src/main/java/io/terminus/parana/item/item/api/bean/response/item/FullItemInfo.java");
        ClassDescriber classDescriber = processContext.getClassDescriberMaker().fromClass(javaClasses.iterator().next());
    }
}
