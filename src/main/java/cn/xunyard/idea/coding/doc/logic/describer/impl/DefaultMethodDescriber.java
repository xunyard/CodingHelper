package cn.xunyard.idea.coding.doc.logic.describer.impl;

import cn.xunyard.idea.coding.doc.ClassUtils;
import cn.xunyard.idea.coding.doc.logic.describer.ClassDescriber;
import cn.xunyard.idea.coding.doc.logic.describer.MethodDescriber;
import cn.xunyard.idea.coding.doc.logic.describer.ParameterDescriber;
import cn.xunyard.idea.coding.doc.logic.model.ApiOperation;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
@Getter
public class DefaultMethodDescriber implements MethodDescriber {

    private final String description;
    private final String note;
    private final List<ParameterDescriber> parameterList;
    @Nullable
    private final ClassDescriber response;
    private final String className;
    private final String packageName;
    private final String simpleName;

    public DefaultMethodDescriber(ApiOperation apiOperation, List<ParameterDescriber> parameterList
            , @Nullable ClassDescriber response, JavaMethod javaMethod, JavaClass javaClass) {
        this.description = apiOperation.getValue();
        this.note = apiOperation.getNote();
        this.parameterList = ObjectUtils.firstNonNull(parameterList, ArrayList::new);
        this.response = response;
        this.simpleName = javaMethod.getName();
        this.packageName = ClassUtils.getPackage(javaClass);
        this.className = ClassUtils.getSimpleName(javaClass);
    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public String getSign() {
        StringBuilder sb = new StringBuilder(response == null ? "void" : response.toSimpleString())
                .append(" ").append(getSimpleName()).append("(");
        for (int i = 0; i < parameterList.size(); i++) {
            if (i != 0) {
                sb.append(", ");
            }
            ParameterDescriber describer = parameterList.get(i);
            sb.append(describer.getClassDescriber().toSimpleString()).append(" ").append(describer.getName());
        }
        sb.append(")");
        return sb.toString();
    }
}
