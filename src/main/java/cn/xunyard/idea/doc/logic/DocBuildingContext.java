package cn.xunyard.idea.doc.logic;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-16
 */
public class DocBuildingContext {
    private final String serviceSuffix;
    private final String packagePrefix;
    private final String outputDirectory;

    public DocBuildingContext(String serviceSuffix, String packagePrefix, String outputDirectory) {
        this.serviceSuffix = serviceSuffix;
        this.packagePrefix = packagePrefix;
        this.outputDirectory = outputDirectory;
    }

    public String getServiceSuffix() {
        return serviceSuffix;
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }
}
