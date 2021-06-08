package com.zll.format

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.FlowLayout
import javax.swing.*

class UiBuilder(private val project: Project, private val virtualFile: VirtualFile) {

    var frame: JFrame? = null

    fun build(): JComponent {
        return JPanel().apply {
            border = BorderFactory.createEmptyBorder(10,0,10,0)
            placeComponents(this)
        }
    }

    private fun placeComponents(panel: JPanel) = panel.apply {
        val className = Util.toUpperCaseFirstOne(virtualFile.nameWithoutExtension).split("_").reduce { acc, s -> "$acc${Util.toUpperCaseFirstOne(s)}" }
        val settings = Settings()

        val jsonText = JTextArea().apply {
            toolTipText = "Paste JSON example here"
        }
        val tipLabel = JLabel("class name: $className")
        val commentCb = JCheckBox("generate comments", settings.generateComments)
        val clearNulls = JCheckBox("clear nulls while deserialize", settings.clearNulls)

        layout = BorderLayout()

        add(JPanel().apply {
            // up
            add(tipLabel)
        }, BorderLayout.NORTH)

        add(JPanel().apply {
            //center
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(10,10,10,10)
            add(JBScrollPane(jsonText).apply {
                verticalScrollBarPolicy = JBScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                horizontalScrollBarPolicy = JBScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
            })
        }, BorderLayout.CENTER)

        add(JPanel().apply {
            // down
            layout = FlowLayout()
            add(commentCb)
            add(clearNulls)
            add(JButton("ok").apply {
                isVisible = true
                addActionListener {
                    // 保存配置
                    settings.generateComments = commentCb.isSelected
                    settings.clearNulls = clearNulls.isSelected
                    settings.save()

                    // 开始生成代码
                    val classesString = ClazzGenerator(settings).generate(className, jsonText.text)
                    if (classesString.startsWith("error:")) {
                        tipLabel.text = classesString
                    } else {
                        Util.writeToFile(project, virtualFile, classesString)
                        frame?.dispose()
                    }
                }
            })
        }, BorderLayout.SOUTH)
    }
}