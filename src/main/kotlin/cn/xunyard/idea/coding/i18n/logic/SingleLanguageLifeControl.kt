package cn.xunyard.idea.coding.i18n.logic

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
interface SingleLanguageLifeControl {

    /**
     * 按照配置重新加载
     * @param newFilepath 新的翻译文件路径
     */
    fun reload(newFilepath: String?)

    /**
     * 释放
     */
    fun free()
}