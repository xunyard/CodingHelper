package cn.xunyard.idea.doc.logic.describer;

import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ProjectUtils;
import lombok.Getter;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-17
 */
@Getter
public class ClassDescriber {
    private final String className;
    private final String classPackage;
    private final String fullPath;

    private ClassDescriber(String className, String classPackage, String fullPath) {
        this.className = className;
        this.classPackage = classPackage;
        this.fullPath = fullPath;
    }

    public static ClassDescriber fromFullPath(String fullPath) {
        if (AssertUtils.isEmpty(fullPath) || !fullPath.endsWith(".java")) {
            throw new IllegalArgumentException("invalid.file.path");
        }

        String className = fullPath.substring(fullPath.lastIndexOf("/") + 1, fullPath.lastIndexOf(".java"));
        String classPackage = ProjectUtils.resolveSrcPackagePath(fullPath);
        return new ClassDescriber(className, classPackage, fullPath);
    }
}
