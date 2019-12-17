package cn.xunyard.idea.doc.logic.describer;

import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
public class MethodDescriber {

    public static MethodDescriber fromJavaMethod(JavaMethod javaMethod) {
        List<JavaParameter> parameters = javaMethod.getParameters();
        return null;
    }
}
