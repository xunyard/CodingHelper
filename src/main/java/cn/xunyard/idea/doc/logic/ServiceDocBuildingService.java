package cn.xunyard.idea.doc.logic;

import cn.xunyard.idea.doc.DocLogger;
import cn.xunyard.idea.util.AssertUtils;
import cn.xunyard.idea.util.ProjectUtils;
import com.intellij.openapi.project.Project;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.SystemIndependent;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingService {
    private final DocBuildingContext docBuildingContext;
    private final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    public ServiceDocBuildingService(DocBuildingContext docBuildingContext) {
        this.docBuildingContext = docBuildingContext;
    }

    public synchronized void run(Project project) {
        if (THREAD_POOL.getActiveCount() > 0) {
            DocLogger.error("尚有任务在进行中，提交失败!");
            throw new RuntimeException("submit.forbidden");
        }

        TaskRunner taskRunner = new TaskRunner(docBuildingContext, project);
        THREAD_POOL.submit(taskRunner);
    }

    @RequiredArgsConstructor
    private static class TaskRunner implements Runnable {
        private final DocBuildingContext docBuildingContext;
        private final Project project;

        @Override
        public void run() {
            ProjectUtils.PROJECT.set(project);
            @SystemIndependent String basePath = project.getBasePath();
            run(basePath);
            ProjectUtils.PROJECT.remove();
        }

        public void run(String basePath) {
            if (!checkOutput()) {
                return;
            }

            try {
                ServiceScanner serviceScanner = new ServiceScanner(docBuildingContext);
                Set<String> srcPathSet = serviceScanner.scan(basePath);
                if (AssertUtils.isEmpty(srcPathSet)) {
                    DocLogger.error("过程终止，未发现有效源文件路径!");
                    return;
                }

                ServiceResolver serviceResolver = new ServiceResolver(srcPathSet, docBuildingContext);
                if (!serviceResolver.run() && !docBuildingContext.getAllowInfoMissing()) {
                    DocLogger.error("过程终止，修复注释缺失问题或者打开忽略开关以继续!");
                    return;
                }

                ServiceDocumentBuilder documentBuilder = new ServiceDocumentBuilder(docBuildingContext,
                        serviceResolver.getServiceDescriberList());
                documentBuilder.run();

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

                String fullPath = outputDirectory + "/" + docBuildingContext.getOutputFileName();
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
}
