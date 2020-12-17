package cn.xunyard.idea.coding.i18n;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-17
 */
public class InspectionConfiguration {
    public static final String PROPERTIES_SUFFIX = ".properties";

    private Map<String, String> languageConfigMap = new HashMap<>();

    public Map<String, String> getLanguageConfigMap() {
        return languageConfigMap;
    }

    public void setLanguageConfigMap(Map<String, String> languageConfigMap) {
        this.languageConfigMap = languageConfigMap;
    }

    public Map<String, String> getAll()  {
        return this.languageConfigMap;
    }

    public void refreshAll(Map<String, String> newMap) {
        languageConfigMap.clear();
        languageConfigMap.putAll(newMap);
    }

    /**
     * 判断是否已有语言项
     *
     * @param language 语言项
     * @return 已有返回true，否则false
     */
    public boolean contains(String language) {
        return languageConfigMap.containsKey(language);
    }
}
