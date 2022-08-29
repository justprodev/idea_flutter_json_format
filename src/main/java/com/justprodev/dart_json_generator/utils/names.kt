package com.justprodev.dart_json_generator.utils

fun String.toUpperCaseFirstOne(): String {
    if (isEmpty()) return this
    return if (Character.isUpperCase(this[0])) this else Character.toUpperCase(this[0]) + this.substring(1)
}

fun String.toLowerCaseFirstOne(): String {
    if (this.isEmpty()) return this
    return if (Character.isLowerCase(this[0])) this else Character.toLowerCase(this[0]) + this.substring(1)
}

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