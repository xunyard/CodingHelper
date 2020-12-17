package cn.xunyard.idea.coding.i18n.logic.impl

import java.io.File
import java.io.FileWriter
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.LockSupport

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class AsyncTranslatePersist : Thread() {
    private val persistentQueue: Queue<ToPersistentPackage> = ConcurrentLinkedQueue()
    private val fileWriterMap: MutableMap<String, FileWriterPackage> = HashMap()
    private var parking = false

    init {
        this.start()
    }

    private fun onAddElement() {
        if (parking) {
            LockSupport.unpark(this)
            parking = false
        }
    }

    fun submit(filepath: String, errorCode: String, translate: String) {
        persistentQueue.push(ToPersistentPackage(filepath, errorCode, translate))
    }

    override fun run() {
        while (true) {
            if (persistentQueue.isEmpty()) {
                sleepOrJustWait()
                continue
            }
            try {
                flushData()
            } catch (e: Exception) {
                println("${e.message}${Arrays.toString(e.stackTrace)}".trimIndent())
            }
        }
    }

    private fun flushData() {
        var toPersistentPackage: ToPersistentPackage?
        while (persistentQueue.poll().also { toPersistentPackage = it } != null) {
            val filepath: String = toPersistentPackage!!.filepath
            var writerPackage = fileWriterMap[filepath]
            if (writerPackage == null) {
                writerPackage = FileWriterPackage(filepath)
                fileWriterMap[filepath] = writerPackage
            }
            writerPackage.getFileWriter().write(
                java.lang.String.format(
                    Locale.CHINESE, "%s=%s\n",
                    toPersistentPackage!!.errorCode, toPersistentPackage!!.translate
                )
            )
        }
    }

    private fun sleepOrJustWait() {
        for ((key, value) in fileWriterMap) {
            if (value.allowClose()) {
                fileWriterMap.remove(key)
            }
        }
        if (fileWriterMap.isEmpty()) {
            parking = true
            LockSupport.park()
        } else {
            try {
                sleep(100)
            } catch (e: InterruptedException) {
                // just ignore
            }
        }
    }

    fun Queue<ToPersistentPackage>.push(pkg: ToPersistentPackage) {
        this.add(pkg)
        onAddElement()
    }

    companion object {
        private class LazyInit {
            companion object {
                var instance: AsyncTranslatePersist = AsyncTranslatePersist()
            }
        }

        fun submit(filepath: String, errorCode: String, translate: String) {
            LazyInit.instance.submit(filepath, errorCode, translate)
        }


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

        internal class FileWriterPackage constructor(
            filepath: String
        ) {
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
    }
}
