import java.io.File
import kotlin.system.exitProcess

var hadError = false

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
    runCommand(command, fileContents)
}

fun runCommand(command: String, fileContents: String) {
    when (command) {
        "tokenize" -> {
            val lexer = Lexer(fileContents)
            val tokens = lexer.scanTokens()
            tokens.forEach { t -> println(t) }
            if (hadError) {
                exitProcess(65)
            }
        }
        "parse" -> {
            val lexer = Lexer(fileContents)
            val tokens = lexer.scanTokens()
            val parser = Parser(tokens)
            val expr = parser.parse()
            System.out.println(printAst(expr))
        }

        else -> {
            System.err.println("Unknown command: $command")
            exitProcess(1)
        }
    }
}

fun report(line: Int, where: String, message: String) {
    System.err.print("[line $line] Error$where: $message")
    hadError = true
}

fun error(line: Int, message: String) {
    report(line, "", message)
}

fun error(token: Token, message: String) {
    val where = when (token.type) {
        TokenType.EOF -> " at end"
        else -> "at '${token.lexeme}'"
    }
    report(token.line, where, message)
}
