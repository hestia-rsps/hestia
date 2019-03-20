package world.gregs.hestia.tools

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.ByteBuffer
import java.nio.file.Files
import java.util.regex.Pattern
import kotlin.system.measureTimeMillis

/**
 * Extracts opcode and size values from client classes
 * Writes values to file
 * Reads values from file
 * Prints values to console
 */
class OpcodeFinder(private vararg val processors: OpcodeExtractor) {

    class OpcodeExtractor(private val className: String) {
        fun process(line: String) {
            val matcher = pattern.matcher(line)
            while (matcher.find()) {
                matches.add(Pair(matcher.group(1).toInt(), matcher.group(2).toInt()))
            }
        }

        private val pattern = Pattern.compile("new\\s$className\\(\\s?([0-9]+)\\s?,\\s?(-?[0-9]+)\\s?\\)")
        private val matches = ArrayList<Pair<Int, Int>>()

        fun read(path: String) {
            matches.clear()
            log("'$className' read in ${measureTimeMillis {
                val bytes = Files.readAllBytes(File(path, "$className.dat").toPath())
                val buffer = ByteBuffer.wrap(bytes)
                while (buffer.hasRemaining()) {
                    matches.add(Pair(buffer.get().toInt(), buffer.get().toInt()))
                }
            }}ms")
        }

        fun print() {
            log("'$className' Matches ${matches.size}")
            matches.sortBy { it.first }
            matches.forEach {
                log("${it.first} ${it.second}")
            }
        }

        fun write(path: String) {
            matches.sortBy { it.first }
            log("'$className' wrote in ${measureTimeMillis {
                ByteBuffer.allocate(matches.size * 2).apply {
                    matches.forEach {
                        put(it.first.toByte())
                        put(it.second.toByte())
                    }
                    Files.write(File(path, "$className.dat").toPath(), this.array())
                }
            }}ms")
        }
    }

    fun read(directory: String) {
        processors.forEach {
            it.read(directory)
        }
    }

    fun extract(directory: String) {
        log("Extracted from all files in ${measureTimeMillis {
            val file = File(directory)
            recursive(file)
        }}ms")
    }

    private fun process(file: File) {
        println("Processing ${file.name}")
        val reader = BufferedReader(FileReader(file))
        while (reader.ready()) {
            val line = reader.readLine()
            processors.forEach {
                it.process(line)
            }
        }
        reader.close()
    }

    private fun recursive(file: File) {
        file.listFiles().forEach {
            if (it.isFile) {
                process(it)
            } else if (it.isDirectory) {
                recursive(it)
            }
        }
    }

    companion object {

        private fun log(text: String) {
            println(text)
        }

        private fun help() {
            System.err.println("Usage: <in command> <out command>")
            System.err.println("In commands:")
            System.err.println("    read        -   r <file directory>")
            System.err.println("    extract     -   e <client directory>")
            System.err.println("Out commands:")
            System.err.println("    write       -   w <write directory> <Class name> ... <Class name>")
            System.err.println("    print       -   p <Class name> ... <Class name>")
            System.exit(0)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size < 2) {
                //C:\client\ Class318 Class192 Class133
                help()
            }

            val inCommand = args[0]
            val inDirectory = args[1]

            val outCommand = args[2]
            val write = when (outCommand) {
                "w", "write" -> true
                "p", "print" -> false
                else -> {
                    help()
                    return
                }
            }

            val extractors = args.copyOfRange(if (write) 4 else 3, args.size).map { OpcodeFinder.OpcodeExtractor(it) }.toTypedArray()

            val finder = OpcodeFinder(*extractors)

            when (inCommand) {
                "r", "read" -> finder.read(inDirectory)
                "e", "extract" -> finder.extract(inDirectory)
                else -> help()
            }

            extractors.forEach {
                if (write) {
                    it.write(args[3])
                } else {
                    it.print()
                }
            }
        }
    }
}