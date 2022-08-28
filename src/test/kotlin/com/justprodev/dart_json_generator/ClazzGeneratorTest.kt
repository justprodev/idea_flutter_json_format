package com.justprodev.dart_json_generator

import java.io.BufferedReader
import java.io.DataInputStream
import java.io.File
import java.io.InputStreamReader

internal class ClazzGeneratorTest {
    @org.junit.jupiter.api.Test
    fun generate() {
        val json = File(ClazzGenerator::class.java.getClassLoader().getResource("test.json").toURI()).readText()
        val model = ClazzGenerator(null).generate("NewModel", json)
        // write dart
        File("dart/lib/NewModel.dart").writeText(model)

        val proc = ProcessBuilder("test_dart.bat").let {
            it.redirectErrorStream(true)
            it.start()
        }
        val out = BufferedReader(InputStreamReader(DataInputStream(proc.inputStream)))
        var s: String?

        do {
            s = out.readLine()?.also { println(it) }
        } while(s != null)

        assert(proc.waitFor() == 0, {"Dart tests not passed"})
    }
}