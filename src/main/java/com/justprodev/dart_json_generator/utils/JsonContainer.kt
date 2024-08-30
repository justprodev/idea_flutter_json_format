package com.justprodev.dart_json_generator.utils

import com.google.gson.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class JsonContainer {
    private var validateJob: Job? = null
    private val mutex = Mutex()
    private val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()

    private var element: JsonElement? = null

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

            this@JsonContainer.element = element

            result(element)
        }
    }

    /**
     * Prettify JSON
     */
    fun prettify(result: (String) -> Unit) {
        val element = element ?: return

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
}