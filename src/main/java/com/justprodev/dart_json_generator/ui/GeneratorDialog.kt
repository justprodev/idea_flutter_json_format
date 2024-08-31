package com.justprodev.dart_json_generator.ui

import com.intellij.icons.AllIcons
import com.intellij.json.JsonLanguage
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.Presentation
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.actionSystem.impl.ActionButton
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
import com.justprodev.dart_json_generator.utils.getString
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Window
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
    private val json = JsonContainer()
    private val settings = Settings()

    // components
    private lateinit var window: Window
    private lateinit var classNameField: JTextField
    private lateinit var editor: Editor
    private lateinit var generateButton: JButton
    private lateinit var formatButton: JButton
    private lateinit var settingsPanel: SettingsPanel

    init {
        createClassNameField()
        createEditor()
        createGenerateButton()
        createFormatButton()
        createSettingsPanel()
        createWindow()
    }

    private fun createFormatButton() {
        formatButton = JButton(getString("format")).apply {
            isEnabled = false
            addActionListener {
                isEnabled = false
                json.prettify { prettyJson ->
                    SwingUtilities.invokeLater {
                        WriteCommandAction.runWriteCommandAction(project) {
                            editor.document.setText(prettyJson)
                            isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun createClassNameField() {
        classNameField = JTextField().apply {
            if (modelName == null) {
                // watch for changes in class name field to update button text
                document.addDocumentListener(object : javax.swing.event.DocumentListener {
                    override fun insertUpdate(p0: javax.swing.event.DocumentEvent?) = onChange(text)
                    override fun removeUpdate(p0: javax.swing.event.DocumentEvent?) = onChange(text)
                    override fun changedUpdate(p0: javax.swing.event.DocumentEvent?) = onChange(text)

                    private fun onChange(text: String) {
                        generateButton.text = getString("generate.0", text)
                    }
                })
            } else {
                // if class name is known, disable field and set button text
                isEnabled = false
                text = modelName.className
            }
        }
    }

    private fun createGenerateButton() {
        generateButton = JButton(getString("generate.0", modelName?.className ?: "")).apply {
            isEnabled = false
            addActionListener {
                if (classNameField.text.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        window,
                        getString("please.input.class.name"),
                        getString("inane.error"),
                        JOptionPane.ERROR_MESSAGE
                    )
                    classNameField.requestFocus()
                    return@addActionListener
                }

                settings.save()

                val jsonElement = json.element ?: return@addActionListener
                val modelName = modelName ?: ModelName.fromClassName(classNameField.text)

                val code = Generator(settings.data).generate(modelName, jsonElement)

                onGenerate(modelName, code)

                window.dispose()
            }
        }
    }

    private fun createWindow() {
        window = JFrame(getString("dialog.title")).apply {
            val root = FocusManager.getCurrentManager().activeWindow
            setSize(root?.let { (it.width * 0.65).toInt() } ?: 700, root?.let { (it.height * 0.65).toInt() } ?: 520)
            defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE


            add(JPanel().apply {
                layout = BorderLayout()
                border = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)

                add(JPanel().apply {
                    layout = BorderLayout().apply {
                        hgap = PADDING
                    }
                    border = BorderFactory.createEmptyBorder(0, 0, PADDING, 0)
                    add(JLabel(getString("dart.class.name")), BorderLayout.WEST)
                    add(classNameField, BorderLayout.CENTER)
                    add(
                        ActionButton(
                            object : ToggleAction() {
                                override fun isSelected(e: AnActionEvent): Boolean {
                                    return settingsPanel.isVisible
                                }

                                override fun setSelected(e: AnActionEvent, state: Boolean) {
                                    settings.data.show = state
                                    settingsPanel.isVisible = state
                                }
                            },
                            Presentation(getString("settings")).apply {
                                icon = AllIcons.General.Settings
                            },
                            getString("settings"),
                            Dimension(24, 24)
                        ),
                        BorderLayout.EAST
                    )
                }, BorderLayout.NORTH)

                add(
                    add(object : JPanel() {
                        override fun isOptimizedDrawingEnabled() = false
                    }.apply {
                        layout = OverlayLayout(this)
                        add(JPanel().apply {
                            layout = BoxLayout(this, BoxLayout.Y_AXIS)
                            border = BorderFactory.createEmptyBorder(32, 0, 0, 24)
                            isOpaque = false
                            alignmentX = 1f
                            alignmentY = 0.0f

                            add(settingsPanel.apply {
                                border = BorderFactory.createCompoundBorder(
                                    BorderFactory.createRaisedSoftBevelBorder(),
                                    BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING),
                                )
                            })
                        })
                        add(editor.component)
                    }),
                    BorderLayout.CENTER
                )

                add(JPanel().apply {
                    layout = BorderLayout()
                    border = BorderFactory.createEmptyBorder(PADDING, 0, 0, 0)
                    add(formatButton, BorderLayout.WEST)
                    add(generateButton, BorderLayout.CENTER)
                }, BorderLayout.SOUTH)
            })
            isVisible = true
            setLocationRelativeTo(root)
            addWindowListener(object : java.awt.event.WindowAdapter() {
                override fun windowClosing(e: java.awt.event.WindowEvent?) {
                    settings.save()
                }
            })
        }
    }

    private fun createEditor() {
        editor = project.createEditor().apply {
            document.addDocumentListener(object : DocumentListener {
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

    private fun createSettingsPanel() {
        settingsPanel = SettingsPanel(settings).apply {
            isVisible = settings.data.show
        }
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