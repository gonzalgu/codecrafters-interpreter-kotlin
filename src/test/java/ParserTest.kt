import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ParserTest{
    @Test
    fun parseTest(){
        val text = "2 + 3"
        runCommand("parse", text)
    }
    @Test
    fun parseTrue(){
        val text = "true"
        runCommand("parse", text)
    }
    @Test
    fun parseFalse(){
        val text = "false"
        runCommand("parse", text)
    }
    @Test
    fun parseNil(){
        val text = "nil"
        runCommand("parse", text)
    }
    @Test
    fun parseUnmatchedParentheses(){
        val text = "(\"foo\""
        runCommand("parse", text)
    }
    @Test
    fun parseParentheses(){
        val text = "(\"foo\")"
        runCommand("parse", text)
    }
}