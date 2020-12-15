package cn.xunyard.idea.coding.i18n.logic.persistent

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
data class ToPersistentPackage(

        /**
         * 语言配置文件
         */
        val filepath: String,

        /**
         * 错误码
         */
        val errorCode: String,

        /**
         * 翻译内容
         */
        val translate: String
)