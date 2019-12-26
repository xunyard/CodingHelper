package cn.xunyard.idea.coding.log;

import com.intellij.openapi.project.Project;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-12-26
 */
@Getter
@RequiredArgsConstructor
public class LogMessage {
    private final Project project;
    private final String identity;
    private final LogOperation operation;
    private final String format;
    private final Object[] arguments;
}
