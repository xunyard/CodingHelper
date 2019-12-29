package cn.xunyard.idea.coding.log;

import cn.xunyard.idea.coding.log.window.WindowLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
public class LoggerFactory {

    private static Map<String, Logger> loggerMap;

    public static Logger getLogger(String identityId) {
        if (loggerMap == null) {
            loggerMap = new HashMap<>();
        }

        if (loggerMap.containsKey(identityId)) {
            return loggerMap.get(identityId);
        }

        WindowLogger logger = new WindowLogger(identityId);
        loggerMap.put(identityId, logger);
        return logger;
    }

}
