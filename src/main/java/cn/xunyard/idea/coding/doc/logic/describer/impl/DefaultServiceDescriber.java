package cn.xunyard.idea.coding.doc.logic.describer.impl;

import cn.xunyard.idea.coding.doc.logic.ClassUtils;
import cn.xunyard.idea.coding.doc.logic.describer.MethodDescriber;
import cn.xunyard.idea.coding.doc.logic.describer.ServiceDescriber;
import cn.xunyard.idea.coding.doc.logic.model.Api;
import cn.xunyard.idea.coding.util.ObjectUtils;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-25
 */
@Getter
public class DefaultServiceDescriber implements ServiceDescriber {

    private final List<MethodDescriber> methods;
    private final String description;
    private final String note;
    private final String packageName;
    private final String className;

    public DefaultServiceDescriber(Api api, List<MethodDescriber> methods, JavaClass javaClass) {
        this.methods = ObjectUtils.firstNonNull(methods, ArrayList::new);
        this.description = api.getValue();
        this.note = api.getNote();
        this.packageName = ClassUtils.getPackage(javaClass);
        this.className = ClassUtils.getSimpleName(javaClass);
    }

    @Override
    public String getPackage() {
        return packageName;
    }

    @Override
    public String getSimpleClass() {
        return className;
    }

    @Override
    public String getFullName() {
        return packageName + "." + className;
    }
}
