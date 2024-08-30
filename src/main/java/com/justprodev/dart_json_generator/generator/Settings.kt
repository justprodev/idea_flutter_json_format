package com.justprodev.dart_json_generator.generator

import com.intellij.ide.util.PropertiesComponent

class Settings {
    private val properties = PropertiesComponent.getInstance()

    val data = load()

    fun save() {
        properties.setValue("show", data.show, false)
        properties.setValue("useDefaults", data.useDefaults, false)
    }

    private fun load(): SettingsData {
        return SettingsData(
            show = properties.getBoolean("show", false),
            useDefaults = properties.getBoolean("useDefaults", false),
        )
    }
}

class SettingsData(
    var show: Boolean,
    var useDefaults: Boolean,
)