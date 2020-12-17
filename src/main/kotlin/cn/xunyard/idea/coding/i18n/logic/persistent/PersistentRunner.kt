package cn.xunyard.idea.coding.i18n.logic.persistent

import com.intellij.openapi.vcs.VcsVFSListener.MyAsyncVfsListener
import com.intellij.openapi.vfs.VirtualFileManager
import java.util.*
import java.util.concurrent.locks.LockSupport
import kotlin.collections.HashMap

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
internal class PersistentRunner constructor(
        private val persistentQueue: Queue<ToPersistentPackage>,
) : Thread() {
    private val fileWriterMap: MutableMap<String, FileWriterPackage> = HashMap()
    private var parking = false

    override fun run() {
        VirtualFileManager.getInstance().addAsyncFileListener(MyAsyncVfsListener(), this)

        while (true) {
            if (persistentQueue.isEmpty()) {
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

                continue
            }
            try {
                var toPersistentPackage: ToPersistentPackage?
                while (persistentQueue.poll().also { toPersistentPackage = it } != null) {
                    val filepath: String = toPersistentPackage!!.filepath
                    var writerPackage = fileWriterMap[filepath]
                    if (writerPackage == null) {
                        writerPackage = FileWriterPackage(filepath)
                        fileWriterMap[filepath] = writerPackage
                    }
                    writerPackage.getFileWriter().write(java.lang.String.format(Locale.CHINESE, "%s=%s\n",
                            toPersistentPackage!!.errorCode, toPersistentPackage!!.translate))
                }
            } catch (e: Exception) {
                println("${e.message}${Arrays.toString(e.stackTrace)}".trimIndent())
            }
        }
    }

    fun onAddElement() {
        if (parking) {
            LockSupport.unpark(this)
            parking = false
        }
    }

    companion object {

        fun Queue<ToPersistentPackage>.push(pkg: ToPersistentPackage, runner: PersistentRunner) {
            this.add(pkg)
            runner.onAddElement()
        }
    }
}