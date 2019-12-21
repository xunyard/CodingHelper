package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingService {
    private static final String OUTPUT_DOC_FILENAME = "doc.md";

    private final DocBuildingContext docBuildingContext;

    public ServiceDocBuildingService(DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
    }

    public void run(String basePath) {
        if (!checkOutput()) {
            return;
        }

        try {
            ServiceScanner serviceScanner = new ServiceScanner(docBuildingContext);
            Set<String> srcPathSet = serviceScanner.scan(basePath);
            ServiceResolver serviceResolver = new ServiceResolver(srcPathSet, docBuildingContext);
            serviceResolver.run();
        } catch (Exception e) {
            DocLogger.error("fail to run, cause: " + formatException(e));
        }
    }

    private String formatException(Exception e) {
        String message = e.toString();
        String str = Arrays.stream(e.getStackTrace()).map(Objects::toString).collect(Collectors.joining("\n"));
        return message + "\n" + str;
    }

    private boolean checkOutput() {
        String outputDirectory = docBuildingContext.getOutputDirectory();
        File file = new File(outputDirectory);
        try {
            if (!file.exists() || !file.isDirectory()) {
                DocLogger.error("错误: " + outputDirectory + " 不是有效的目录");
                return false;
            }

            String fullPath = outputDirectory + "/" + OUTPUT_DOC_FILENAME;
            File outputFile = new File(fullPath);
            if (outputFile.exists()) {
                DocLogger.warn("检测到输出文件已存在，删除!");
                boolean isOk = outputFile.delete();

                if (!isOk) {
                    DocLogger.error("错误: " + fullPath + " 无法删除");
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            DocLogger.error("检测到错误: " + formatException(e));
            return false;
        }
    }
}
