package com.justprodev.dart_json_generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.intellij.util.containers.isNullOrEmpty
import java.lang.IllegalStateException

class ClazzGenerator(val settings: Settings?) {

    fun generate(name: String, string: String) = try {
        JsonParser().parse(string).let {
            when (it) {
                is JsonObject -> it.asJsonObject
                is JsonArray -> it.asJsonArray[0].asJsonObject
                else -> null
            }
        }.let { obj ->
            mutableListOf<Clazz>().let {
                Clazz(it, name, obj) to it
            }
        }.let { (clazz, clazzes) ->
            val sb = StringBuilder()

            sb.append("import 'package:freezed_annotation/freezed_annotation.dart';\n\n")
            sb.append("part '$name.freezed.dart';\n")
            sb.append("part '$name.g.dart';\n\n")

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

        val className = Util.toUpperCaseFirstOne((if (keepName) clazz.name else clazz.getClassName()))


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
            .append("class $className with _\$$className{\n")

        // constructor
        sb.append("  const factory $className(")
        if (!clazz.children.isNullOrEmpty()) {
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