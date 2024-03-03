package com.justprodev.dart_json_generator.generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.intellij.util.containers.isNullOrEmpty
import com.justprodev.dart_json_generator.utils.JSONUtils
import com.justprodev.dart_json_generator.utils.Settings
import com.justprodev.dart_json_generator.utils.toUpperCaseFirstOne
import java.lang.IllegalStateException

class ClazzGenerator(val settings: Settings?) {

    fun generate(fileName: String, className: String, json: String) = try {
        JSONUtils.parse(json).let {
            when (it) {
                is JsonObject -> it.asJsonObject
                is JsonArray -> it.asJsonArray[0].asJsonObject
                else -> null
            }
        }.let { obj ->
            mutableListOf<Clazz>().let {
                Clazz(it, className, obj) to it
            }
        }.let { (clazz, clazzes) ->
            val sb = StringBuilder()

            sb.append("import 'package:freezed_annotation/freezed_annotation.dart';\n\n")
            sb.append("part '$fileName.freezed.dart';\n")
            sb.append("part '$fileName.g.dart';\n\n")

            clazzes.reversed().forEach {
                sb.append(printClazz(it == clazz, it))
                sb.append("\n\n")
            }
            sb.toString()
        }
    } catch (jsonParseException: JsonParseException) {
        jsonParseException.printStackTrace()
        "error: not supported json"
    } catch (illegalStateException: IllegalStateException) {
        illegalStateException.printStackTrace()

        if (illegalStateException.message?.startsWith("Not a JSON Object") == true) {
            "error: not supported json"
        } else {
            "error: unknown"
        }
    }

    private fun printClazz(keepName: Boolean, clazz: Clazz): String {
        val sb = StringBuilder()

        val className = if (keepName)
            clazz.name.toUpperCaseFirstOne()
        else
            clazz.getClassName().toUpperCaseFirstOne()


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
        if (clazz.children != null && clazz.children!!.isNotEmpty()) {
            sb.append("{")
            clazz.children!!.map {
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