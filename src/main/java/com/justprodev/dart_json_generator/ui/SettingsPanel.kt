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
    private lateinit var useDefaultStringCheckBox: JCheckBox
    private lateinit var useDefaultListCheckBox: JCheckBox
    private lateinit var useDefaultBoolCheckBox: JCheckBox
    private lateinit var useDefaultIntCheckBox: JCheckBox
    private lateinit var useDefaultDoubleCheckBox: JCheckBox

    init {
        createUseDefaultsCheckBox()

        layout = BoxLayout(this, Y_AXIS)
        add(useDefaultStringCheckBox)
        add(useDefaultListCheckBox)
        add(useDefaultBoolCheckBox)
        add(useDefaultIntCheckBox)
        add(useDefaultDoubleCheckBox)
    }

    private fun createUseDefaultsCheckBox() {
        useDefaultStringCheckBox = JCheckBox(getString("use.default.string")).apply {
            isSelected = settings.data.useDefaultString
            addActionListener {
                settings.data.useDefaultString = isSelected
            }
        }

        useDefaultListCheckBox = JCheckBox(getString("use.default.list")).apply {
            isSelected = settings.data.useDefaultList
            addActionListener {
                settings.data.useDefaultList = isSelected
            }
        }

        useDefaultBoolCheckBox = JCheckBox(getString("use.default.bool")).apply {
            isSelected = settings.data.useDefaultBool
            addActionListener {
                settings.data.useDefaultBool = isSelected
            }
        }

        useDefaultIntCheckBox = JCheckBox(getString("use.default.int")).apply {
            isSelected = settings.data.useDefaultInt
            addActionListener {
                settings.data.useDefaultInt = isSelected
            }
        }

        useDefaultDoubleCheckBox = JCheckBox(getString("use.default.double")).apply {
            isSelected = settings.data.useDefaultDouble
            addActionListener {
                settings.data.useDefaultDouble = isSelected
            }
        }
    }
}
