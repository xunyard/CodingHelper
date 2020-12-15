package cn.xunyard.idea.coding.dialog

import cn.xunyard.idea.coding.util.ObjectUtils
import java.awt.event.ActionListener
import java.util.function.Consumer
import java.util.function.Supplier

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-15
 */
class BindingListWithAddBuilder<T> {
    private var setter: Consumer<List<T>>? = null
    private var init: List<T>? = null
    private var listener: ActionListener? = null

    private constructor(setter: Consumer<List<T>>) {
        this.setter = setter
    }

    private constructor(init: List<T>) {
        this.init = init
    }

    private constructor() {}

    fun init(init: List<T>?): BindingListWithAddBuilder<T> {
        this.init = init
        return this
    }

    fun addListener(listener: ActionListener): BindingListWithAddBuilder<T> {
        this.listener = listener
        return this
    }

    fun build(): BindingListWithAdd<T> {
        val listData = ObjectUtils.firstNonNull(init, Supplier { ArrayList() })
        val bindingListWithAdd = BindingListWithAdd(setter)
        bindingListWithAdd.setData(listData)
        bindingListWithAdd.setAddActionListener(listener)
        return bindingListWithAdd
    }

    companion object {
        fun <T> from(setter: Consumer<List<T>>): BindingListWithAddBuilder<T> {
            return BindingListWithAddBuilder(setter)
        }

        fun <T> from(init: List<T>): BindingListWithAddBuilder<T> {
            return BindingListWithAddBuilder(init)
        }

        fun <T> from(): BindingListWithAddBuilder<T> {
            return BindingListWithAddBuilder()
        }
    }
}
