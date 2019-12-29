package cn.xunyard.idea.coding.doc;

import cn.xunyard.idea.coding.doc.logic.ProcessContext;
import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.log.LoggerFactory;
import cn.xunyard.idea.coding.util.AssertUtils;
import cn.xunyard.idea.coding.util.ProjectUtils;
import com.intellij.openapi.project.Project;
import com.thoughtworks.qdox.model.JavaClass;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.SystemIndependent;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class ServiceDocBuildingService {
    private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);
    private final DocConfig docConfig;
    private final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    public ServiceDocBuildingService(DocConfig docConfig) {
        this.docConfig = docConfig;
    }

    public synchronized void run(Project project) {
        if (THREAD_POOL.getActiveCount() > 0) {
            log.error("尚有任务在进行中，提交失败!");
            throw new RuntimeException("submit.forbidden");
        }

        TaskRunner taskRunner = new TaskRunner(docConfig, project);
        THREAD_POOL.submit(taskRunner);
    }

    @RequiredArgsConstructor
    private static class TaskRunner implements Runnable {
        private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);
        private final DocConfig docConfig;
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

            ProcessContext processContext = new ProcessContext(docConfig);
            try {
                processContext.init();

                ServiceScanner serviceScanner = new ServiceScanner(processContext);
                List<JavaClass> serviceJavaClassList = serviceScanner.scan(basePath);
                if (AssertUtils.isEmpty(serviceJavaClassList)) {
                    log.error("过程终止，未发现有效源文件路径!");
                    return;
                }

                ServiceResolver serviceResolver = new ServiceResolver(serviceJavaClassList, processContext);
                if (!serviceResolver.run() && !docConfig.getAllowInfoMissing()) {
                    log.error("过程终止，修复注释缺失问题或者打开忽略开关以继续!");
                    return;
                }

                ServiceDocumentBuilder documentBuilder = new ServiceDocumentBuilder(processContext, serviceResolver.getServiceDescriberList());
                documentBuilder.run();

            } catch (Exception e) {
                log.error("fail to run, cause: " + formatException(e));
            } finally {
                processContext.clear();
            }
        }

        private String formatException(Exception e) {
            String message = e.toString();
            String str = Arrays.stream(e.getStackTrace()).map(Objects::toString).collect(Collectors.joining("\n"));
            return message + "\n" + str;
        }

        private boolean checkOutput() {
            String outputDirectory = docConfig.getOutputDirectory();
            File file = new File(outputDirectory);
            try {
                if (!file.exists() || !file.isDirectory()) {
                    log.error("错误: " + outputDirectory + " 不是有效的目录");
                    return false;
                }

                String fullPath = outputDirectory + "/" + docConfig.getOutputFileName();
                File outputFile = new File(fullPath);
                if (outputFile.exists()) {
                    log.warn("检测到输出文件已存在，删除!");
                    boolean isOk = outputFile.delete();

                    if (!isOk) {
                        log.error("错误: " + fullPath + " 无法删除");
                        return false;
                    }
                }

                return true;
            } catch (Exception e) {
                log.error("检测到错误: " + formatException(e));
                return false;
            }
        }
    }
}
