package com.justprodev.dart_json_generator

import com.intellij.json.JsonLanguage
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiUtilBase
import com.justprodev.dart_json_generator.ui.UiBuilder
import javax.swing.JFrame

class DartJsonFormatAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) as Project
        val input = PsiFileFactory.getInstance(project).createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "")

        PsiUtilBase.getPsiFileInEditor(event.getData(PlatformDataKeys.EDITOR) as Editor, project)
                ?.let { UiBuilder(it.project, input, it.virtualFile) }
                ?.let {
                    JFrame("To create dart model for serializing/deserializing JSON, based on JSON example").apply {
                        setSize(700, 520)
                        defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
                        add(it.build())
                        isVisible = true
                        setLocationRelativeTo(null)
                    }.apply { it.frame = this }
                }
    }
}