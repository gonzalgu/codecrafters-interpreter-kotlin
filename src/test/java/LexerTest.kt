import org.jetbrains.annotations.TestOnly
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LexerTest{
    @Test
    fun lexTest(){
        val text = "("
        val lexer = Lexer(text)
        val tokens = lexer.scanTokens()
        System.out.println(tokens)
    }

    @Test
    fun lexErrorTest(){
        val text = ",.\$(#"
        runCommand("tokenize", text)
    }

}