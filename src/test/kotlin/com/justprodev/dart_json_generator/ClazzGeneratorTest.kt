package com.justprodev.dart_json_generator

import com.justprodev.dart_json_generator.generator.ClazzGenerator
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File
import java.io.InputStreamReader

const val testModelName = "NewModel"
const val testModelFileName = "new_model"

const val dartDirPath = "dart"
const val dartTestScriptPath = ".test_dart.sh"
const val dartTestScript = """
cd $dartDirPath || exit 1
dart pub get
dart run build_runner build --delete-conflicting-outputs
dart lib/main.dart
"""


class ClazzGeneratorTest {
    @org.junit.jupiter.api.Test
    fun generate() {
        val json = File(ClazzGenerator::class.java.classLoader.getResource("test.json")!!.toURI()).readText()
        val model = ClazzGenerator(null).generate(testModelFileName, testModelName, json)

        assert(testDartModel(model)) { "Dart tests not passed" }
    }

    private fun testDartModel(model: String): Boolean {
        File("$dartDirPath/lib/model").deleteRecursively()
        File("$dartDirPath/lib/model").mkdirs()
        File("$dartDirPath/lib/model/$testModelFileName.dart").writeText(model)
        File(dartTestScriptPath).writeText(dartTestScript)

        val proc = ProcessBuilder("sh", dartTestScriptPath).let {
            it.redirectErrorStream(true)
            it.start()
        }
        val out = BufferedReader(InputStreamReader(DataInputStream(proc.inputStream)))
        var s: String?

        do {
            s = out.readLine()?.also { println(it) }
        } while (s != null)

        File(dartTestScriptPath).delete()

        return proc.waitFor() == 0
    }
}