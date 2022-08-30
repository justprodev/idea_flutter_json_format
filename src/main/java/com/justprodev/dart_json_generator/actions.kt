package com.justprodev.dart_json_generator

import com.intellij.json.JsonLanguage
import com.intellij.lang.Language
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiUtilBase
import com.justprodev.dart_json_generator.ui.GeneratorDialog
import com.justprodev.dart_json_generator.ui.Model
import com.justprodev.dart_json_generator.utils.*
import javax.swing.JFrame

class DartJsonGenerateAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) as Project
        val input = PsiFileFactory.getInstance(project).createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "")
        val output = PsiUtilBase.getPsiFileInEditor(event.getData(PlatformDataKeys.EDITOR) as Editor, project)!!.virtualFile
        val className = output.nameWithoutExtension.toUpperCaseFirstOne()
            .split("_")
            .reduce { acc, s -> "$acc${s.toUpperCaseFirstOne()}" }

        GeneratorDialog(project, input, Model(className, output.name)) { _, code ->
            output.write(project, code)
        }
    }
}

class DartJsonNewFileAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.getData(PlatformDataKeys.PROJECT) as Project
        val input = PsiFileFactory.getInstance(project).createFileFromText(Language.findLanguageByID(JsonLanguage.INSTANCE.id)!!, "")

        val directory = when (val navigatable = LangDataKeys.NAVIGATABLE.getData(event.dataContext)) {
            is PsiDirectory -> navigatable
            is PsiFile -> navigatable.containingDirectory
            else -> {
                val root = ModuleRootManager.getInstance(LangDataKeys.MODULE.getData(event.dataContext) ?: return)
                root.sourceRoots
                    .asSequence()
                    .mapNotNull {
                        PsiManager.getInstance(project).findDirectory(it)
                    }.firstOrNull()
            }
        } ?: return

        GeneratorDialog(project, input) { fileName, code ->
            val output = PsiFileFactory.getInstance(project).createFileFromText("${fileName.trim('`')}.dart", DartFileType(), code)
            directory.add(output)
        }
    }
}
