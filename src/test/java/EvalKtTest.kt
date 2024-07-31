import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EvalKtTest{
    @Test
    fun testEval(){
        val text = "2 + 3"
        runCommand("evaluate", text)
    }
    @Test
    fun testTrue(){
        val text = "true"
        runCommand("evaluate", text)
    }
    @Test
    fun testFalse(){
        val text = "false"
        runCommand("evaluate", text)
    }
    @Test
    fun testNil(){
        val text = "nil"
        runCommand("evaluate", text)
    }
}