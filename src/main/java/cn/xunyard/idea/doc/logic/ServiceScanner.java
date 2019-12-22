package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.doc.logic.describer.ClassDescriber;
import cn.xunyard.idea.util.ProjectUtils;
import com.google.common.base.Joiner;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceScanner {

    private final DocBuildingContext docBuildingContext;

    private final String serviceSuffix;
    private final String packagePrefix;

    private final Set<String> scannedServiceJavaPathSet;

    public ServiceScanner(DocBuildingContext docBuildingContext) {
        this.serviceSuffix = wrapService(docBuildingContext.getServiceSuffix());
        this.packagePrefix = wrapPackage(docBuildingContext.getPackagePrefix());

        this.docBuildingContext = docBuildingContext;
        this.scannedServiceJavaPathSet = new HashSet<>();
    }

    private String wrapPackage(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return null;
        }

        if (!prefix.contains(".")) {
            return prefix;
        }

        return Joiner.on("/").skipNulls().join(prefix.split("\\."));
    }

    private String wrapService(String suffix) {
        if (suffix == null || suffix.length() == 0) {
            return null;
        }

        return suffix;
    }

    public Set<String> scan(String basePath) {
        DocLogger.info("开始扫描服务类...");
        scanPath(basePath);
        DocLogger.info(String.format("扫描完成!共发现%d个服务", scannedServiceJavaPathSet.size()));
        return scannedServiceJavaPathSet;
    }

    private void scanPath(String path) {
        File file = new File(path);

        if (file.isDirectory()) {
            String[] children = file.list();

            if (children == null) {
                return;
            }

            for (String child : children) {
                scanPath(path + "/" + child);
            }
        } else {
            if (!file.exists()) {
                DocLogger.error("检测到无效文件地址:" + path);
                throw new IllegalArgumentException("invalid.file.path");
            }

            if (!path.endsWith(".java") || !ProjectUtils.isSrcClass(path)) {
                return;
            }

            ClassDescriber classDescriber = ClassDescriber.fromFullPath(path);
            docBuildingContext.addClass(classDescriber);

            if (packagePrefix != null && !classDescriber.getClassPackage().contains(packagePrefix)) {
                return;
            }

            if (serviceSuffix != null && !classDescriber.getClassName().endsWith(serviceSuffix)) {
                return;
            }

            scannedServiceJavaPathSet.add(path);

            if (docBuildingContext.getLogServiceDetail()) {
                DocLogger.info("发现服务:" + classDescriber.getClassPackage() + "/" + classDescriber.getClassName());
            }
        }
    }
}
