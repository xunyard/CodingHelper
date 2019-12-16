package cn.xunyard.idea.doc.logic;

import java.util.Set;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingService {

    private final DocBuildingContext docBuildingContext;

    public ServiceDocBuildingService(DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
    }

    public void run(String basePath) {
        ServiceScanner serviceScanner = new ServiceScanner(docBuildingContext.getServiceSuffix(),
                docBuildingContext.getPackagePrefix());

        Set<String> srcPathSet = serviceScanner.scan(basePath);
        ServiceResolver serviceResolver = new ServiceResolver(srcPathSet);
        serviceResolver.run();
    }

}
