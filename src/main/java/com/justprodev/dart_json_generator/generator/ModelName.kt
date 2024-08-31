package com.justprodev.dart_json_generator.generator

import com.justprodev.dart_json_generator.utils.createFileName

/**
 * Model's className & fileName
 */
class ModelName(val className: String, val fileName: String) {
    companion object {
        fun fromClassName(className: String) = ModelName(className, createFileName(className))
    }
}