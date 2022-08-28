package com.justprodev.dart_json_generator.ui

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.justprodev.dart_json_generator.ClazzGenerator
import com.justprodev.dart_json_generator.Settings
import com.justprodev.dart_json_generator.Util
import com.justprodev.dart_json_generator.utils.JSONUtils
import java.awt.BorderLayout
import javax.swing.*

class UiBuilder(
    private val project: Project,
    private val input: PsiFile,
    private val output: VirtualFile,
) {
    var frame: JFrame? = null
    private val validator = JSONUtils()

    fun build(): JComponent {
        return JPanel().apply {
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(10, 10, 10, 10)
            placeComponents(this)
        }
    }

    private fun placeComponents(panel: JPanel) = panel.apply {
        val editor = TextEditorProvider.getInstance().createEditor(project, input.virtualFile)
        val className = Util.toUpperCaseFirstOne(output.nameWithoutExtension).split("_").reduce { acc, s -> "$acc${Util.toUpperCaseFirstOne(s)}" }
        val settings = Settings()

        val button = JButton("Generate $className").apply {
            isEnabled = false
            addActionListener {
                //settings.generateComments = commentCb.isSelected
                settings.save()

                val classesString = ClazzGenerator(settings).generate(className, input.text)
                Util.writeToFile(project, output, classesString)
                frame?.dispose()
            }
        }

        val formatButton = JButton("Format").apply {
            isEnabled = false
            addActionListener {
                isEnabled = false
                validator.prettify(input.text) { prettyJson ->
                    (editor as? TextEditor)?.editor?.let { editor ->
                        SwingUtilities.invokeAndWait {
                            editor.document.setText(prettyJson)
                            isEnabled = true
                        }
                    }
                }
            }
        }

        //val commentCb = JCheckBox("generate comments", settings.generateComments)

        add(editor.component, BorderLayout.CENTER)

        add(JPanel().apply {
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(10, 0, 0, 0)
            add(formatButton, BorderLayout.WEST)
            add(button, BorderLayout.CENTER)
        }, BorderLayout.SOUTH)

        (editor as? TextEditor)?.editor?.document?.addDocumentListener(object : DocumentListener {
            override fun beforeDocumentChange(event: DocumentEvent?) = Unit

            override fun documentChanged(event: DocumentEvent?) {
                val json = event?.document?.text ?: return
                validator.validate(json) { isValid ->
                    button.isEnabled = isValid
                    formatButton.isEnabled = isValid
                }
            }
        })
    }
}