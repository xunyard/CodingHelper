package cn.xunyard.idea.coding.doc.logic.service;

import cn.xunyard.idea.coding.doc.logic.ClassUtils;
import cn.xunyard.idea.coding.doc.logic.DocConfig;
import cn.xunyard.idea.coding.doc.logic.ProcessContext;
import cn.xunyard.idea.coding.doc.logic.describer.ClassDescriber;
import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.log.LoggerFactory;
import cn.xunyard.idea.coding.util.AssertUtils;
import com.google.common.base.Joiner;
import com.thoughtworks.qdox.model.JavaClass;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceScanner {
    private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);
    private final ProcessContext processContext;
    private final String serviceSuffix;
    private final String packagePrefix;

    private final List<JavaClass> scannedServices;

    public ServiceScanner(ProcessContext processContext) {
        this.processContext = processContext;
        this.serviceSuffix = wrapService(processContext.getDocConfig().getServiceSuffix());
        this.packagePrefix = wrapPackage(processContext.getDocConfig().getPackagePrefix());

        this.scannedServices = new LinkedList<>();
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

    public List<JavaClass> scan(String basePath) throws IOException {
        log.info("开始扫描服务类...");
        scanPath(basePath);
        log.info(String.format("扫描完成!共发现%d个服务", scannedServices.size()));
        return scannedServices;
    }

    private void scanPath(String path) throws IOException {
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
                log.error("检测到无效文件地址:" + path);
                throw new IllegalArgumentException("invalid.file.path");
            }

            if (!path.endsWith(".java") || !ClassUtils.isSrcClass(path)) {
                return;
            }

            List<JavaClass> javaClasses = processContext.getSourceClassLoader().loadClass(path);
            if (AssertUtils.isEmpty(javaClasses)) {
                return;
            }
            ClassDescriber classDescriber = processContext.getClassDescriberMaker().simpleFromClass(path);

            if (packagePrefix != null && !classDescriber.getPackage().contains(packagePrefix)) {
                return;
            }

            if (serviceSuffix != null && !classDescriber.getSimpleName().endsWith(serviceSuffix)) {
                return;
            }

            scannedServices.addAll(javaClasses);

            if (processContext.getDocConfig().getLogServiceDetail()) {
                log.info("发现服务:" + classDescriber.getPackage() + "/" + classDescriber.getSimpleName());
            }
        }
    }
}