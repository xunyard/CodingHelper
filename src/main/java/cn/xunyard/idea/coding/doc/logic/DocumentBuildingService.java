package cn.xunyard.idea.coding.doc.logic;

import cn.xunyard.idea.coding.doc.logic.service.ServiceAsyncProcessor;
import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.log.LoggerFactory;
import cn.xunyard.idea.coding.util.ProjectUtils;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-15
 */
public class DocumentBuildingService {
    private final Logger log = LoggerFactory.getLogger(DocConfig.IDENTITY);
    private final DocConfig docConfig;
    private final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    public DocumentBuildingService(DocConfig docConfig) {
        this.docConfig = docConfig;
    }

    public synchronized void run() {
        log.clear();
        if (THREAD_POOL.getActiveCount() > 0) {
            log.error("尚有任务在进行中，提交失败!");
            return;
        }

        ServiceAsyncProcessor processor = new ServiceAsyncProcessor(docConfig, ProjectUtils.getCurrentProject(), this::onDone);
        THREAD_POOL.submit(processor);
    }

    private void onDone(DocConfig docConfig) {
        log.done();
    }
}
