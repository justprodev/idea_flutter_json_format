package com.justprodev.dart_json_generator.utils

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class DartFileType : FileType {
    override fun getCharset(p0: VirtualFile, p1: ByteArray): String? {
        return null
    }

    override fun getDefaultExtension(): String {
        return ".dart"
    }

    override fun getIcon(): Icon? {
        return null
    }

    override fun getName(): String {
        return "Dart file"

    }

    override fun getDescription(): String {
        return "Dart source file"

    }

    override fun isBinary(): Boolean {
        return false
    }

    override fun isReadOnly(): Boolean {
        return false
    }
}
