import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    if (args.size < 2) {
        System.err.println("Usage: ./your_program.sh tokenize <filename>")
        exitProcess(1)
    }

    val command = args[0]
    val filename = args[1]
    val fileContents = File(filename).readText()
    when(command){
        "tokenize" -> {
            val lexer = Lexer(fileContents)
            val tokens = lexer.scanTokens()
            tokens.forEach{ t -> println(t) }        }
        else -> {
            System.err.println("Unknown command: ${command}")
            exitProcess(1)
        }
    }
}
