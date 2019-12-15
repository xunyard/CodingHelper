package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.util.LogCallback;

import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingService {

    private final ServiceScanner serviceScanner;
    private final LogCallback logCallback;

    public ServiceDocBuildingService(String serviceSuffix,
                                     String packagePrefix,
                                     LogCallback logCallback) {
        serviceScanner = new ServiceScanner(serviceSuffix, packagePrefix, logCallback);
        this.logCallback = logCallback;
    }

    public void run(String basePath) {
        Set<String> srcPathSet = serviceScanner.scan(basePath);
        ServiceResolver serviceResolver = new ServiceResolver(srcPathSet, logCallback);
        serviceResolver.run();
    }

}
