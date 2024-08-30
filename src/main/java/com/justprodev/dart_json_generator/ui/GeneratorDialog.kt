package com.justprodev.dart_json_generator.ui

import com.intellij.json.JsonLanguage
import com.intellij.lang.Language
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.psi.PsiFileFactory
import com.justprodev.dart_json_generator.generator.Generator
import com.justprodev.dart_json_generator.generator.ModelName
import com.justprodev.dart_json_generator.generator.Settings
import com.justprodev.dart_json_generator.utils.JsonContainer
import com.justprodev.dart_json_generator.utils.createFileName
import java.awt.BorderLayout
import javax.swing.*

private const val PADDING = 10


/**
 * Dialog for generating dart model class for serializing/deserializing JSON
 *
 * @param project
 * @param modelName not null if known file name and class name
 * @param onGenerate invoked when generate button is clicked
 */
class GeneratorDialog(
    private val project: Project,
    private val modelName: ModelName? = null,
    private val onGenerate: (modelName: ModelName, code: String) -> Unit
) {
    private val frame: JFrame
    private val json = JsonContainer()
    private val editor = project.createEditor()

    init {
        frame = JFrame("Create dart model class for serializing/deserializing JSON").apply {
            val root = FocusManager.getCurrentManager().activeWindow
            setSize(root?.let { (it.width * 0.65).toInt() } ?: 700, root?.let { (it.height * 0.65).toInt() } ?: 520)
            defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            add(build())
            isVisible = true
            setLocationRelativeTo(root)
        }
    }

    private fun build(): JComponent {
        return JPanel().apply {
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
            placeComponents(this)
        }
    }

    private fun prettify(postAction: () -> Unit) {
        json.prettify { prettyJson ->
            SwingUtilities.invokeLater {
                WriteCommandAction.runWriteCommandAction(project) {
                    editor.document.setText(prettyJson)
                    postAction()
                }
            }
        }
    }

    private fun placeComponents(panel: JPanel) = panel.apply {
        val settings = Settings()

        lateinit var classNameField: JTextField

        val generateButton = JButton("Generate ${modelName?.className ?: ""}").apply {
            isEnabled = false
            addActionListener {
                if (classNameField.text.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Please input class name",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE
                    )
                    classNameField.requestFocus()
                    return@addActionListener
                }

                val jsonElement = json.element ?: return@addActionListener
                val modelName = modelName ?: ModelName(classNameField.text, createFileName(classNameField.text))

                val code = Generator(settings).generate(modelName, jsonElement)

                onGenerate(modelName, code)

                settings.save()

                frame.dispose()
            }
        }

        classNameField = JTextField().apply {
            if (modelName == null) {
                // watch for changes in class name field to update button text
                document.addDocumentListener(object : javax.swing.event.DocumentListener {
                    override fun insertUpdate(p0: javax.swing.event.DocumentEvent?) = updateGeneratorButton()
                    override fun removeUpdate(p0: javax.swing.event.DocumentEvent?) = updateGeneratorButton()
                    override fun changedUpdate(p0: javax.swing.event.DocumentEvent?) = updateGeneratorButton()

                    private fun updateGeneratorButton() {
                        generateButton.text = "Generate ${classNameField.text}"
                    }
                })
            } else {
                // if class name is known, disable field and set button text
                isEnabled = false
                text = modelName.className
            }
        }

        val formatButton = JButton("Format").apply {
            isEnabled = false
            addActionListener {
                isEnabled = false
                prettify {
                    isEnabled = true
                }
            }
        }

        //val commentCb = JCheckBox("generate comments", settings.generateComments)

        add(editor.component, BorderLayout.CENTER)

        add(JPanel().apply {
            layout = BorderLayout()
            border = BorderFactory.createEmptyBorder(PADDING, 0, 0, 0)
            add(formatButton, BorderLayout.WEST)
            add(generateButton, BorderLayout.CENTER)
        }, BorderLayout.SOUTH)

        add(JPanel().apply {
            layout = BorderLayout().apply {
                hgap = PADDING
            }
            border = BorderFactory.createEmptyBorder(0, 0, PADDING, 0)
            add(JLabel("Dart class name:"), BorderLayout.WEST)
            add(classNameField, BorderLayout.CENTER)
        }, BorderLayout.NORTH)

        editor.document.addDocumentListener(object : DocumentListener {
            override fun beforeDocumentChange(event: DocumentEvent) = Unit

            override fun documentChanged(event: DocumentEvent) {
                generateButton.isEnabled = false
                json.validate(event.document.text) { element ->
                    generateButton.isEnabled = element != null
                    formatButton.isEnabled = element != null
                }
            }
        })
    }
}

private fun Project.createEditor(): Editor {
    val editor = TextEditorProvider.getInstance().createEditor(
        this,
        PsiFileFactory.getInstance(this).createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "").virtualFile
    ) as TextEditor

    Disposer.register(this, editor)

    return editor.editor
}