package cn.xunyard.idea.coding.i18n.logic.persistent

import cn.xunyard.idea.coding.i18n.logic.persistent.PersistentRunner.Companion.push
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-01-11
 */
class TranslateAsyncPersistent {
    private val persistentQueue: Queue<ToPersistentPackage> = ConcurrentLinkedQueue()
    private val runner: PersistentRunner = PersistentRunner(persistentQueue).apply { start() }

    fun submit(filepath: String, errorCode: String, translate: String) {
        persistentQueue.push(ToPersistentPackage(filepath, errorCode, translate), runner)
    }

    companion object {
        private class LazyInit {
            companion object {
                var instance: TranslateAsyncPersistent = TranslateAsyncPersistent()
            }
        }

        fun submit(filepath: String, errorCode: String, translate: String) {
            LazyInit.instance.submit(filepath, errorCode, translate)
        }
    }
}