package com.justprodev.dart_json_generator

import com.justprodev.dart_json_generator.generator.Generator
import com.justprodev.dart_json_generator.generator.ModelName
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File
import java.io.InputStreamReader

const val testModelName = "NewModel"
const val testModelFileName = "new_model"

const val dartDirPath = "dart"
const val dartTestScriptPathPrefix = ".test_dart"
const val dartTestScript = """
cd $dartDirPath || exit 1
dart pub get
dart run build_runner build --delete-conflicting-outputs
dart lib/main.dart
"""


class GeneratorTest {
    @org.junit.jupiter.api.Test
    fun generate() {
        val json = File(Generator::class.java.classLoader.getResource("test.json")!!.toURI()).readText()
        val model = Generator(null).generate(ModelName(testModelName, testModelFileName), json)

        assert(testDartModel(model)) { "Dart tests not passed" }
    }

    private fun testDartModel(model: String): Boolean {
        File("$dartDirPath/lib/model").deleteRecursively()
        File("$dartDirPath/lib/model").mkdirs()
        File("$dartDirPath/lib/model/$testModelFileName.dart").writeText(model)

        val procBuilder: ProcessBuilder
        val dartScriptFileName: String

        if (System.getProperty("os.name").contains("Windows", ignoreCase = true)) {
            dartScriptFileName = "$dartTestScriptPathPrefix.bat"
            procBuilder = ProcessBuilder("cmd", "/c", dartScriptFileName)
        } else {
            dartScriptFileName = "$dartTestScriptPathPrefix.sh"
            procBuilder = ProcessBuilder("sh", dartScriptFileName)
        }

        File(dartScriptFileName).writeText(dartTestScript)

        val proc = with(procBuilder) {
            redirectErrorStream(true)
            start()
        }

        val out = BufferedReader(InputStreamReader(DataInputStream(proc.inputStream)))
        var s: String?

        do {
            s = out.readLine()?.also { println(it) }
        } while (s != null)

        File(dartScriptFileName).delete()

        return proc.waitFor() == 0
    }
}