package cn.xunyard.idea.doc.logic.describer;

import com.thoughtworks.qdox.model.JavaType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
public class BeanDescriberManager {

    private final Map<JavaType, BeanDescriber> describerMap;

    private BeanDescriberManager() {
        this.describerMap = new HashMap<>();
    }

    public BeanDescriber load(JavaType javaType) {
        if (describerMap.containsKey(javaType)) {
            return describerMap.get(javaType);
        }

        BeanDescriber beanDescriber = BeanDescriber.fromJavaType(javaType);
        describerMap.put(javaType, beanDescriber);
        return beanDescriber;
    }

    public void clear() {
        describerMap.clear();
    }

    public static BeanDescriberManager beanDescriberManager;

    public static BeanDescriberManager getInstance() {
        if (beanDescriberManager == null) {
            beanDescriberManager = new BeanDescriberManager();
        }

        return beanDescriberManager;
    }
}
