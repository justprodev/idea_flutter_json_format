package com.justprodev.dart_json_generator.ui

import com.justprodev.dart_json_generator.generator.Settings
import com.justprodev.dart_json_generator.utils.LabelsBundle
import com.justprodev.dart_json_generator.utils.getString
import javax.swing.BoxLayout
import javax.swing.BoxLayout.Y_AXIS
import javax.swing.JCheckBox
import javax.swing.JPanel

private const val PADDING = 10

class SettingsPanel(private val settings: Settings) : JPanel() {
    private lateinit var useDefaultsCheckBox: JCheckBox

    init {
        createUseDefaultsCheckBox()

        layout = BoxLayout(this, Y_AXIS)
        add(useDefaultsCheckBox)
    }

    private fun createUseDefaultsCheckBox() {
        useDefaultsCheckBox = JCheckBox(getString("use.defaults")).apply {
            isSelected = settings.data.useDefaults
            addActionListener {
                settings.data.useDefaults = isSelected
            }
        }
    }
}
