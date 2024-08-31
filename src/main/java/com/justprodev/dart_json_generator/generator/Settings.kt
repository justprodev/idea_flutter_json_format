package com.justprodev.dart_json_generator.generator

import com.intellij.ide.util.PropertiesComponent

class Settings(
    private val properties: PropertiesComponent = PropertiesComponent.getInstance()
) {
    val data = load()

    fun save() {
        properties.setValue("show", data.show)
        properties.setValue("useDefaultString", data.useDefaultString)
        properties.setValue("useDefaultList", data.useDefaultList)
        properties.setValue("useDefaultBool", data.useDefaultBool)
        properties.setValue("useDefaultInt", data.useDefaultInt)
        properties.setValue("useDefaultDouble", data.useDefaultDouble)
    }

    private fun load(): SettingsData {
        return SettingsData().apply {
            show = properties.getBoolean("show")
            useDefaultString = properties.getBoolean("useDefaultString")
            useDefaultList = properties.getBoolean("useDefaultList")
            useDefaultBool = properties.getBoolean("useDefaultBool")
            useDefaultInt = properties.getBoolean("useDefaultInt")
            useDefaultDouble = properties.getBoolean("useDefaultDouble")
        }
    }
}

class SettingsData(
    var show: Boolean = false,
    var useDefaultString: Boolean = false,
    var useDefaultList: Boolean = false,
    var useDefaultBool: Boolean = false,
    var useDefaultInt: Boolean = false,
    var useDefaultDouble: Boolean = false,
)