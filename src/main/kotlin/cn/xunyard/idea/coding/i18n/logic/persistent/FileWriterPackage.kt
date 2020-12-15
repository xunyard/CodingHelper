package cn.xunyard.idea.coding.i18n.logic.persistent

import java.io.File
import java.io.FileWriter

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
internal class FileWriterPackage(filepath: String) {
    private var unusedCount = 0
    private val fileWriter: FileWriter = FileWriter(File(filepath), true)

    fun getFileWriter(): FileWriter {
        unusedCount = 0
        return fileWriter
    }

    fun allowClose(): Boolean {
        unusedCount++

        if (unusedCount == FLUSH_UNUSED_COUNT) {
            fileWriter.flush()
        }

        if (unusedCount > MAX_UNUSED_COUNT) {
            fileWriter.close()
            return true
        }

        return false
    }

    companion object {
        private const val FLUSH_UNUSED_COUNT = 10
        private const val MAX_UNUSED_COUNT = 100
    }
}