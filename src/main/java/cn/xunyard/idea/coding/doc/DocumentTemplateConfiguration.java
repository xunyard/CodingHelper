package cn.xunyard.idea.coding.doc;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-18
 */
public class DocumentTemplateConfiguration {

    /**
     * 注释来源
     */
    private ContentSource firstContentSource;

    private Boolean enableSerialNumber;

    public Boolean getEnableSerialNumber() {
        return enableSerialNumber;
    }

    public void setEnableSerialNumber(Boolean enableSerialNumber) {
        this.enableSerialNumber = enableSerialNumber;
    }

    public ContentSource getFirstContentSource() {
        return firstContentSource;
    }

    public void setFirstContentSource(ContentSource firstContentSource) {
        this.firstContentSource = firstContentSource;
    }


}
