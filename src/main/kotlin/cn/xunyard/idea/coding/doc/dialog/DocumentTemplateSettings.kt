package cn.xunyard.idea.coding.doc.dialog

import cn.xunyard.idea.coding.doc.ContentSource
import cn.xunyard.idea.coding.doc.DocumentTemplateConfiguration
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EnumComboBoxModel
import com.intellij.ui.SimpleListCellRenderer
import com.intellij.ui.components.JBList
import com.intellij.ui.layout.*
import javax.swing.ComboBoxModel
import javax.swing.JComponent
import javax.swing.JList
import javax.swing.ListCellRenderer

/**
 *
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2020-12-18
 */
class DocumentTemplateSettings constructor(
    val project: Project?
) : DialogWrapper(project) {


    init {
        init()
    }

    override fun createCenterPanel(): JComponent {

        return panel {
            titledRow("全局") {
                row {
                    label("文案来源:")
                    comboBox(
                        EnumComboBoxModel(ContentSource::class.java),
                        { configuration.firstContentSource ?: ContentSource.ANNOTATION },
                        configuration::setFirstContentSource
                    )
                }
                row {
                    checkBox(
                        "展示序号",
                        { configuration.enableSerialNumber ?: true },
                        configuration::setEnableSerialNumber
                    )
                }
            }
            titledRow("注解") {

            }
            titledRow("JavaDoc标签") {

            }
            titledRow("Parameter") {

            }
        }
    }

    private fun Row.listWithAddAndRemoveButton(model: ComboBoxModel<String>,
                                               modelBinding: PropertyBinding<String?>,
                                               renderer: ListCellRenderer<String?>? = null): CellBuilder<ComboBox<String>> {
        return component(ComboBox<String>())
            .applyToComponent {
                this.renderer = renderer ?: SimpleListCellRenderer.create("") { it.toString() }
                selectedItem = modelBinding.get()
            }
            .withBinding(
                { component -> component.selectedItem as String? },
                { component, value -> component.setSelectedItem(value) },
                modelBinding
            )
    }

    companion object {
        private val configuration: DocumentTemplateConfiguration = DocumentTemplateConfiguration()
    }
}