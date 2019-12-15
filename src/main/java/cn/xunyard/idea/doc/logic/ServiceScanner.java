package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.util.LogCallback;
import com.google.common.base.Joiner;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceScanner {

    private final String serviceSuffix;
    private final String packagePrefix;


    private final LogCallback logCallback;

    private final Set<String> scannedServiceJavaPathSet;

    public ServiceScanner(String serviceSuffixParam,
                          String packagePrefixParam,
                          LogCallback logCallback) {
        this.serviceSuffix = wrapService(serviceSuffixParam);
        this.packagePrefix = wrapPackage(packagePrefixParam);

        this.logCallback = logCallback;
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
        logCallback.log("开始扫描服务类...");
        scanPath(basePath);
        logCallback.log(String.format("扫描完成!共发现%d个服务", scannedServiceJavaPathSet.size()));
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
                logCallback.log("检测到无效文件地址:" + path);
                throw new IllegalArgumentException("invalid.file.path");
            }

            if (path.endsWith(".java")) {
                String matchString = path.substring(0, path.length() - ".java".length());

                String packagePath = resolveSrcPackagePath(matchString);
                String className = path.substring(path.lastIndexOf("/") + 1, path.length() - ".java".length());
                if (packagePrefix != null && (packagePath == null || !packagePath.contains(packagePrefix))) {
                    return;
                }

                if (serviceSuffix != null && !matchString.endsWith(serviceSuffix)) {
                    return;
                }

                scannedServiceJavaPathSet.add(path);
                logCallback.log("发现服务:" + packagePath + "/" + className);
            }
        }
    }

    private boolean isValidJavaSourcePath(String path) {
        return path.contains(JAVA_SRC_ROOT);
    }

    private String resolveSrcPackagePath(String path) {
        if (path == null || path.length() == 0 || !path.contains(JAVA_SRC_ROOT)) {
            return null;
        }

        return path.substring(path.lastIndexOf(JAVA_SRC_ROOT) + JAVA_SRC_ROOT.length(), path.lastIndexOf("/"));
    }

    private final String JAVA_SRC_ROOT = "/src/main/java/";
}
