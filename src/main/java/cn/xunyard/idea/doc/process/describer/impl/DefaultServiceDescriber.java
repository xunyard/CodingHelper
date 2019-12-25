package cn.xunyard.idea.doc.process.describer.impl;

import cn.xunyard.idea.doc.process.describer.MethodDescriber;
import cn.xunyard.idea.doc.process.describer.ServiceDescriber;
import cn.xunyard.idea.doc.process.model.Api;
import cn.xunyard.idea.util.ObjectUtils;
import cn.xunyard.idea.util.ProjectUtils;
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
        this.packageName = ProjectUtils.getPackage(javaClass);
        this.className = ProjectUtils.getSimpleName(javaClass);
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
