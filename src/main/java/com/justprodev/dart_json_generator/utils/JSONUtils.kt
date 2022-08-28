package com.justprodev.dart_json_generator.utils

import com.google.gson.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors

@DelicateCoroutinesApi
class JSONUtils {
    private var validateJob: Job? = null
    private val mutex = Mutex()
    private val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    private val parser = JsonParser()
    private var jsonElement: JsonElement? = null

    fun validate(json: String, result: (Boolean)->Unit) {
        if(mutex.isLocked) return

        validateJob?.cancel()
        validateJob = GlobalScope.launch(Dispatchers.IO) {
            val isValid = try {
                val element = parser.parse(json).also { jsonElement = it }
                (element.isJsonObject || element.isJsonArray)
            } catch (e: Throwable) {
                false
            }

            result(isValid)
        }
    }

    fun prettify(json: String, result: (String)->Unit) {
        validateJob?.invokeOnCompletion {
            GlobalScope.launch(Dispatchers.IO) {
                mutex.withLock {
                    try {
                        val element = jsonElement!!
                        val `object` = if (element.isJsonObject) {
                            element.asJsonObject
                        } else {
                            element.asJsonArray
                        }
                        val prettyJson = gson.toJson(`object`)
                        result(prettyJson)
                    } catch (e: Throwable) {}
                }
            }
        }
    }
}