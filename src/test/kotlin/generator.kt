import com.zll.format.ClazzGenerator
import com.zll.format.Settings
import java.io.File

const val dartTestDir = "test"

fun main(args: Array<String>) {
    val json = File("$dartTestDir/test.json").readText()
    val model = ClazzGenerator(Settings().apply { clearNulls = true }).generate("TestModel2", json)
    File("$dartTestDir/TestModel2.dart").writeText(model)
}