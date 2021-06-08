package com.zll.format

import com.intellij.ide.util.PropertiesComponent

class Settings {

    companion object {
        private const val KEY_COMMENT = "dart_json_format_comment"
        private const val DESERIALIZE_CLEAR_NULLS = "dart_json_format_clear_nulls"
    }

    var generateComments: Boolean
    var clearNulls: Boolean

    init {
        val propertiesComponent = PropertiesComponent.getInstance()
        generateComments = propertiesComponent.getBoolean(KEY_COMMENT, true)
        clearNulls = propertiesComponent.getBoolean(DESERIALIZE_CLEAR_NULLS, true)
    }

    fun save() = PropertiesComponent.getInstance().apply {
        setValue(KEY_COMMENT, generateComments.toString())
        setValue(DESERIALIZE_CLEAR_NULLS, clearNulls.toString())
    }
}