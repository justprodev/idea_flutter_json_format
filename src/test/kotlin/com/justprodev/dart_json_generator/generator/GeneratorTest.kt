package com.justprodev.dart_json_generator.generator

import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File
import java.io.InputStreamReader

const val standardModelName = "StandardModel"
const val defaultModelName = "DefaultModel"

const val dartDirPath = "dart"
const val dartTestScriptPathPrefix = ".test_dart"
const val dartTestScript = """
cd $dartDirPath || exit 1
dart pub get
dart run build_runner build --delete-conflicting-outputs
dart test
"""


class GeneratorTest {
    @org.junit.jupiter.api.Test
    fun generate() {
        val dartTestJobs = mutableListOf<DartTestJob>()

        // no settings
        var json = File(Generator::class.java.classLoader.getResource("test.json")!!.toURI()).readText()
        var modelName = ModelName.fromClassName(standardModelName)
        var model = Generator(SettingsData()).generate(modelName, JsonParser.parseString(json))
        dartTestJobs.add(DartTestJob(modelName.fileName, model))

        // using @Default annotation
        json = File(Generator::class.java.classLoader.getResource("test_default.json")!!.toURI()).readText()
        modelName = ModelName.fromClassName(defaultModelName)
        val settings = SettingsData(
            useDefaultString = true,
            useDefaultList = true,
            useDefaultBool = true,
            useDefaultInt = true,
            useDefaultDouble = true,
        )
        model = Generator(settings).generate(modelName, JsonParser.parseString(json))
        dartTestJobs.add(DartTestJob(modelName.fileName, model))

        assert(testDartModel(dartTestJobs)) { "Dart tests not passed" }
    }

    private fun testDartModel(jobs: List<DartTestJob>): Boolean {
        File("$dartDirPath/lib/model").deleteRecursively()
        File("$dartDirPath/lib/model").mkdirs()

        // write models
        jobs.forEach { job ->
            File("$dartDirPath/lib/model/${job.fileName}.dart").writeText(job.model)
        }

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

class DartTestJob(
    val fileName: String,
    val model: String,
)
