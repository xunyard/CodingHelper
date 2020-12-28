package cn.xunyard.idea.coding.doc;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-18
 */
public enum ContentSource {
    ANNOTATION("注解"),
    JAVADOC("JavaDoc");

    private final String description;

    ContentSource(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
