package com.justprodev.dart_json_generator.generator


import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.justprodev.dart_json_generator.utils.toCamelCase

/***
 * Generate Dart model class from JSON
 */
class Generator(private val settings: SettingsData) {

    fun generate(modelName: ModelName, jsonElement: JsonElement): String {
        val className = modelName.className
        val fileName = modelName.fileName

        val jsonObject = when (jsonElement) {
            is JsonObject -> jsonElement.asJsonObject
            is JsonArray -> jsonElement.asJsonArray[0].asJsonObject
            else -> jsonElement
        }

        val allEntities = mutableListOf<Entity>()
        createEntity(allEntities, className, jsonObject)

        val sb = StringBuilder().apply {
            append("import 'package:freezed_annotation/freezed_annotation.dart';\n\n")
            append("part '$fileName.freezed.dart';\n")
            append("part '$fileName.g.dart';\n\n")

            allEntities.reversed().forEach {
                appendEntity(it)
                append("\n\n")
            }
        }

        return sb.toString()
    }

    private fun StringBuilder.appendEntity(entity: Entity) {
        with(entity) {
            // class
            append("@freezed\n")
            append("class $type with _\$$type {\n")

            // constructor
            append("  const factory $type(")
            if (children != null && children!!.isNotEmpty()) {
                append("{")
                children!!.forEach {
                    append("\n    ")
                    appendField(it)
                }
                append("\n  }")
            }
            append(") = _$type;\n\n")

            // json
            append("  factory $type.fromJson(Map<String, Object?> json) => _\$${type}FromJson(json);\n")

            append("}")
        }
    }

    private fun StringBuilder.appendField(entity: Entity) {
        with(entity) {
            append("@JsonKey(name: '${name}') ")
            appendType(this)
            append(" ${name.toCamelCase()},")
        }
    }

    private fun StringBuilder.appendType(entity: Entity) {
        with(entity) {
            if (type == "dynamic") {
                append("dynamic")
                return
            }

            // try to add @Default annotation with value
            val defaultValue: String? = when {
                type == "int" && settings.useDefaultInt -> "0"
                type == "double" && settings.useDefaultDouble -> "0.0"
                type == "String" && settings.useDefaultString -> "''"
                type == "bool" && settings.useDefaultBool -> "false"
                type.startsWith("List") && settings.useDefaultList -> "[]"
                else -> null
            }

            // determined default value
            if(defaultValue != null) {
                append("@Default($defaultValue) $type")
                return
            } else {
                // add nullable
                append("$type?")
            }
        }
    }
}