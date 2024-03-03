package com.justprodev.dart_json_generator.utils

import com.google.gson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object JSONUtils {
    private var validateJob: Job? = null
    private val mutex = Mutex()
    private val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    fun validate(json: String, result: (JsonElement?) -> Unit) {
        if (mutex.isLocked) return

        validateJob?.cancel()
        validateJob = GlobalScope.launch(Dispatchers.IO) {
            val element = try {
                val parsed = JsonParser.parseString(json)

                if (parsed.isJsonObject || parsed.isJsonArray) {
                    parsed
                } else {
                    null
                }
            } catch (e: Throwable) {
                null
            }

            result(element)
        }
    }

    /**
     * Prettify JSON
     */
    fun prettify(element: JsonElement, result: (String) -> Unit) {
        validateJob?.invokeOnCompletion {
            GlobalScope.launch(Dispatchers.IO) {
                mutex.withLock {
                    val `object` = if (element.isJsonObject) {
                        element.asJsonObject
                    } else {
                        element.asJsonArray
                    }
                    val prettyJson = gson.toJson(`object`)
                    result(prettyJson)
                }
            }
        }
    }

    fun parse(json: String): JsonElement = JsonParser.parseReader(json.reader())
}