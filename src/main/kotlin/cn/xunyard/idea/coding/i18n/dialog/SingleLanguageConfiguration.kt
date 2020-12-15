package cn.xunyard.idea.coding.i18n.dialog

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
data class SingleLanguageConfiguration(
        /**
         * 翻译目标语言
         */
        val language: String,

        /**
         * 翻译文件路径
         */
        val filepath: String,
)