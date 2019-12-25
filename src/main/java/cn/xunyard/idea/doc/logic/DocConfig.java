package cn.xunyard.idea.doc.logic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-16
 */
@Getter
public class DocConfig {
    private final String serviceSuffix;
    private final String packagePrefix;
    private final String outputDirectory;
    private final String outputFileName;
    @Setter
    private Boolean logServiceDetail;
    @Setter
    private Boolean logUnresolved;
    @Setter
    private Boolean allowInfoMissing;
    @Setter
    private List<String> returnPackList;

    public DocConfig(String serviceSuffix,
                     String packagePrefix,
                     String outputDirectory,
                     String outputFileName) {
        this.serviceSuffix = serviceSuffix;
        this.packagePrefix = packagePrefix;
        this.outputDirectory = outputDirectory;
        this.outputFileName = outputFileName;
        this.logServiceDetail = false;
        this.logUnresolved = false;
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
