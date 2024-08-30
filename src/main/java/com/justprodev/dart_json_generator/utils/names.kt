package com.justprodev.dart_json_generator.utils

/**
 * Create valid Dart file name for class
 *
 * Example: `ClassName` -> `class_name`
 */
fun createFileName(className: String): String {
    var fileName = ""
    var prevChar: Char? = null
    className.toCharArray().forEach { char ->
        if (prevChar?.isLowerCase() == true && char.isUpperCase()) {
            fileName += "_" + Character.toLowerCase(char)
        } else {
            fileName += Character.toLowerCase(char)
        }
        prevChar = char
    }
    return fileName
}

/**
 * Create valid Dart class name from file name
 *
 * Example: `class_name` -> `ClassName`
 */
fun createClassName(filename: String) = filename.toUpperCaseFirstOne().toCamelCase()

/**
 * Convert snake_case to camelCase
 */
fun String.toCamelCase() = this.split("_").reduce { acc, s -> "$acc${s.toUpperCaseFirstOne()}" }

fun String.toUpperCaseFirstOne(): String {
    if (isEmpty()) return this
    return if (Character.isUpperCase(this[0])) this else Character.toUpperCase(this[0]) + this.substring(1)
}
