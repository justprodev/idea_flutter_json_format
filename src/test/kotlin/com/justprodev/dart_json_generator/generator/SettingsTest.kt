package com.justprodev.dart_json_generator.generator

import com.intellij.ide.util.PropertiesComponent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SettingsTest {
    @Test
    fun test() {
        val fakePropertiesComponent = FakePropertiesComponent()
        var settings = Settings(fakePropertiesComponent)

        for (field in settings.data.javaClass.declaredFields) {
            field.isAccessible = true
            field.set(settings.data, true)
        }
        settings.save()

        settings = Settings(fakePropertiesComponent)

        for (field in settings.data.javaClass.declaredFields) {
            field.isAccessible = true
            assertTrue(field.get(settings.data) as Boolean)
        }
    }
}

class FakePropertiesComponent : PropertiesComponent() {
    val storage = mutableMapOf<String, String>()

    override fun unsetValue(name: String) {
        storage.remove(name)
    }

    override fun isValueSet(name: String): Boolean {
        return storage.containsKey(name)
    }

    override fun getValue(name: String): String? {
        return storage[name]
    }

    override fun setValue(name: String, value: String?) {
        TODO("Not yet implemented")
    }

    override fun setValue(name: String, value: String?, defaultValue: String?) {
        TODO("Not yet implemented")
    }

    override fun setValue(name: String, value: Float, defaultValue: Float) {
        TODO("Not yet implemented")
    }

    override fun setValue(name: String, value: Int, defaultValue: Int) {
        TODO("Not yet implemented")
    }

    override fun setValue(name: String, value: Boolean, defaultValue: Boolean) {
        storage[name] = value.toString()
    }

    override fun getValues(name: String): Array<String>? {
        TODO("Not yet implemented")
    }

    override fun setValues(name: String, values: Array<out String>?) {
        TODO("Not yet implemented")
    }
}