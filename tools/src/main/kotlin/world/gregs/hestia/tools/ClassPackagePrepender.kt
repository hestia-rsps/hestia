package world.gregs.hestia.tools

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*

object ClassPackagePrepender {
    @JvmStatic
    fun main(args: Array<String>) {
        File("C:\\Users\\Greg\\IdeaProjects\\hestia-client\\client\\src\\world\\gregs\\hestia\\client\\").listFiles().filter { it.isFile }.forEach {

            val read = BufferedReader(FileReader(it))
            val list = ArrayList<String>()

            var dataRow: String? = read.readLine()
            while (dataRow != null) {
                list.add(dataRow)
                dataRow = read.readLine()
            }

            val writer = FileWriter(it) //same as your file name above so that it will replace it
            writer.append("package world.gregs.hestia.client;").appendln()

            for (i in list.indices) {
                writer.append(list[i]).appendln()
            }
            writer.flush()
            writer.close()
        }
    }
}