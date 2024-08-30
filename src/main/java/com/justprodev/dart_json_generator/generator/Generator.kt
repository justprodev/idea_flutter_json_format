package com.justprodev.dart_json_generator.generator


import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.justprodev.dart_json_generator.utils.toUpperCaseFirstOne

/***
 * Generate Dart model class from JSON
 */
class Generator(val settings: Settings?) {

    fun generate(modelName: ModelName, jsonElement: JsonElement): String {
        val className = modelName.className
        val fileName = modelName.fileName

        val jsonObject = when (jsonElement) {
            is JsonObject -> jsonElement.asJsonObject
            is JsonArray -> jsonElement.asJsonArray[0].asJsonObject
            else -> null
        }

        val clazzes = mutableListOf<Clazz>()
        val root = Clazz(clazzes, className, jsonObject)

        val sb = StringBuilder().apply {
            append("import 'package:freezed_annotation/freezed_annotation.dart';\n\n")
            append("part '$fileName.freezed.dart';\n")
            append("part '$fileName.g.dart';\n\n")

            clazzes.reversed().forEach {
                append(it.toString(it == root))
                append("\n\n")
            }
        }

        return sb.toString()
    }

    private fun Clazz.toString(keepName: Boolean): String {
        val sb = StringBuilder()

        val className = if (keepName)
            name.toUpperCaseFirstOne()
        else
            getClassName().toUpperCaseFirstOne()


        // comments
//        if (settings?.generateComments == true) {
//            clazz.children?.map {
//                "// ${it.getComment()}\n"
//            }?.forEach {
//                sb.append(it)
//            }
//            sb.append("\n")
//        }

        // class
        sb.append("@freezed\n")
            .append("class $className with _\$$className {\n")

        // constructor
        sb.append("  const factory $className(")
        if (children != null && children!!.isNotEmpty()) {
            sb.append("{")
            children!!.map {
                "\n    @JsonKey(name: '${it.name}') ${it.getStatement()},"
            }.forEach {
                sb.append(it)
            }
            sb.append("\n  }")
        }
        sb.append(") = _$className;\n\n")

        // json
        sb.append("  factory $className.fromJson(Map<String, Object?> json) => _\$${className}FromJson(json);\n")

        sb.append("}")

        return sb.toString()
    }
}