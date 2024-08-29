package com.justprodev.dart_json_generator.generator

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.justprodev.dart_json_generator.utils.toUpperCaseFirstOne

abstract class Clazz(
    open val root: MutableList<Clazz>,
    open val name: String,
    open val content: Any?,
    open val children: List<Clazz>?
) {
    companion object {
        operator fun invoke(root: MutableList<Clazz>, name: String, any: Any?): Clazz {
            // translate to english: Handle null values
            if (any == null || "null" == any.toString())
                return EmptyClazz(root, name, any, null)

            // translate to english: Handle objects
            if (any is JsonObject)
                return ObjectClazz(root, name, any, json2Clazz(root, any))

            // translate to english: Handle arrays
            if (any is JsonArray) {
                return if (any.size() == 0) {
                    ListClazz(root, name, any, null, null)
                } else {
                    val temp = Clazz(root, name, any[0])
                    ListClazz(root, name, any, null, temp)
                }
            }

            // Handle basic types
            if (any.isBoolean())
                return BaseClazz(root, "bool", name, any, null)

            if (any.isInt() || any.isLong())
                return BaseClazz(root, "int", name, any, null)

            if (any.isDouble() || any.isFloat())
                return BaseClazz(root, "double", name, any, null)

            // If none of the above matches, it defaults to the String type
            return  BaseClazz(root, "String", name, any, null)
        }

        private fun json2Clazz(root: MutableList<Clazz>, jsonObject: JsonObject): List<Clazz> {
            val list = mutableListOf<Clazz>()
            for (o in jsonObject.entrySet()) {
                val entry = o as Map.Entry<*, *>
                list.add(Clazz(root, entry.key.toString(), entry.value))
            }
            return list
        }

        private fun Any.isInt() = toString().toIntOrNull() != null
        private fun Any.isLong() = toString().toLongOrNull() != null
        private fun Any.isDouble() = toString().toDoubleOrNull() != null
        private fun Any.isFloat() = toString().toFloatOrNull() != null
        private fun Any.isBoolean() = toString().let { it == "true" || it == "false" }
    }

    fun getStatement() = "${getClassName()}? ${getCamelName()}"
    fun getCamelName() = name.split("_").reduce { acc, s -> "$acc${s.toUpperCaseFirstOne()}" }

    abstract fun toJson(): String
    abstract fun getAssignments(parent: String): List<String>
    abstract fun getClassName(): String
    abstract fun map(obj: String): String
}

data class EmptyClazz(
    override val root: MutableList<Clazz>,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?
) : Clazz(root, name, content, children) {

    override fun getClassName() = "dynamic"
    override fun getAssignments(parent: String) = listOf("map['$name'],")
    override fun map(obj: String) = ""
    override fun toJson() = "${getCamelName()}?.toJson()"
}

data class BaseClazz(
    override val root: MutableList<Clazz>,
    val type: String,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?
) : Clazz(root, name, content, children) {

    override fun getClassName() = type
    override fun getAssignments(parent: String) = listOf("map['$name'],")
    override fun map(obj: String): String {
        return when (type) {
            "bool" -> "$obj.toString() == 'true'"
            "int" -> "int.tryParse($obj.toString()) ?? 0"
            "double" -> "double.tryParse($obj.toString()) ?? 0.0"
            else -> "$obj.toString()"
        }
    }
    override fun toJson() = getCamelName()
}

data class ObjectClazz(
    override val root: MutableList<Clazz>,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?
) : Clazz(root, name, content, children) {
    init {
        root.add(this)
    }

    override fun getClassName() = "${name.toUpperCaseFirstOne()}Bean"
    override fun getAssignments(parent: String) = listOf("map['$name']!=null ? ${getClassName()}.fromMap(map['$name']) : null,")
    override fun map(obj: String): String {
        return "${getClassName()}.fromMap($obj)"
    }

    override fun toJson() = "${getCamelName()}?.toJson()"
}

data class ListClazz(
    override val root: MutableList<Clazz>,
    override val name: String,
    override val content: Any?,
    override val children: List<Clazz>?,
    val child: Clazz?
) : Clazz(root, name, content, children) {

    override fun getClassName() = "List<${child?.getClassName() ?: "dynamic"}>"

    override fun map(obj: String): String {
        return if (child == null || child is EmptyClazz) "$obj!=null ? []..addAll($obj as List) : null"
        else "$obj!=null ? []..addAll(($obj as List).map((${obj}o) => ${child.map("${obj}o")})) : null"
    }

    override fun getAssignments(parent: String): List<String> {
        return if (child == null || child is EmptyClazz) listOf("map['$name'],")
        else listOf(
            "map['$name']!=null ? ([]..addAll(",
            "  (map['$name'] as List).map((o) => ${child.map("o")})",
            ")) : null,"
        )
    }
    override fun toJson(): String {
        if(child == null || child is BaseClazz) return getCamelName();

        if(child.getClassName() == "dynamic") {
            return "${getCamelName()}?.map((o) {try{ return o.toJson(); }catch(e){ return o; }}).toList(growable: false)"
        }

        return "${getCamelName()}?.map((o)=>o.toJson()).toList(growable: false)"
    }
}