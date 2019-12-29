package cn.xunyard.idea.coding.log.window;

import cn.xunyard.idea.coding.log.Logger;
import cn.xunyard.idea.coding.util.ProjectUtils;
import lombok.RequiredArgsConstructor;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
@RequiredArgsConstructor
public class WindowLogger extends AbstractThreadBasedWindowedLogger implements Logger {

    private final String identity;

    private LogMessage buildLogMessage(LogOperation operation, String format, Object... args) {
        return new LogMessage(ProjectUtils.getCurrentProject(), identity, operation, format, args);
    }

    @Override
    public void clear() {
        submitLog(buildLogMessage(LogOperation.CLEAR, null));
    }

    @Override
    public void debug(String format, Object... args) {
        submitLog(buildLogMessage(LogOperation.DEBUG, format, args));
    }

    @Override
    public void info(String format, Object... args) {
        submitLog(buildLogMessage(LogOperation.INFO, format, args));
    }

    @Override
    public void warn(String format, Object... args) {
        submitLog(buildLogMessage(LogOperation.WARN, format, args));
    }

    @Override
    public void error(String format, Object... args) {
        submitLog(buildLogMessage(LogOperation.ERROR, format, args));
    }

    @Override
    public void done() {
        submitLog(buildLogMessage(LogOperation.DONE, null));
    }
}
