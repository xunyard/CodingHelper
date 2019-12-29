package cn.xunyard.idea.coding.log;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
public interface Logger {

    void clear();

    void debug(String format, Object... args);

    void info(String format, Object... args);

    void warn(String format, Object... args);

    void error(String format, Object... args);

    void done();
}
